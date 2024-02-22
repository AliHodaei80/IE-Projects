package ir.ie.mizdooni.services;

import java.util.Map;

import ir.ie.mizdooni.exceptions.RestaurantNameNotUnique;
import ir.ie.mizdooni.exceptions.RestaurantManagerNotFound;
import ir.ie.mizdooni.exceptions.InvalidUserRole;

import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.storage.Restaurants;
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

    private boolean isManager(String managerUsername) {
        UserRole u = userHandler.getUserRole(managerUsername);
        return (u != null && u.equals(UserRole.MANAGER));
    }

    private boolean managerExists(String managerUsername) {
        UserRole u = userHandler.getUserRole(managerUsername);
        return (u != null);
    }

    public void addRestaurant(String name, String type, String startTime, String endTime, String managerUsername,
                              String description, Map<String, String> address)
            throws RestaurantManagerNotFound, InvalidUserRole {

        if (!managerExists(managerUsername)) {
            throw new RestaurantManagerNotFound();
        }
        if (!(isManager(managerUsername))) {
            throw new InvalidUserRole();
        }
        restaurants.addRestaurant(name, type, Parser.parseTime(startTime, RESTAURANT_TIME_FORMAT),
                Parser.parseTime(endTime, RESTAURANT_TIME_FORMAT), description, managerUsername, address);
    }

    public static RestaurantHandler getInstance() {
        if (restaurantHandler == null)
            restaurantHandler = new RestaurantHandler();
        return restaurantHandler;
    }

}
