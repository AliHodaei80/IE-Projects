package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.services.ReservationHandler;
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
import java.util.Map;

import static ir.ie.mizdooni.definitions.Paths.ERROR_JSP_PAGE;
import static ir.ie.mizdooni.definitions.Paths.RESERVATIONS_PAGE;
import static ir.ie.mizdooni.definitions.RequestKeys.RESERVATION_NUM_KEY;
import static ir.ie.mizdooni.definitions.RequestKeys.USERNAME_KEY;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateCancelReservation;

@WebServlet(RESERVATIONS_PAGE)
public class ReservationsPageController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ReservationsPageController.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("ReservationsPageController: get request");
        request.setAttribute("reservations",
                (ReservationHandler.getInstance().showHistoryReservation(UserHandler.getInstance().getCurrentUser().getUsername())));
        request.setAttribute("restaurantIds", RestaurantHandler.getInstance().getRestaurantsIds());
        request.setAttribute("username", UserHandler.getInstance().getCurrentUser().getUsername());
        request.getRequestDispatcher("reservations.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("ReservationsPageController: post request");
        String reservationId = request.getParameter("action");
        String username = request.getParameter("username");
        System.out.println("reservationId: " + reservationId);
        System.out.println("username: " + username);
        try {
            Map<String, Object> data = Map.of(
                    USERNAME_KEY, username,
                    RESERVATION_NUM_KEY, reservationId);
            validateCancelReservation(data);
            ReservationHandler.getInstance().cancelReservation(username, Long.parseLong(reservationId));
        } catch (InvalidRequestFormat | InvalidRequestTypeFormat | ReservationNotForUser | CancellationTimePassed e) {
            logger.error("Cancellation reserve " + reservationId + "failed: error: " + e.getMessage(), e);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(ERROR_JSP_PAGE).forward(request, response);
        }
        response.sendRedirect(RESERVATIONS_PAGE);
    }
}
