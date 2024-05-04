package ir.ie.mizdooni.storage;

import ir.ie.mizdooni.commons.HttpRequestSender;
import ir.ie.mizdooni.definitions.Locations;
import ir.ie.mizdooni.definitions.TimeFormats;
import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.services.UserHandler;
import ir.ie.mizdooni.storage.commons.Container;
import ir.ie.mizdooni.utils.Parser;

import java.sql.Date;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;

import static ir.ie.mizdooni.definitions.RequestKeys.*;

public class Restaurants extends Container<Restaurants> {
    Map<String, Restaurant> restaurants;
    long restaurantCount;

    public Restaurants() {
        restaurants = new HashMap<>();
        restaurantCount = 0;
    }

    @Override
    public Restaurants loadFromUrl(String urlPath) {
        String response = HttpRequestSender.sendGetRequest(urlPath);
        List<Map<String, Object>> restaurantsList = Parser.parseStringToJsonArray(response);
        Restaurants restaurantsObject = new Restaurants();
        for (var restaurantMap : restaurantsList) {
            restaurantsObject.addRestaurant(
                    (String) restaurantMap.get(ADD_RESTAURANT_NAME_KEY),
                    (String) restaurantMap.get(RESTAURANT_TYPE_KEY),
                    Parser.parseTime((String) restaurantMap.get(START_TIME_KEY), TimeFormats.RESTAURANT_TIME_FORMAT),
                    Parser.parseTime((String) restaurantMap.get(END_TIME_KEY), TimeFormats.RESTAURANT_TIME_FORMAT),
                    (String) restaurantMap.get(DESCRIPTION_KEY),
                    (String) restaurantMap.get(MANAGER_USERNAME_KEY),
                    (Map<String, String>) restaurantMap.get(RESTAURANT_ADDRESS_KEY),
                    (String) restaurantMap.get(RESTAURANT_IMAGE_URL_KEY));
        }
        return restaurantsObject;
    }

    public boolean typeSearchFilter(Restaurant rest, String type) {
        return rest.getType().equals(type);
    }

    public boolean citySearchFilter(Restaurant rest, String city) {
        return rest.getCity().equals(city);
    }

    public boolean countrySearchFilter(Restaurant rest, String country) {
        return rest.getCountry().equals(country);
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

    public List<Restaurant> searchByCountry(String country) {
        return new ArrayList<>(restaurants.values())
                .stream()
                .filter(rest -> countrySearchFilter(rest, country))
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
                              String managerUsername, Map<String, String> address, String imageUrl) {
        restaurantCount++;
        restaurants.put(restName, new Restaurant(restName, startTime, endTime, type, desc, managerUsername, address, imageUrl, restaurantCount));
        this.saveToFile(Locations.RESTAURANTS_LOCATION);
    }

    public Map<String, Restaurant> getRestaurants() {
        return restaurants;
    }

    public ArrayList<Restaurant> getRestaurantList(boolean sorted) {
        if (!sorted) {
            return new ArrayList<Restaurant>(restaurants.values());
        } else {
            Comparator<Restaurant> c = (s1, s2) -> -1 * s1.getAvgOverallScore().compareTo(s2.getAvgOverallScore());
            ArrayList<Restaurant> rests = new ArrayList<Restaurant>(restaurants.values());
            rests.sort(c);
            return rests;
        }
    }

    public void setRestaurants(Map<String, Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public Map<String, Long> getRestaurantsId() {
        Map<String, Long> res = new HashMap<>();
        for (Restaurant rest : restaurants.values()) {
            res.put(rest.getName(), rest.getId());
        }
        return res;
    }
}
