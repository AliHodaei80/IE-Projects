package ir.ie.mizdooni.controllers;

import ir.ie.mizdooni.commons.Response;
import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.RestaurantTable;
import ir.ie.mizdooni.services.ReservationHandler;
import ir.ie.mizdooni.services.RestaurantHandler;
import ir.ie.mizdooni.services.RestaurantTableHandler;
import ir.ie.mizdooni.services.ReviewHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ir.ie.mizdooni.definitions.Parameters.ACTION_FIELD;
import static ir.ie.mizdooni.definitions.Parameters.SEARCH_FIELD;
import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.definitions.Successes.RESTAURANT_ADDED_SUCCESSFULLY;
import static ir.ie.mizdooni.definitions.Successes.REVIEW_ADDED_SUCCESSFULLY;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.*;

@RestController
public class RestaurantRestController {

    private static RestaurantHandler restaurantHandler;
    private static ReviewHandler reviewHandler;
    private static ReservationHandler reservationHandler;

    private final Logger logger;

    @Autowired
    public RestaurantRestController() {
        restaurantHandler = RestaurantHandler.getInstance();
        reviewHandler = ReviewHandler.getInstance();
        reservationHandler = ReservationHandler.getInstance();
        logger = LoggerFactory.getLogger(RestaurantRestController.class);
    }

