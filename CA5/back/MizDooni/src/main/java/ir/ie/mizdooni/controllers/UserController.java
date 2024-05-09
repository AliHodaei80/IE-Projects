package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.commons.Response;
import ir.ie.mizdooni.exceptions.UserNotFound;
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

@RestController
public class UserController {
    private static UserHandler userHandler;
    private static RestaurantHandler restaurantHandler;

    private final Logger logger;

    @Autowired
    public UserController(UserHandler userHandler, RestaurantHandler restaurantHandler) {
        this.userHandler = userHandler;
        this.restaurantHandler = restaurantHandler;
        this.logger = LoggerFactory.getLogger(UserController.class);
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Response> getUserInfoHandler(@PathVariable String username) {
        Map<String, Object> outputData = new HashMap<>();
        try {
            User user = userHandler.getUserByUsername(username);
            if (user == null)
                throw new UserNotFound();
            outputData.put("user", user);
            // TODO: check if necessary
            logger.info("User Info for User `" + username + "` retrieved successfully");
            return new ResponseEntity<>(new Response(true, outputData), HttpStatus.OK);
        } catch (UserNotFound e) {
            logger.error("User Info retrieve for User `" + username + "` failed: error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/user/{username}/restaurants", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Response> getUserRestaurantHandler(@PathVariable String username) {
        Map<String, Object> outputData = new HashMap<>();
        try {
            User user = userHandler.getUserByUsername(username);
            if (user == null)
                throw new UserNotFound();
            outputData.put("restaurants", restaurantHandler.searchRestaurantByManagerName(username));
            logger.info("User Restaurants for User `" + user.getUsername() + "` retrieved successfully");
            return new ResponseEntity<>(new Response(true, outputData), HttpStatus.OK);
        } catch (UserNotFound e) {
            logger.error("User Restaurants for User `" + username + "`  failed: error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
