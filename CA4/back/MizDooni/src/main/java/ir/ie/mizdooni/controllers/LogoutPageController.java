package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.services.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static ir.ie.mizdooni.definitions.Paths.LOGIN_PAGE;
import static ir.ie.mizdooni.definitions.Paths.LOGOUT_PAGE;

@WebServlet(LOGOUT_PAGE)
public class LogoutPageController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LogoutPageController.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("LogoutPageController: get request");
        UserHandler.getInstance().logoutUser();
        response.sendRedirect(LOGIN_PAGE);
    }
}
