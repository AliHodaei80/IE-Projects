package ir.ie.mizdooni.storage;

import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.services.UserHandler;

import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;

public class Restaurants {
    Map<String, Restaurant> restaurants;

    public Restaurants() {
        restaurants = new HashMap<>();
    }

    public boolean typeSearchFilter(Restaurant rest, String type) {
        return rest.getType().equals(type);
    }

    public boolean nameSearchFilter(Restaurant rest, String type) {
        return rest.getName().equals(type);
    }

    public Restaurant getRestaurantByName(String restName) {
        return restaurants.get(restName);
    }

    public List<Restaurant> searchByType(String type) {
        return new ArrayList<>(restaurants.values())
                .stream()
                .filter(rest -> typeSearchFilter(rest, type))
                .collect(Collectors.toList());
    }

    public List<Restaurant> searchByName(String restName) {
        return new ArrayList<>(restaurants.values())
                .stream()
                .filter(rest -> nameSearchFilter(rest, restName))
                .collect(Collectors.toList());
    }

    public void addRestaurant(String restName, String type, LocalTime startTime, LocalTime endTime, String desc,
            String managerUsername, Map<String, String> address) {
        restaurants.put(restName, new Restaurant(restName, startTime, endTime, type, desc, managerUsername, address));
    }
}
