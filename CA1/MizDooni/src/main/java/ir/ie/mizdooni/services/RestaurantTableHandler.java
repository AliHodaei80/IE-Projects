package ir.ie.mizdooni.services;

import ir.ie.mizdooni.storage.RestaurantTables;
import ir.ie.mizdooni.storage.Restaurants;

public class RestaurantTableHandler {

    private static RestaurantTableHandler restaurantTableHandler;
    private final Restaurants restaurants;
    private final RestaurantTables restauarantTables;

    private RestaurantTableHandler(Restaurants rests) {
        restaurants = rests;
        restauarantTables = new RestaurantTables();
    }

    public static RestaurantTableHandler getInstance(Restaurants rests) {
        if (restaurantTableHandler == null)
            restaurantTableHandler = new RestaurantTableHandler(rests);
        return restaurantTableHandler;
    }
}
