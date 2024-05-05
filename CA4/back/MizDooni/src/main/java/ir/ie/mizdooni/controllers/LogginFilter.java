package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.services.UserHandler;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static ir.ie.mizdooni.definitions.Paths.*;

public class LogginFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LogginFilter.class);

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("LogginFilter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (req.getRequestURI().equals(LOGIN_URL) || req.getRequestURI().equals(SIGNUP_URL) ||
                req.getRequestURI().equals(RESTAURANTS_SEARCH_URL) || req.getRequestURI().equals(RESTAURANTS_URL) ||
                req.getRequestURI().matches(RESTAURANT_URL_REGEX) || req.getRequestURI().matches(RESTAURANT_REVIEW_URL_REGEX)) {
            chain.doFilter(request, response);
            return;
        }
        boolean loggedIn = UserHandler.getInstance().isUserLoggedIn();

        if (loggedIn) {
            chain.doFilter(request, response);
        } else {
            res.sendError(401, "Unauthorized");
        }
    }
}
