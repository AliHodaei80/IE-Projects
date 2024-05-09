package ir.ie.mizdooni.services;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import ir.ie.mizdooni.definitions.DataBaseUrlPath;
import ir.ie.mizdooni.definitions.Locations;
import ir.ie.mizdooni.definitions.TimeFormats;
import ir.ie.mizdooni.exceptions.*;

import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.storage.Restaurants;
import ir.ie.mizdooni.utils.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantHandler {
    private final UserHandler userHandler;
    private final Restaurants restaurants;

    @Autowired
    private RestaurantHandler(UserHandler userHandler) {
        this.userHandler = userHandler;
        restaurants = new Restaurants().loadFromUrl(DataBaseUrlPath.RESTAURANT_DATABASE_URL);
    }

    public boolean isManager(String managerUsername) {
        UserRole u = userHandler.getUserRole(managerUsername);
        return (u != null && u.equals(UserRole.MANAGER));
    }

    public Restaurant getRestaurant(String restName) {
        Restaurant res = restaurants.getRestaurantByName(restName);
        return res;
    }

    public Restaurant getRestaurant(Long id) {
        return restaurants.getRestaurantById(id);
    }

    public List<Restaurant> searchRestaurantByType(String restType) {
        return restaurants.searchByType(restType);
    }

    public List<Restaurant> searchRestaurantByCity(String city) {
        return restaurants.searchByCity(city);
    }

    public List<Restaurant> searchRestaurantByName(String restName) {
        return restaurants.searchByName(restName);
    }

    public List<Restaurant> generalSearch(String restName, String location, String restType) {
        Set<Restaurant> resultSet = new HashSet<>();

        if (restName != null && !restName.isEmpty()) {
            resultSet.addAll(restaurants.searchByName(restName));
        }

        if (location != null && !location.isEmpty()) {
            resultSet.addAll(restaurants.searchByCity(location));
            resultSet.addAll(restaurants.searchByCountry(location));
        }

        if (restType != null && !restType.isEmpty()) {
            resultSet.addAll(restaurants.searchByType(restType));
        }

        return new ArrayList<>(resultSet);
    }
    public List<Restaurant> searchRestaurantByManagerName(String managerName) {
        return restaurants.searchByManagerName(managerName);
    }

    public boolean restaurantExists(String restName) {
        return getRestaurant(restName) != null;
    }

    public boolean restaurantExistsId(Long id) {
        return getRestaurant(id) != null;
    }

    public void addRestaurant(String restName, String type, String startTime, String endTime, String managerUsername,
            String description, Map<String, String> address, String imageUrl)
            throws RestaurantManagerNotFound, InvalidUserRole, RestaurentExists {

        if (!userHandler.doesUserExist(managerUsername)) {
            throw new RestaurantManagerNotFound();
        }
        if (!(isManager(managerUsername))) {
            throw new InvalidUserRole();
        }
        if ((restaurantExists(restName))) {
            throw new RestaurentExists();
        }
        restaurants.addRestaurant(restName, type, Parser.parseTime(startTime, TimeFormats.RESTAURANT_TIME_FORMAT),
                Parser.parseTime(endTime, TimeFormats.RESTAURANT_TIME_FORMAT), description, managerUsername, address, imageUrl);
    }

    public boolean dateIsInRestaurantRange(String restName, LocalDateTime dateTime) throws RestaurantNotFound {
        Restaurant restaurant = restaurants.getRestaurantByName(restName);
        if (restaurant == null)
            throw new RestaurantNotFound();
        LocalTime startTime = restaurant.getStartTime();
        LocalTime endTime = restaurant.getEndTime();

        LocalTime timeToCheck = LocalTime.of(dateTime.getHour(), dateTime.getMinute());
        return (timeToCheck.isAfter(startTime) && timeToCheck.isBefore(endTime)) ||
                timeToCheck.equals(startTime) || timeToCheck.equals(endTime);
    }

    public void updateScores(String restName, Double foodScore, Double serviceScore, Double overallScore,
            Double ambianceScore) {
        restaurants.getRestaurantByName(restName).updateScores(foodScore, ambianceScore, serviceScore, overallScore);
        restaurants.saveToFile(Locations.RESTAURANTS_LOCATION);
        restaurants.saveToFile(Locations.REVIEWS_LOCATION);
    }

    public Restaurants getRestaurants() {
        return restaurants;
    }

    public Map<String, Long> getRestaurantsIds() {
        return restaurants.getRestaurantsId();
    }

    public  long getTotalRestaurantsCount() {
        return restaurants.getRestaurantCount();
    }
}
