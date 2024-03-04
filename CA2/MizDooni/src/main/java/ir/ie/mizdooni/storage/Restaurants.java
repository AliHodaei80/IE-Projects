package ir.ie.mizdooni.storage;

import ir.ie.mizdooni.definitions.Locations;
import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.services.UserHandler;
import ir.ie.mizdooni.storage.commons.Container;

import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;

public class Restaurants extends Container<Restaurants> {
    Map<String, Restaurant> restaurants;
    long restaurantCount;

    public Restaurants() {
        restaurants = new HashMap<>();
        restaurantCount = 0;
    }

    public boolean typeSearchFilter(Restaurant rest, String type) {
        return rest.getType().equals(type);
    }

    public boolean nameSearchFilter(Restaurant rest, String type) {
        return rest.getName().contains(type);
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

    public List<Restaurant> searchByManagerName(String managerName) {
        return new ArrayList<>(restaurants.values())
                .stream()
                .filter(rest -> managerName.equals(rest.getManagerUsername()))
                .collect(Collectors.toList());
    }

    public void addRestaurant(String restName, String type, LocalTime startTime, LocalTime endTime, String desc,
                              String managerUsername, Map<String, String> address) {
        restaurantCount++;
        restaurants.put(restName, new Restaurant(restName, startTime, endTime, type, desc, managerUsername, address, restaurantCount));
        this.saveToFile(Locations.RESTAURANTS_LOCATION);
    }

    public Map<String, Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Map<String, Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
