package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.services.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static ir.ie.mizdooni.definitions.Paths.LOGIN_JSP_PAGE;
import static ir.ie.mizdooni.definitions.Paths.LOGIN_PAGE;

@WebServlet(LOGIN_PAGE)
public class LoginPageController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("LoginPageController");
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("LoginPageController");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username: " + username + " password: " + password);
        if (UserHandler.getInstance().doesUserExist(username)) {
            if (UserHandler.getInstance().isPasswordCorrect(username, password)) {
                UserHandler.getInstance().loginUser(username);
                System.out.println("Login success");
                response.sendRedirect("/MizDooni-0.0.1-SNAPSHOT");
            } else {
                System.out.println("Login failed");

                response.sendRedirect(LOGIN_JSP_PAGE);
            }
        } else {
            System.out.println("User not exist");

            response.sendRedirect(LOGIN_JSP_PAGE);
        }
    }
}
