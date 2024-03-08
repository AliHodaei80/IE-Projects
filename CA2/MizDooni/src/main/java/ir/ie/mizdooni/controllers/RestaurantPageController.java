package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.RestaurantTable;
import ir.ie.mizdooni.services.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ir.ie.mizdooni.definitions.Paths.*;
import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateAddReview;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateReserveTable;

@WebServlet(RESTAURANT_PAGE)
public class RestaurantPageController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(RestaurantPageController.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("RestaurantPageController: get request");
        String restaurantId = request.getPathInfo().split("/")[1];
        Restaurant restaurant = RestaurantHandler.getInstance().getRestaurant(Long.parseLong(restaurantId));
        request.setAttribute("restaurant", restaurant);
        try {

            List<RestaurantTable> restaurantTables = new ArrayList<>(RestaurantTableHandler.getInstance().getRestTables(restaurant.getName()));
            request.setAttribute("restaurantTables", restaurantTables);
            request.setAttribute("username", UserHandler.getInstance().getCurrentUser().getUsername());
            request.setAttribute("reviews", ReviewHandler.getInstance().getRestReviews(restaurant.getName()));
            request.getRequestDispatcher("/restaurant.jsp").forward(request, response);
        } catch (RestaurantNotFound e) {
            logger.error("RestaurantPageController: Restaurant not found");
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(ERROR_JSP_PAGE).forward(request, response);
        }
    }

    private void reserve(HttpServletRequest request)
            throws InvalidRequestTypeFormat, InvalidTimeFormat, InvalidRequestFormat, UserNotExists, TableAlreadyReserved,
            InvalidUserRole, DateTimeNotInRange, TableDoesntExist, InvalidDateTime, RestaurantNotFound {
        String restaurantId = request.getParameter("restaurant_id");
        Restaurant restaurant = RestaurantHandler.getInstance().getRestaurant(Long.parseLong(restaurantId));
        String restName = restaurant.getName();
        if (request.getParameter("date_time") == null || request.getParameter("date_time").isEmpty()) {
            throw new InvalidRequestFormat("date_time");
        }
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("date_time"));
        String localDateTimeString = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(localDateTime);
        Map<String, Object> data = Map.of(
                RESTAURANT_NAME_KEY, restName,
                USERNAME_KEY, request.getParameter("username"),
                TABLE_NUM_KEY, Long.parseLong(request.getParameter("table_number")),
                DATETIME_KEY, localDateTimeString
        );
        validateReserveTable(data);
        long reservationNum = ReservationHandler.getInstance().addReservation((String) data.get(RESTAURANT_NAME_KEY),
                (String) data.get(USERNAME_KEY),
                (Long) data.get(TABLE_NUM_KEY),
                (String) data.get(DATETIME_KEY));
    }

    void addReview(HttpServletRequest request) throws UserNotFound, InvalidUserRole, RestaurantNotFound,
            InvalidRequestTypeFormat, InvalidTimeFormat, InvalidRequestFormat, InvalidRatingFormat, NoReservationBefore {
        String restaurantId = request.getPathInfo().split("/")[1];
        Restaurant restaurant = RestaurantHandler.getInstance().getRestaurant(Long.parseLong(restaurantId));
        Map<String, Object> data = Map.of(
                RESTAURANT_NAME_KEY, restaurant.getName(),
                USERNAME_KEY, request.getParameter("username"),
                AMBIANCE_RATE_KEY, request.getParameter("ambiance_rate"),
                OVERALL_RATE_KEY, request.getParameter("overall_rate"),
                FOOD_RATE_KEY, request.getParameter("food_rate"),
                SERVICE_RATE_KEY, request.getParameter("service_rate"),
                COMMENT_KEY, request.getParameter("comment")
        );
        validateAddReview(data);
        Double ambianceRate = Double.parseDouble((String) data.get(AMBIANCE_RATE_KEY));
        Double overallRate = Double.parseDouble((String) data.get(OVERALL_RATE_KEY));
        Double foodRate = Double.parseDouble((String) data.get(FOOD_RATE_KEY));
        String comment = (String) data.get(COMMENT_KEY);
        Double serviceRate = Double.parseDouble((String) data.get(SERVICE_RATE_KEY));
        String username = request.getParameter("username");
        ReviewHandler.getInstance().addReview(restaurant.getName(), username, ambianceRate, overallRate, serviceRate, foodRate, comment);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("RestaurantPageController: post request");
        String action = request.getParameter("action");
        String restaurantId = request.getPathInfo().split("/")[1];
        try {
            switch (action) {
                case "reserve" -> {
                    reserve(request);
                    response.sendRedirect(RESERVATIONS_PAGE);
                    return;
                }
                case "feedback" -> {
                    addReview(request);
                    response.sendRedirect("/restaurants/" + restaurantId);
                    return;
                }
                default -> {
                    logger.error("Invalid action");
                    request.setAttribute("error", "Invalid action");
                    request.getRequestDispatcher(ERROR_JSP_PAGE).forward(request, response);
                }
            }
        } catch (InvalidUserRole | UserNotExists | TableAlreadyReserved | RestaurantNotFound | TableDoesntExist |
                 InvalidDateTime | DateTimeNotInRange | InvalidRequestTypeFormat | InvalidTimeFormat |
                 InvalidRequestFormat | UserNotFound | InvalidRatingFormat | NoReservationBefore e) {
            logger.error(action + " failed: error: " + e.getMessage(), e);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(ERROR_JSP_PAGE).forward(request, response);
        }
    }
}
