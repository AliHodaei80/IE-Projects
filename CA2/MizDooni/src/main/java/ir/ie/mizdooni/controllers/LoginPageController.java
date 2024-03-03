package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.exceptions.AuthenticationFailed;
import ir.ie.mizdooni.exceptions.UserNotExists;
import ir.ie.mizdooni.services.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static ir.ie.mizdooni.definitions.Paths.ERROR_JSP_PAGE;
import static ir.ie.mizdooni.definitions.Paths.LOGIN_PAGE;

@WebServlet(LOGIN_PAGE)
public class LoginPageController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("LoginPageController");
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("LoginPageController");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username: " + username + " password: " + password);
        try {
            UserHandler.getInstance().loginUser(username, password);
            System.out.println("Login success");
            response.sendRedirect("/");
        } catch (AuthenticationFailed | UserNotExists e) {
            System.out.println("Login failed. error: " + e.getMessage());
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(ERROR_JSP_PAGE).forward(request, response);
        }
    }
}
