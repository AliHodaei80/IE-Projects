package ir.ie.mizdooni.services;

import ir.ie.mizdooni.exceptions.InvalidUserRole;
import ir.ie.mizdooni.exceptions.RestaurantManagerNotFound;
import ir.ie.mizdooni.exceptions.RestaurantNotFound;
import ir.ie.mizdooni.exceptions.TableAlreadyExists;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.storage.RestaurantTables;

public class RestaurantTableHandler {

    private static RestaurantTableHandler restaurantTableHandler;
    private final RestaurantHandler restaurantsHandler;
    private final UserHandler userHandler;
    private final RestaurantTables restaurantTables;

    private RestaurantTableHandler(RestaurantHandler restsHandler, UserHandler usrHandler) {
        restaurantsHandler = restsHandler;
        userHandler = usrHandler;
        restaurantTables = new RestaurantTables();
    }

    public void addRestaurantTable(String restName, Long tableNo, Long seatsNo, String managerUsername)
            throws RestaurantManagerNotFound, InvalidUserRole, RestaurantNotFound, TableAlreadyExists {
        if (!restaurantsHandler.restaurantExists(restName)) {
            throw new RestaurantNotFound();
        }
        if (!((userHandler.getUserRole(managerUsername) == (UserRole.MANAGER))
                && (restaurantsHandler.isManager(managerUsername)))) {
            throw new InvalidUserRole();
        }
        if (restaurantTables.tableExists(restName, tableNo)) {
            throw new TableAlreadyExists();
        }
        restaurantTables.addRestaurantTable(restName, managerUsername, tableNo, seatsNo);
        System.out.println(restaurantTables.getAllTables());
    }

    public static RestaurantTableHandler getInstance(RestaurantHandler restaurantHandler,
                                                     UserHandler userHandler) {
        if (restaurantTableHandler == null)
            restaurantTableHandler = new RestaurantTableHandler(restaurantHandler, userHandler);
        return restaurantTableHandler;
    }
}
