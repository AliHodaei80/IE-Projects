package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.commons.Response;
import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.definitions.Successes.RESTAURANT_ADDED_SUCCESSFULLY;
import static ir.ie.mizdooni.definitions.Successes.USER_ADDED_SUCCESSFULLY;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateAddRest;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateAddUser;

@RestController
public class MizDooniRestController {


    private static UserHandler userHandler;
    private static RestaurantTableHandler restaurantTableHandler;
    private static ReservationHandler reservationHandler;
    private static RestaurantHandler restaurantHandler;
    private static ReviewHandler reviewHandler;

    @Autowired
    public MizDooniRestController() {
        userHandler = UserHandler.getInstance();
        restaurantHandler = RestaurantHandler.getInstance();
        reservationHandler = ReservationHandler.getInstance();
        restaurantTableHandler = RestaurantTableHandler.getInstance();
        reviewHandler = ReviewHandler.getInstance();
    }

    // addUser
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<Response> signUp(@RequestBody Map<String, Object> data) {
        try {
            validateAddUser(data);
            userHandler.addUser((String) data.get(USERNAME_KEY),
                    (String) data.get(EMAIL_KEY),
                    (String) data.get(USER_ROLE_KEY),
                    (String) data.get(PASSWORD_KEY),
                    (Map<String, String>) data.get(USER_ADDRESS_KEY));
            return new ResponseEntity<>(new Response(true, USER_ADDED_SUCCESSFULLY), HttpStatus.OK);
        } catch (UserNameAlreadyTaken | InvalidUserRole | EmailAlreadyTaken | InvalidRequestTypeFormat |
                InvalidUsernameFormat | InvalidRequestFormat | InvalidEmailFormat e) {
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // addRestaurant
    @RequestMapping(value = "/addRestaurant", method = RequestMethod.POST)
    public ResponseEntity<Response> addRestaurant(@RequestBody Map<String, Object> data) {
        try {
            validateAddRest(data);
            restaurantHandler.addRestaurant((String) data.get(ADD_RESTAURANT_NAME_KEY),
                    (String) data.get(RESTAURANT_TYPE_KEY),
                    (String) data.get(START_TIME_KEY),
                    (String) data.get(END_TIME_KEY),
                    (String) data.get(MANAGER_USERNAME_KEY),
                    (String) data.get(DESCRIPTION_KEY),
                    (Map<String, String>) data.get(RESTAURANT_ADDRESS_KEY),
                    (String) data.get(RESTAURANT_IMAGE_URL_KEY));
            return new ResponseEntity<>(new Response(true, RESTAURANT_ADDED_SUCCESSFULLY), HttpStatus.OK);
        } catch (RestaurantManagerNotFound e) {
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InvalidUserRole | RestaurentExists | InvalidRequestTypeFormat | InvalidTimeFormat |
                 InvalidRequestFormat e) {
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<Response> test() {
        Map<String, Object> data = new HashMap<>();
        data.put("key1", Map.of("key2", "ok"));
        Response response = new Response(true, data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
