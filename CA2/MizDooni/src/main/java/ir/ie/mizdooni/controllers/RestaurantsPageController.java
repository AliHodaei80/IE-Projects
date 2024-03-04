package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.exceptions.AuthenticationFailed;
import ir.ie.mizdooni.exceptions.UserNotExists;
import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.services.RestaurantHandler;
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

import static ir.ie.mizdooni.definitions.Paths.ERROR_JSP_PAGE;
import static ir.ie.mizdooni.definitions.Paths.RESTAURANTS_PAGE;
import static ir.ie.mizdooni.definitions.Parameters.*;

@WebServlet(RESTAURANTS_PAGE)
public class RestaurantsPageController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LoginPageController.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("restaurants",
                (RestaurantHandler.getInstance().getRestaurants().getRestaurantList(false)));
        logger.info(request.getAttribute("restaurants"));
        request.getRequestDispatcher("restaurants.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter(ACTION_FIELD);
        String search = request.getParameter(SEARCH_FIELD);
        logger.info("RestaurantsPage: get request with action:", action);
        switch (action) {
            case "search_by_name" -> // todo make class for these constants
                request.setAttribute("restaurants", (RestaurantHandler.getInstance()).searchRestaurantByName(search));
            case "search_by_type" ->
                request.setAttribute("restaurants", (RestaurantHandler.getInstance()).searchRestaurantByType(search));
            case "search_by_city" ->
                request.setAttribute("restaurants", (RestaurantHandler.getInstance()).searchRestaurantByCity(search));
            case "sort_by_rate" ->
                request.setAttribute("restaurants",
                        (RestaurantHandler.getInstance().getRestaurants().getRestaurantList(true)));
            case "clear" ->
                request.setAttribute("restaurants",
                        (RestaurantHandler.getInstance().getRestaurants().getRestaurantList(false)));
        }
        logger.info(request.getAttribute("restaurants"));
        request.getRequestDispatcher("restaurants.jsp").forward(request, response);
    }
}
