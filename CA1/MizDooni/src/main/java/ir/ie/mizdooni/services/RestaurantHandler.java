package ir.ie.mizdooni.services;

public class RestaurantHandler {
    private static RestaurantHandler restaurantHandler;
    private RestaurantHandler() {
    }

    public static RestaurantHandler getInstance() {
        if (restaurantHandler == null)
            restaurantHandler = new RestaurantHandler();
        return restaurantHandler;
    }
}
