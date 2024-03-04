package ir.ie.mizdooni.controllers;

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
import static ir.ie.mizdooni.definitions.Paths.RESTAURANT_PAGE;

@WebServlet(RESTAURANT_PAGE)
public class RestaurantPageController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LoginPageController.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/restaurant.jsp").forward(request, response);
    }

}
