package ir.ie.mizdooni.storage;

import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.services.UserHandler;

import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Restaurants {
    Map<String, Restaurant> restaurants;

    public Restaurants() {
        restaurants = new HashMap<>();
    }

    public Restaurant getRestaurantByName(String restName) {
        return restaurants.get(restName);
    }

    public void addRestaurant(String restName, String type, LocalTime startTime, LocalTime endTime, String desc,
                              String managerUsername, Map<String, String> address) {
        restaurants.put(restName, new Restaurant(restName, startTime, endTime, type, desc, managerUsername, address));
    }
}
