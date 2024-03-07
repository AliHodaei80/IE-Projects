package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.commons.Request;
import ir.ie.mizdooni.commons.Response;
import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.models.Opening;
import ir.ie.mizdooni.models.Reservation;
import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.Review;
import ir.ie.mizdooni.services.*;
import ir.ie.mizdooni.utils.Parser;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ir.ie.mizdooni.definitions.Commands.*;
import static ir.ie.mizdooni.definitions.Errors.RESTUARANT_NOT_FOUND;
import static ir.ie.mizdooni.definitions.Errors.UNSUPPORTED_COMMAND;
import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.definitions.ResponseKeys.*;
import static ir.ie.mizdooni.definitions.Successes.*;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validate;

public class MizDooniController {
    private static UserHandler userHandler;
    private static RestaurantTableHandler restaurantTableHandler;
    private static ReservationHandler reservationHandler;
    private static RestaurantHandler restaurantHandler;
    private static ReviewHandler reviewHandler;

    private static MizDooniController mizDooniController;

    private MizDooniController() {
        userHandler = UserHandler.getInstance();
        restaurantHandler = RestaurantHandler.getInstance();
        reservationHandler = ReservationHandler.getInstance();
        restaurantTableHandler = RestaurantTableHandler.getInstance();
        reviewHandler = ReviewHandler.getInstance();

        // getInstance();

    }

    public static MizDooniController getInstance() {
        if (mizDooniController == null) {
            mizDooniController = new MizDooniController();
        }
        return mizDooniController;
    }

    public Response addUser(Map<String, Object> data) {
        try {
            userHandler.addUser((String) data.get(USERNAME_KEY),
                    (String) data.get(EMAIL_KEY),
                    (String) data.get(USER_ROLE_KEY),
                    (String) data.get(PASSWORD_KEY),
                    (Map<String, String>) data.get(USER_ADDRESS_KEY));
            return new Response(true, USER_ADDED_SUCCESSFULLY);
        } catch (
                UserNameAlreadyTaken | InvalidUserRole | EmailAlreadyTaken e) {
            return new Response(false, e.getMessage());
        }

    }

    public Response addRestaurant(Map<String, Object> data) {
        try {
            restaurantHandler.addRestaurant((String) data.get(ADD_RESTAURANT_NAME_KEY),
                    (String) data.get(RESTAURANT_TYPE_KEY),
                    (String) data.get(START_TIME_KEY),
                    (String) data.get(END_TIME_KEY),
                    (String) data.get(MANAGER_USERNAME_KEY),
                    (String) data.get(DESCRIPTION_KEY),
                    (Map<String, String>) data.get(RESTAURANT_ADDRESS_KEY));
            return new Response(true, RESTAURANT_ADDED_SUCCESSFULLY);
        } catch (
                InvalidUserRole | RestaurantManagerNotFound | RestaurentExists e) {
            return new Response(false, e.getMessage());
        }

    }

    public Response addRestaurantTable(Map<String, Object> data) {
        try {
            restaurantTableHandler.addRestaurantTable((String) data.get(RESTAURANT_NAME_KEY),
                    (Long) (Math.round((Double) (data.get(TABLE_NUM_KEY)))),
                    (int) (Math.round((Double) (data.get(SEATS_NUM_KEY)))),
                    (String) data.get(MANAGER_USERNAME_KEY));
            return new Response(true, TABLE_ADDED_SUCCESSFULLY);
        } catch (
                InvalidUserRole | RestaurantManagerNotFound | TableAlreadyExists | RestaurantNotFound
                | ManagerUsernameNotMatch e) {
            return new Response(false, e.getMessage());
        }
    }

    public Response reserveTable(Map<String, Object> data) {
        try {
            Map<String, Object> resultDate = new HashMap<>();
            long reservationNum = reservationHandler.addReservation((String) data.get(RESTAURANT_NAME_KEY),
                    (String) data.get(USERNAME_KEY),
                    ((Double) data.get(TABLE_NUM_KEY)).intValue(),
                    (String) data.get(DATETIME_KEY));
            resultDate.put(RESERVATION_NUM_KEY, reservationNum);

            return new Response(true, resultDate);
        } catch (
                InvalidUserRole | UserNotExists | TableAlreadyReserved | RestaurantNotFound
                | TableDoesntExist | InvalidDateTime | DateTimeNotInRange e) {
            return new Response(false, e.getMessage());
        }
    }

