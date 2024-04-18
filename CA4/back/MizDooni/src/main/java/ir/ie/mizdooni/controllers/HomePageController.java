package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.services.RestaurantHandler;
import ir.ie.mizdooni.services.RestaurantTableHandler;
import ir.ie.mizdooni.services.UserHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static ir.ie.mizdooni.definitions.Errors.INVALID_USER_ROLE;
import static ir.ie.mizdooni.definitions.Paths.*;
import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.definitions.RequestKeys.MANAGER_USERNAME_KEY;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateAddTable;

@WebServlet("")
public class HomePageController  extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(HomePageController.class);
    private void loadPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = UserHandler.getInstance().getCurrentUser();
        if (currentUser == null) {
            response.sendRedirect(LOGIN_PAGE);
            return;
        }
        request.setAttribute("username", currentUser.getUsername());
        if (currentUser.getRole().equals(UserRole.MANAGER)) {
            List<Restaurant> restaurants = RestaurantHandler.getInstance().searchRestaurantByManagerName(currentUser.getUsername());
            List<String> restaurantNames = restaurants.stream().map(Restaurant::getName).toList();
            request.setAttribute("restaurants", restaurants);
            request.setAttribute("restaurantsTables", RestaurantTableHandler.getInstance().getRestaurantTables(restaurantNames));
            request.getRequestDispatcher(MANAGER_HOME_JSP_PAGE).forward(request, response);;
        } else {
            request.getRequestDispatcher(CLIENT_HOME_JSP_PAGE).forward(request, response);;
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("HomePageController: post request");
        User currentUser = UserHandler.getInstance().getCurrentUser();
        if (currentUser == null) {
            response.sendRedirect(LOGIN_PAGE);
            return;
        }
        if (currentUser.getRole().equals(UserRole.CLIENT)) {
            logger.error(INVALID_USER_ROLE);
            request.setAttribute("error", INVALID_USER_ROLE);
            request.getRequestDispatcher(ERROR_JSP_PAGE).forward(request, response);
            return;
        }

        try {
            Map<String, Object> data = Map.of(
                    RESTAURANT_NAME_KEY, request.getParameter("restaurant_name"),
                    TABLE_NUM_KEY, request.getParameter("table_number"),
                    SEATS_NUM_KEY, request.getParameter("seats_number"),
                    MANAGER_USERNAME_KEY, request.getParameter("manager_name")
            );
            logger.info("data: " + data);
            validateAddTable(data);
            RestaurantTableHandler.getInstance().addRestaurantTable((String) data.get(RESTAURANT_NAME_KEY),
                    Long.parseLong((String) data.get(TABLE_NUM_KEY)),
                    Integer.parseInt((String) data.get(SEATS_NUM_KEY)),
                    (String) data.get(MANAGER_USERNAME_KEY));
            request.setAttribute("username", currentUser.getUsername());
            response.sendRedirect("/");
        } catch (InvalidRequestFormat | InvalidNumType | InvalidRequestTypeFormat
                 | RestaurantManagerNotFound | InvalidUserRole | ManagerUsernameNotMatch | RestaurantNotFound |
                 TableAlreadyExists | NumberFormatException e) {
            logger.error("add table failed: error: " + e.getMessage(), e);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(ERROR_JSP_PAGE).forward(request, response);
        }

    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("HomePageController: get request");
        User currentUser = UserHandler.getInstance().getCurrentUser();
        if (currentUser == null) {
            response.sendRedirect(LOGIN_PAGE);
            return;
        }
        loadPage(request, response);
    }

}
