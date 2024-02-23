package ir.ie.mizdooni.services;

import ir.ie.mizdooni.storage.RestaurantTables;
import ir.ie.mizdooni.storage.Restaurants;

public class RestaurantTableHandler {

    private static RestaurantTableHandler restaurantTableHandler;
    private final RestaurantHandler restaurantsHandler;
    private final RestaurantTables restauarantTables;

    private RestaurantTableHandler(RestaurantHandler restsHandler) {
        restaurantsHandler = restsHandler;
        restauarantTables = new RestaurantTables();
    }

    public static RestaurantTableHandler getInstance(RestaurantHandler restaurantHandler) {
        if (restaurantTableHandler == null)
            restaurantTableHandler = new RestaurantTableHandler(restaurantHandler);
        return restaurantTableHandler;
    }
}
