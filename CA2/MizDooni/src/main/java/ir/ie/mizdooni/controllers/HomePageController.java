package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.models.UserRole;
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

import static ir.ie.mizdooni.definitions.Paths.*;

@WebServlet("")
public class HomePageController  extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(HomePageController.class);
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("HomePageController: get request");
        User currentUser = UserHandler.getInstance().getCurrentUser();
        RequestDispatcher requestDispatcher;
        if (currentUser == null) {
            response.sendRedirect(LOGIN_PAGE);
            return;
        }
        if (currentUser.getRole().equals(UserRole.MANAGER)) {
            requestDispatcher = request.getRequestDispatcher(MANAGER_HOME_JSP_PAGE);
            requestDispatcher.forward(request, response);

        } else {
            response.sendRedirect(CLIENT_HOME_JSP_PAGE);
        }
    }

}
