package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.commons.Response;
import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.services.ReservationHandler;
import ir.ie.mizdooni.services.RestaurantHandler;
import ir.ie.mizdooni.services.UserHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static ir.ie.mizdooni.definitions.RequestKeys.RESERVATION_NUM_KEY;
import static ir.ie.mizdooni.definitions.RequestKeys.USERNAME_KEY;
import static ir.ie.mizdooni.definitions.Successes.RESERVATION_CANCELLED_SUCCESSFULLY;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateCancelReservation;

@RestController
public class ReservationRestController {
    private static RestaurantHandler restaurantHandler;
    private static UserHandler userHandler;
    private static ReservationHandler reservationHandler;

    private final Logger logger;

    @Autowired
    public ReservationRestController() {
        restaurantHandler = RestaurantHandler.getInstance();
        userHandler = UserHandler.getInstance();
        reservationHandler = ReservationHandler.getInstance();
        logger = LoggerFactory.getLogger(ReservationRestController.class);
    }

    @RequestMapping(value = "/reservations/{username}", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Response> getReservationHandler(@PathVariable String username) {
        Map<String, Object> outputData = new HashMap<>();
        try {
            User user = userHandler.getUserByUsername(username);
            if (user == null)
                throw new UserNotFound();
            outputData.put("reservations", reservationHandler.showHistoryReservation(username));
            // TODO: check if necessary
//            outputData.put("restaurantIds", restaurantHandler.getRestaurantsIds());
            logger.info("Reservations for User `" + username + "` retrieved successfully");
            return new ResponseEntity<>(new Response(true, outputData), HttpStatus.OK);
        } catch (UserNotFound e) {
            logger.error("Reservations retrieve for User `" + username + "` failed: error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/reservations/{id}/cancel", method = RequestMethod.POST)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Response> cancelReservationHandler(@PathVariable String id,
                                                             @RequestBody Map<String, Object> data) {
        try {
            data.put(RESERVATION_NUM_KEY, id);
            validateCancelReservation(data);
            reservationHandler.cancelReservation((String) data.get(USERNAME_KEY), Long.parseLong(id));
            logger.info("Reservation `" + id + "` cancelled successfully");
            return new ResponseEntity<>(new Response(true, RESERVATION_CANCELLED_SUCCESSFULLY), HttpStatus.OK);
        } catch (InvalidRequestFormat | InvalidRequestTypeFormat | ReservationNotForUser | CancellationTimePassed e) {
            logger.error("Cancellation reserve `" + id + "` failed: error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