    // addRestaurant
    @RequestMapping(value = "/restaurants/add", method = RequestMethod.POST)
    public ResponseEntity<Response> addRestaurantHandler(@RequestBody Map<String, Object> data) {
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
            logger.info("Restaurant `" + (String) data.get(ADD_RESTAURANT_NAME_KEY) + "` added successfully");
            return new ResponseEntity<>(new Response(true, RESTAURANT_ADDED_SUCCESSFULLY), HttpStatus.OK);
        } catch (RestaurantManagerNotFound e) {
            logger.error("Restaurant `" + (String) data.get(ADD_RESTAURANT_NAME_KEY) +
                    "` addition failed. error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InvalidUserRole | RestaurentExists | InvalidRequestTypeFormat | InvalidTimeFormat |
                 InvalidRequestFormat e) {
            logger.error("Restaurant `" + (String) data.get(ADD_RESTAURANT_NAME_KEY) +
                    "` addition failed. error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // RestaurantsPageController GET
    @RequestMapping(value = "/restaurants", method = RequestMethod.GET)
    public ResponseEntity<Response> getRestaurantsHandler() {
        Map<String, Object> outputData = new HashMap<>();
        List<Restaurant> restaurantList = restaurantHandler.getRestaurants().getRestaurantList(false);
        outputData.put("restaurants", restaurantList);
        logger.info("Restaurants retrieved successfully");
        return new ResponseEntity<>(new Response(true, outputData), HttpStatus.OK);
    }

    // RestaurantsPageController post
    @RequestMapping(value = "/restaurants/search", method = RequestMethod.POST)
    public ResponseEntity<Response> searchRestaurantHandler(@RequestBody Map<String, Object> data) {
        String action = (String) data.get(ACTION_FIELD);
        String search = (String) data.get(SEARCH_FIELD);
        Map<String, Object> outputData = new HashMap<>();
        switch (action) {
            case "search_by_name" -> outputData.put("restaurants", restaurantHandler.searchRestaurantByName(search));
            case "search_by_type" -> outputData.put("restaurants", restaurantHandler.searchRestaurantByType(search));
            case "search_by_city" -> outputData.put("restaurants", restaurantHandler.searchRestaurantByCity(search));
            case "sort_by_rate" -> outputData.put("restaurants",
                    restaurantHandler.getRestaurants().getRestaurantList(true));
            default -> outputData.put("restaurants",
                    (RestaurantHandler.getInstance().getRestaurants().getRestaurantList(false)));
        }
        logger.info("Restaurants filtered (`" + action + ", `" + search + "`) retrieved successfully");
        return new ResponseEntity<>(new Response(true, outputData), HttpStatus.OK);
    }

    // RestaurantPageController POST
    @RequestMapping(value = "/restaurants/{id}/feedback", method = RequestMethod.POST)
    public ResponseEntity<Response> feedbackRestaurantHandler(@PathVariable String id,
                                                              @RequestBody Map<String, Object> data) {
        try {
            Restaurant restaurant = restaurantHandler.getRestaurant(Long.parseLong(id));
            data.put(RESTAURANT_NAME_KEY, restaurant.getName());
            validateAddReview(data);
            Double ambianceRate = Double.parseDouble((String) data.get(AMBIANCE_RATE_KEY));
            Double overallRate = Double.parseDouble((String) data.get(OVERALL_RATE_KEY));
            Double foodRate = Double.parseDouble((String) data.get(FOOD_RATE_KEY));
            String comment = (String) data.get(COMMENT_KEY);
            Double serviceRate = Double.parseDouble((String) data.get(SERVICE_RATE_KEY));
            String username = (String) data.get(USERNAME_KEY);
            reviewHandler.addReview(restaurant.getName(), username, ambianceRate, overallRate, serviceRate, foodRate, comment);
            logger.info("feedback of User`" + username + "` for Restaurant `" + restaurant.getName() + "`added successfully");
            return new ResponseEntity<>(new Response(true, REVIEW_ADDED_SUCCESSFULLY), HttpStatus.OK);
        } catch (InvalidUserRole | InvalidRequestTypeFormat |
                 InvalidRequestFormat | InvalidRatingFormat | NoReservationBefore e) {
            logger.error("feedback failed: error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (UserNotFound | RestaurantNotFound e) {
            logger.error("feedback failed: error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    // RestaurantPageController POST
    @RequestMapping(value = "/restaurants/{id}/reserve", method = RequestMethod.POST)
    public ResponseEntity<Response> reserveRestaurantHandler(@PathVariable String id,
                                                             @RequestBody Map<String, Object> data) {
        try {
            Restaurant restaurant = restaurantHandler.getRestaurant(Long.parseLong(id));
            if (restaurant == null)
                throw new RestaurantNotFound();
            String restName = restaurant.getName();
            if (!data.containsKey(DATETIME_KEY) || ((String) data.get(DATETIME_KEY)).isEmpty()) {
                throw new InvalidRequestFormat(DATETIME_KEY);
            }
            LocalDateTime localDateTime = LocalDateTime.parse((String) data.get(DATETIME_KEY));
            String localDateTimeString = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(localDateTime);
            Map<String, Object> modifiedData = Map.of(
                    RESTAURANT_NAME_KEY, restName,
                    USERNAME_KEY, (String) data.get(USERNAME_KEY),
                    TABLE_NUM_KEY, Long.parseLong((String) data.get(TABLE_NUM_KEY)),
                    DATETIME_KEY, localDateTimeString
            );
            validateReserveTable(modifiedData);
            long reservationNum = reservationHandler.addReservation(
                    (String) modifiedData.get(RESTAURANT_NAME_KEY),
                    (String) modifiedData.get(USERNAME_KEY),
                    (Long) modifiedData.get(TABLE_NUM_KEY),
                    (String) modifiedData.get(DATETIME_KEY));
            logger.info("Reservation `" + reservationNum + "` added successfully");
            Map<String, Object> outputData = new HashMap<>();
            outputData.put("reservationNumber", reservationNum);
            return new ResponseEntity<>(new Response(true, outputData), HttpStatus.OK);
        } catch (InvalidRequestFormat | InvalidTimeFormat | InvalidRequestTypeFormat |
                 InvalidUserRole | TableDoesntExist | TableAlreadyReserved | InvalidDateTime | DateTimeNotInRange e) {
            logger.error("Reservation failed: error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RestaurantNotFound | UserNotExists e) {
            logger.error("Reservation failed: error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    // RestaurantPageController GET
    @RequestMapping(value = "/restaurants/{id}", method = RequestMethod.GET)
    public ResponseEntity<Response> getRestaurantHandler(@PathVariable String id) {
        Map<String, Object> outputData = new HashMap<>();
        try {
            Restaurant restaurant = restaurantHandler.getRestaurant(Long.parseLong(id));
            if (restaurant == null)
                throw new RestaurantNotFound();
            outputData.put("restaurant", restaurant);
            List<RestaurantTable> restaurantTables = new ArrayList<>(RestaurantTableHandler.getInstance().getRestTables(restaurant.getName()));
            outputData.put("restaurantTables", restaurantTables);
            outputData.put("reviews", reviewHandler.getRestReviews(restaurant.getName()));
            logger.info("Restaurant `" + restaurant.getName() + "` retrieved successfully");
            return new ResponseEntity<>(new Response(true, outputData), HttpStatus.OK);
        } catch (RestaurantNotFound e) {
            logger.error("Restaurant retrieve failed: error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}