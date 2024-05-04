package ir.ie.mizdooni.services; 

import ir.ie.mizdooni.definitions.DataBaseUrlPath;
import ir.ie.mizdooni.definitions.Locations;
import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.models.RestaurantTable;
import ir.ie.mizdooni.storage.RestaurantTables;
import ir.ie.mizdooni.storage.commons.Container;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class RestaurantTableHandler {

    private static RestaurantTableHandler restaurantTableHandler;
    private final RestaurantHandler restaurantsHandler;
    private final UserHandler userHandler;
    private final RestaurantTables restaurantTables;

    private RestaurantTableHandler() {
        restaurantsHandler = RestaurantHandler.getInstance();
        userHandler = UserHandler.getInstance();
        restaurantTables = new RestaurantTables().loadFromUrl(DataBaseUrlPath.RESTAURANT_TABLES_DATABASE_URL);
    }

    public Long addRestaurantTable(String restName, int seatsNo, String managerUsername)
            throws RestaurantManagerNotFound, InvalidUserRole, RestaurantNotFound, TableAlreadyExists, ManagerUsernameNotMatch {
        if (!restaurantsHandler.restaurantExists(restName)) {
            throw new RestaurantNotFound();
        }
        if (!userHandler.doesUserExist(managerUsername)) {
            throw new RestaurantManagerNotFound();
        }
        if (!(restaurantsHandler.isManager(managerUsername))) {
            throw new InvalidUserRole();
        }
        if (!restaurantsHandler.getRestaurant(restName).getManagerUsername().equals(managerUsername)) {
            throw new ManagerUsernameNotMatch();
        }
        return restaurantTables.addRestaurantTable(restName, managerUsername, seatsNo);
    }

    public List<RestaurantTable> getRestaurantTables(String restName) {
        return restaurantTables.getRestaurantTables(restName);
    }

    public Map<String, Map<Long, RestaurantTable>> getRestaurantTables(List<String> restNames) {
        return restaurantTables.getRestaurantTables(restNames);
    }

    public Collection<RestaurantTable> getRestTables(String restName) throws RestaurantNotFound {
        if (restaurantsHandler.restaurantExists(restName)) {
            return restaurantTables.getRestTables(restName);
        } else {
            throw new RestaurantNotFound();
        }
    }

    public static RestaurantTableHandler getInstance() {
        if (restaurantTableHandler == null)
            restaurantTableHandler = new RestaurantTableHandler();
        return restaurantTableHandler;
    }

    public boolean doesTableExist(String restName, Long tableNumber) {
        return restaurantTables.getRestaurantTable(restName, tableNumber) != null;
    }

    public RestaurantTables getRestaurantTables() {
        return restaurantTables;
    }
}
