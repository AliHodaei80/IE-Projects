package ir.ie.mizdooni.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

import ir.ie.mizdooni.exceptions.*;

import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.storage.Restaurants;
import java.util.List;
import ir.ie.mizdooni.utils.Parser;

import static ir.ie.mizdooni.defines.TimeFormats.RESTAURANT_TIME_FORMAT;

public class RestaurantHandler {
    private static RestaurantHandler restaurantHandler;
    private final UserHandler userHandler;
    private final Restaurants restaurants;

    private RestaurantHandler() {
        userHandler = UserHandler.getInstance();
        restaurants = new Restaurants();
    }

    public boolean isManager(String managerUsername) {
        UserRole u = userHandler.getUserRole(managerUsername);
        return (u != null && u.equals(UserRole.MANAGER));
    }

    public Restaurant getRestaurant(String restName) {
        Restaurant res = restaurants.getRestaurantByName(restName);
        return res;
    }

    public List<Restaurant> searchByType(String restType) {
        return restaurants.searchByType(restType);
    }

    public boolean restaurantExists(String restName) {
        return getRestaurant(restName) != null;
    }

    public void addRestaurant(String restName, String type, String startTime, String endTime, String managerUsername,
            String description, Map<String, String> address)
            throws RestaurantManagerNotFound, InvalidUserRole, RestaurentExists {

        if (!userHandler.isUserExist(managerUsername)) {
            throw new RestaurantManagerNotFound();
        }
        if (!(isManager(managerUsername))) {
            throw new InvalidUserRole();
        }
        if ((restaurantExists(restName))) {
            throw new RestaurentExists();
        }
        restaurants.addRestaurant(restName, type, Parser.parseTime(startTime, RESTAURANT_TIME_FORMAT),
                Parser.parseTime(endTime, RESTAURANT_TIME_FORMAT), description, managerUsername, address);
    }

    public boolean dateIsInRestaurantRange(String restName, LocalDateTime dateTime) throws RestaurantNotFound {
        Restaurant restaurant = restaurants.getRestaurantByName(restName);
        if (restaurant == null)
            throw new RestaurantNotFound();
        LocalTime startTime = restaurant.getStartTime();
        LocalTime endTime = restaurant.getEndTime();

        LocalTime timeToCheck = LocalTime.of(dateTime.getHour(), dateTime.getMinute());
        return (timeToCheck.isAfter(startTime) && timeToCheck.isBefore(endTime)) ||
                timeToCheck.equals(startTime) || timeToCheck.equals(endTime);
    }

    public static RestaurantHandler getInstance() {
        if (restaurantHandler == null)
            restaurantHandler = new RestaurantHandler();
        return restaurantHandler;
    }

}