    public Response searchRestaurantByType(Map<String, Object> data) {
        List<Restaurant> results = restaurantHandler.searchRestaurantByType((String) data.get(RESTAURANT_TYPE_KEY));
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put(RESTAURANTS_KEY, results);
        Response res = new Response(true, responseBody);
        return res;
    }

    public Response searchRestaurantByName(Map<String, Object> data) {
        List<Restaurant> results = restaurantHandler
                .searchRestaurantByName((String) data.get(RESTAURANT_SEARCH_NAME_KEY));
        if (results.isEmpty()) {
            return new Response(false, RESTUARANT_NOT_FOUND);
        }
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put(RESTAURANTS_KEY, results);
        return new Response(true, responseBody);
    }

    public Response cancelReservation(Map<String, Object> data) {
        try {
            reservationHandler.cancelReservation((String) data.get(USERNAME_KEY),
                    (Long) (Math.round((Double) (data.get(RESERVATION_NUM_KEY)))));
            return new Response(true, RESERVATION_CANCELLED_SUCCESSFULLY);
        } catch (CancellationTimePassed | ReservationNotForUser e) {
            return new Response(false, e.getMessage());
        }
    }

    public Response showReservationHistory(Map<String, Object> data) {
        List<Reservation> userReservations = reservationHandler.showHistoryReservation((String) data.get(USERNAME_KEY));
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(RESERVATION_HISTORY_KEY, userReservations);
        return new Response(true, responseBody);
    }

    public Response showAvailableTables(Map<String, Object> data) {
        try {
            Map<String, Object> responseBody = new HashMap<>();
            List<Opening> availableOpenings = reservationHandler.findAvailableTables(
                    (String) data.get(RESTAURANT_NAME_KEY),
                    LocalDateTime.now());
            responseBody.put(AVAILABLE_TABLES_KEY, availableOpenings);
            return new Response(true, responseBody);
        } catch (RestaurantNotFound e) {
            return new Response(false, e.getMessage());
        }
    }

    public Response addReview(Map<String, Object> data) {
        try {
            Review review = reviewHandler.addReview((String) data.get(RESTAURANT_NAME_KEY),
                    (String) data.get(USERNAME_KEY),
                    (Double) data.get(AMBIANCE_RATE_KEY),
                    (Double) data.get(OVERALL_RATE_KEY),
                    (Double) data.get(SERVICE_RATE_KEY),
                    (Double) data.get(FOOD_RATE_KEY),
                    (String) data.get(COMMENT_KEY));
            return new Response(true, REVIEW_ADDED_SUCCESSFULLY);
        } catch (InvalidUserRole | UserNotFound | RestaurantNotFound | NoReservationBefore e) {
            return new Response(false, e.getMessage());

        }
    }

    public Response handleRequest(Request request) {
        String op = request.getOperation();
        Map<String, Object> data = request.getData();
        try {
            validate(request);
        } catch (InvalidUsernameFormat | InvalidRequestFormat | InvalidEmailFormat | InvalidTimeFormat
                | InvalidRatingFormat | InvalidNumType | InvalidRequestTypeFormat e) {
            return new Response(false, e.getMessage());
        }
        switch (op) {
            case OP_ADD_USER:
                return addUser(data);
            case OP_ADD_RESTAURANT:
                return addRestaurant(data);
            case OP_ADD_TABLE:
                return addRestaurantTable(data);
            case OP_RESERVE_TABLE:
                return reserveTable(data);
            case OP_SEARCH_RESTAURANT_BY_TYPE:
                return searchRestaurantByType(data);
            case OP_SEARCH_RESTAURANT_BY_NAME:
                return searchRestaurantByName(data);
            case OP_CANCEL_RESERVATION:
                return cancelReservation(data);
            case OP_SHOW_RESERVATION_HISTORY:
                return showReservationHistory(data);
            case OP_ADD_REVIEW:
                return addReview(data);
            case OP_SHOW_AVAILABLE_TABLES:
                return showAvailableTables(data);
            default:
                return new Response(false, UNSUPPORTED_COMMAND);
        }
    }
}
