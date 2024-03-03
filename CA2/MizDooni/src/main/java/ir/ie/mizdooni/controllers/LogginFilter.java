package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.services.UserHandler;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static ir.ie.mizdooni.definitions.Paths.LOGIN_PAGE;

@WebFilter("/*")
public class LogginFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("LogginFilter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (req.getRequestURI().equals(LOGIN_PAGE)) {
            chain.doFilter(request, response);
            return;
        }
        boolean loggedIn = UserHandler.getInstance().isUserLoggedIn();

        if (loggedIn) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(LOGIN_PAGE);
        }
    }
}
