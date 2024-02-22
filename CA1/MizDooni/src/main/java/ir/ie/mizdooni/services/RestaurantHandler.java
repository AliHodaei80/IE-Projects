package ir.ie.mizdooni.services;

import java.util.Map;

import ir.ie.mizdooni.exceptions.RestaurantNameNotUnique;
import ir.ie.mizdooni.exceptions.RestaurantManagerNotFound;
import ir.ie.mizdooni.exceptions.InvalidUserRole;

import ir.ie.mizdooni.models.UserRole;

public class RestaurantHandler {
    private static RestaurantHandler restaurantHandler;
    private static UserHandler userHandler;

    private RestaurantHandler() {
    }

    private boolean isManager(String managerUsername) {
        UserRole u = userHandler.getUserRole(managerUsername);
        return (u != null && u.equals(UserRole.MANAGER));
    }

    private boolean managerExists(String managerUsername) {
        UserRole u = userHandler.getUserRole(managerUsername);
        return (u != null);
    }

    public void addRestaurant(String name,
            String startTimeString,
            String endTimeString,
            String managerUsernameString,
            String descriptionString,
            Map<String, String> address)
            throws RestaurantManagerNotFound, InvalidUserRole {
        if (!managerExists(managerUsernameString)) {
            throw new RestaurantManagerNotFound();
        }
        if (!(isManager(managerUsernameString))) {
            throw new InvalidUserRole();
        }
        // users.addUser(username, email, UserRole.getUserRole(role), password,
        // address);
    }

    public static RestaurantHandler getInstance() {
        if (restaurantHandler == null)
            restaurantHandler = new RestaurantHandler();
        return restaurantHandler;
    }

}
