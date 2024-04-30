package ir.ie.mizdooni.controllers;
import org.springframework.web.bind.annotation.CrossOrigin;
import ir.ie.mizdooni.commons.Response;
import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.services.ReservationHandler;
import ir.ie.mizdooni.services.RestaurantHandler;
import ir.ie.mizdooni.services.RestaurantTableHandler;
import ir.ie.mizdooni.services.ReviewHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.definitions.RequestKeys.ADD_RESTAURANT_NAME_KEY;
import static ir.ie.mizdooni.definitions.Successes.RESTAURANT_ADDED_SUCCESSFULLY;
import static ir.ie.mizdooni.definitions.Successes.TABLE_ADDED_SUCCESSFULLY;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateAddRest;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateAddTable;

@RestController
public class TableController {
    private static RestaurantTableHandler restaurantTableHandler;
    private final Logger logger;

    @Autowired
    public TableController() {
        restaurantTableHandler = RestaurantTableHandler.getInstance();
        logger = LoggerFactory.getLogger(TableController.class);
    }

    @RequestMapping(value = "/tables/add", method = RequestMethod.POST)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Response> addTableHandler(@RequestBody Map<String, Object> data) {
        try {
            validateAddTable(data);
            restaurantTableHandler.addRestaurantTable((String) data.get(RESTAURANT_NAME_KEY),
                    Long.parseLong((String) data.get(TABLE_NUM_KEY)),
                    Integer.parseInt((String) data.get(SEATS_NUM_KEY)),
                    (String) data.get(MANAGER_USERNAME_KEY));
            logger.info("Restaurant `" + (String) data.get(RESTAURANT_NAME_KEY) + "` Table `" + data.get(TABLE_NUM_KEY) + "` added successfully");
            return new ResponseEntity<>(new Response(true, TABLE_ADDED_SUCCESSFULLY), HttpStatus.OK);
        } catch (RestaurantManagerNotFound | RestaurantNotFound e) {
            logger.error("Restaurant `" + (String) data.get(ADD_RESTAURANT_NAME_KEY) +
                    "` Table `" + data.get(TABLE_NUM_KEY) + "` addition failed. error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InvalidUserRole | InvalidNumType | InvalidRequestTypeFormat  |
                 InvalidRequestFormat | TableAlreadyExists | ManagerUsernameNotMatch e) {
            logger.error("Restaurant `" + (String) data.get(ADD_RESTAURANT_NAME_KEY) +
                    "` addition failed. error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
