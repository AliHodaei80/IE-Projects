package ir.ie.mizdooni.services;

public class RestaurantTableHandler {

    private static RestaurantTableHandler restaurantTableHandler;
    private RestaurantTableHandler() {
    }

    public static RestaurantTableHandler getInstance() {
        if (restaurantTableHandler == null)
            restaurantTableHandler = new RestaurantTableHandler();
        return restaurantTableHandler;
    }
}
