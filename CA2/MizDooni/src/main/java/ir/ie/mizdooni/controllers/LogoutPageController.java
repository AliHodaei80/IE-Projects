package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.services.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static ir.ie.mizdooni.definitions.Paths.LOGIN_PAGE;
import static ir.ie.mizdooni.definitions.Paths.LOGOUT_PAGE;

@WebServlet(LOGOUT_PAGE)
public class LogoutPageController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("LogoutPageController");
        UserHandler.getInstance().logoutUser();
        response.sendRedirect(LOGIN_PAGE);
    }
}
