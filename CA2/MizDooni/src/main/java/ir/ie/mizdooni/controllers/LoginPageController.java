package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.exceptions.AuthenticationFailed;
import ir.ie.mizdooni.exceptions.UserNotExists;
import ir.ie.mizdooni.services.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static ir.ie.mizdooni.definitions.Paths.ERROR_JSP_PAGE;
import static ir.ie.mizdooni.definitions.Paths.LOGIN_PAGE;

@WebServlet(LOGIN_PAGE)
public class LoginPageController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LoginPageController.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("LoginPageController: get request");
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("LoginPageController: post request");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        logger.info("username: " + username);
        try {
            UserHandler.getInstance().loginUser(username, password);
            logger.info("Login success");
            response.sendRedirect("/");
        } catch (AuthenticationFailed | UserNotExists e) {
            logger.error("Login failed. error: " + e.getMessage(), e);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(ERROR_JSP_PAGE).forward(request, response);
        }
    }
}
