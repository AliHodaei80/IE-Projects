package ir.ie.mizdooni.controllers;

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

import static ir.ie.mizdooni.definitions.Paths.*;

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
            response.sendRedirect(CLIENT_HOME_JSP_PAGE);
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
