package ir.ie.mizdooni.controllers;
import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Span;
import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.RestaurantTable;
import org.springframework.web.bind.annotation.*;
import ir.ie.mizdooni.commons.Response;
import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.services.ReservationHandler;
import ir.ie.mizdooni.services.RestaurantHandler;
import ir.ie.mizdooni.services.RestaurantTableHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Map;

import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.definitions.RequestKeys.ADD_RESTAURANT_NAME_KEY;
import static ir.ie.mizdooni.definitions.Successes.TABLE_ADDED_SUCCESSFULLY;
import static ir.ie.mizdooni.validators.RequestSchemaValidator.validateAddTable;

@RestController
public class TableController {
    private static RestaurantTableHandler restaurantTableHandler;
    private static RestaurantHandler restaurantHandler;
    private static ReservationHandler reservationHandler;
    private final Logger logger;

    @Autowired
    public TableController(RestaurantTableHandler restaurantTableHandler, RestaurantHandler restaurantHandler, ReservationHandler reservationHandler) {
        this.restaurantTableHandler = restaurantTableHandler;
        this.restaurantHandler = restaurantHandler;
        this.reservationHandler = reservationHandler;
        this.logger = LoggerFactory.getLogger(TableController.class);
    }

    @RequestMapping(value = "/tables/{restaurantId}/add", method = RequestMethod.POST)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Response> addTableHandler(@PathVariable Long restaurantId,
                                                    @RequestBody Map<String, Object> data,
                                                    @RequestAttribute String JWTUsername) {
        try {
            if (!JWTUsername.equals(data.get(MANAGER_USERNAME_KEY)))
                throw new InvalidAccess();
            Restaurant restaurant = restaurantHandler.getRestaurant(restaurantId);
            if (restaurant == null)
                throw new RestaurantNotFound();
            data.put(RESTAURANT_NAME_KEY, restaurant.getName());
            validateAddTable(data);
            long tableNum = restaurantTableHandler.addRestaurantTable((String) data.get(RESTAURANT_NAME_KEY),
                    (data.get(SEATS_NUM_KEY) instanceof Double) ?
                            ((Double) data.get(SEATS_NUM_KEY)).intValue() :
                            (data.get(SEATS_NUM_KEY) instanceof  String) ? Integer.parseInt((String) data.get(SEATS_NUM_KEY)) :
                                    (Integer) data.get(SEATS_NUM_KEY),
                    (String) data.get(MANAGER_USERNAME_KEY));
            logger.info("Restaurant `" + (String) data.get(RESTAURANT_NAME_KEY) + "` Table `" + tableNum + "` added successfully");
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
        } catch (InvalidAccess e) {
            logger.error("Restaurant `" + (String) data.get(ADD_RESTAURANT_NAME_KEY) +
                    "` Table `" + data.get(TABLE_NUM_KEY) + "` addition failed. error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/tables/{restaurantId}", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Response> getRestaurantTableHandler(@PathVariable Long restaurantId,
                                                              @RequestAttribute String JWTUsername) {
        try {
            Restaurant restaurant = restaurantHandler.getRestaurant(restaurantId);
            if (restaurant == null)
                throw new RestaurantNotFound();
            if (!JWTUsername.equals(restaurant.getManagerUsername()))
                throw new InvalidAccess();
            logger.info("Restaurant `" + restaurant.getName() + "` Tables retrieved successfully");
            return new ResponseEntity<>(new Response(true,
                    Map.of("restaurantTables", new ArrayList<>(restaurantTableHandler.getRestTables(restaurant.getName()))))
                    , HttpStatus.OK);
        } catch (RestaurantNotFound e) {
            logger.error("Restaurant `" + restaurantId + "` Tables retrieve failed. error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InvalidAccess e) {
            logger.error("Restaurant `" + restaurantId + "` Tables retrieve failed. error: " + e.getMessage(), e);
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/tables/reservations", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Response> getRestaurantTableReservationHandler(@RequestBody Map<String, Object> body,
                                                                         @RequestAttribute String JWTUsername) {
        Span parent = ElasticApm.currentSpan();
        Span child = parent.startSpan();
        child.setName("getTableReservations");
        try {
            Restaurant restaurant = restaurantHandler.getRestaurant((Long) body.get(RESTAURANT_ID_KEY));
            if (restaurant == null)
                throw new RestaurantNotFound();
            if (!JWTUsername.equals(restaurant.getManagerUsername()))
                throw new InvalidAccess();
            RestaurantTable table = restaurantTableHandler.getRestaurantTable(restaurant.getName(), (Long) body.get(TABLE_NUM_KEY));
            if (table == null)
                throw new TableDoesntExist();
            logger.info("Restaurant `" + restaurant.getName() + "` Table `" + table.getTableNumber() + "` Reservations retrieved successfully");
            ResponseEntity<Response> t = new ResponseEntity<>(new Response(true,
                    Map.of("reservations", reservationHandler.getTableReservations(restaurant.getName(), table.getTableNumber())))
                    , HttpStatus.OK);
            child.end();
            return t;
        } catch (RestaurantNotFound | TableDoesntExist e) {
            logger.error("Reservations retrieve failed. error: " + e.getMessage(), e);
            child.captureException(e);
            child.end();
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InvalidAccess e) {
            logger.error("Reservations retrieve failed. error: " + e.getMessage(), e);
            child.captureException(e);
            child.end();
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }
}
