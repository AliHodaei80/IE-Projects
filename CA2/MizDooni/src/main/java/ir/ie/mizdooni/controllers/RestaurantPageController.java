package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.exceptions.InvalidUserRole;
import ir.ie.mizdooni.exceptions.RestaurantNotFound;
import ir.ie.mizdooni.exceptions.UserNotFound;
import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.RestaurantTable;
import ir.ie.mizdooni.services.RestaurantHandler;
import ir.ie.mizdooni.services.RestaurantTableHandler;
import ir.ie.mizdooni.services.ReviewHandler;
import ir.ie.mizdooni.services.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ir.ie.mizdooni.definitions.Paths.ERROR_JSP_PAGE;
import static ir.ie.mizdooni.definitions.Paths.RESTAURANT_PAGE;

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

//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        logger.info("RestaurantPageController: get request");
//        Double ambianceRate = Double.parseDouble(request.getParameter("ambiance_rate"));
//        Double overallRate = Double.parseDouble(request.getParameter("overall_rate"));
//        Double foodRate = Double.parseDouble(request.getParameter("food_rate"));
//        String comment = (String) request.getParameter("comment");
//        Double serviceRate = Double.parseDouble(request.getParameter("service_rate"));
//        String username = UserHandler.getInstance().getCurrentUser().getUsername();
//        try {
//            String restaurantId = request.getPathInfo().split("/")[1];
//            Restaurant restaurant = RestaurantHandler.getInstance().getRestaurant(Long.parseLong(restaurantId));
//            ReviewHandler.getInstance().addReview(restaurant.getName(), username, ambianceRate, overallRate, serviceRate, foodRate, comment);
//            response.sendRedirect("/restaurants/" + restaurantId);
//        } catch (RestaurantNotFound | UserNotFound | InvalidUserRole e) {
//            logger.error("SubmitReviewController: Restaurant not found");
//            request.setAttribute("error", e.getMessage());
//            request.getRequestDispatcher(ERROR_JSP_PAGE).forward(request, response);
//        }
//
//    }
}
