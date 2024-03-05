package ir.ie.mizdooni.storage;

import ir.ie.mizdooni.definitions.Locations;
import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.services.UserHandler;
import ir.ie.mizdooni.storage.commons.Container;

import java.sql.Date;
import java.time.LocalTime;
import java.util.*;
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

    public boolean citySearchFilter(Restaurant rest, String city) {
        return rest.getCity().equals(city);
    }

    public boolean nameSearchFilter(Restaurant rest, String type) {
        return rest.getName().contains(type);
    }

    public Restaurant getRestaurantByName(String restName) {
        return restaurants.get(restName);
    }

    public Restaurant getRestaurantById(Long desiredId) {
        Optional<Restaurant> optionalRestaurant = restaurants.values().stream()
                .filter(restaurant -> restaurant.getId().equals(desiredId))
                .findFirst();
        return optionalRestaurant.orElse(null);
    }


    public List<Restaurant> searchByType(String type) {
        return new ArrayList<>(restaurants.values())
                .stream()
                .filter(rest -> typeSearchFilter(rest, type))
                .collect(Collectors.toList());
    }

    public List<Restaurant> searchByCity(String city) {
        return new ArrayList<>(restaurants.values())
                .stream()
                .filter(rest -> citySearchFilter(rest, city))
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

    public ArrayList<Restaurant> getRestaurantList(boolean sorted) {
        if (!sorted) {
            return new ArrayList<Restaurant>(restaurants.values());
        } else {
            Comparator<Restaurant> c = (s1, s2) -> s1.getOverallScore().compareTo(s2.getOverallScore());
            ArrayList<Restaurant> rests = new ArrayList<Restaurant>(restaurants.values());
            rests.sort(c);
            return rests;
        }
    }

    public void setRestaurants(Map<String, Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
