package ir.ie.mizdooni.services;

import co.elastic.apm.api.Span;
import co.elastic.apm.api.ElasticApm;
import ir.ie.mizdooni.commons.HttpRequestSender;
import ir.ie.mizdooni.definitions.DataBaseUrlPath;
import ir.ie.mizdooni.definitions.TimeFormats;
import ir.ie.mizdooni.exceptions.InvalidUserRole;
import ir.ie.mizdooni.exceptions.RestaurantManagerNotFound;
import ir.ie.mizdooni.exceptions.RestaurantNotFound;
import ir.ie.mizdooni.exceptions.RestaurentExists;
import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.repositories.RestaurantRepository;
import ir.ie.mizdooni.utils.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static ir.ie.mizdooni.definitions.RequestKeys.*;

@Service
public class RestaurantHandler {
    private final UserHandler userHandler;
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantHandler(UserHandler userHandler, RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userHandler = userHandler;
        if (restaurantRepository.count() == 0) {
            loadInitDatabase();
        }
    }

    private void loadInitDatabase() {
        String response = HttpRequestSender.sendGetRequest(DataBaseUrlPath.RESTAURANT_DATABASE_URL);
        List<Map<String, Object>> restaurantsList = Parser.parseStringToJsonArray(response);
        List<Restaurant> restaurantList = new ArrayList<>();
        long id = 1;
        for (var restaurantMap : restaurantsList) {
            restaurantList.add(new Restaurant(
                    (String) restaurantMap.get(ADD_RESTAURANT_NAME_KEY),
                    Parser.parseTime((String) restaurantMap.get(START_TIME_KEY), TimeFormats.RESTAURANT_TIME_FORMAT),
                    Parser.parseTime((String) restaurantMap.get(END_TIME_KEY), TimeFormats.RESTAURANT_TIME_FORMAT),
                    (String) restaurantMap.get(RESTAURANT_TYPE_KEY),
                    (String) restaurantMap.get(DESCRIPTION_KEY),
                    userHandler.getManagerUserByUsername((String) restaurantMap.get(MANAGER_USERNAME_KEY)),
                    (Map<String, String>) restaurantMap.get(RESTAURANT_ADDRESS_KEY),
                    (String) restaurantMap.get(RESTAURANT_IMAGE_URL_KEY), id));
            id++;
        }
        restaurantRepository.saveAll(restaurantList);
    }

    public boolean isManager(String managerUsername) {
        UserRole u = userHandler.getUserRole(managerUsername);
        return (u != null && u.equals(UserRole.MANAGER));
    }

    public Restaurant getRestaurant(String restName) {
        Restaurant res = restaurantRepository.findFirstByName(restName).orElse(null);
        return res;
    }

    public Restaurant getRestaurant(Long id) {
        return restaurantRepository.findByIdNum(id).orElse(null);
    }

    public List<Restaurant> searchRestaurantByType(String restType) {
        Span parent = ElasticApm.currentSpan();
        Span child = parent.startSpan();
        child.setName("SearchByType");
        List<Restaurant> restaurants= restaurantRepository.findByType(restType);
        child.end();
        return  restaurants;
    }

    public List<Restaurant> searchRestaurantByCity(String city) {
        Span parent = ElasticApm.currentSpan();
        Span child = parent.startSpan();
        child.setName("SearchByCity");
        List<Restaurant> restaurants=restaurantRepository.findByAddressCity(city);
        child.end();
        return restaurants;
    }

    public List<Restaurant> searchRestaurantByName(String restName) {
        Span parent = ElasticApm.currentSpan();
        Span child = parent.startSpan();
        child.setName("SearchByName");
        List<Restaurant> restaurants = restaurantRepository.findByNameContainingIgnoreCase(restName);
        child.end();
        return restaurants;
    }

    public List<Restaurant> generalSearch(String restName, String location, String restType) {
        Span parent = ElasticApm.currentSpan();
        Span child = parent.startSpan();
        child.setName("GeneralSearch");
        Set<Restaurant> resultSet = new HashSet<>();
        if (restName != null && !restName.isEmpty()) {
            resultSet.addAll(restaurantRepository.findByNameContainingIgnoreCase(restName));
        }

        if (location != null && !location.isEmpty()) {
            resultSet.addAll(restaurantRepository.findByAddressCity(location));
            resultSet.addAll(restaurantRepository.findByAddressCountry(location));
        }

        if (restType != null && !restType.isEmpty()) {
            resultSet.addAll(restaurantRepository.findByType(restType));
        }
        child.end();
        return new ArrayList<>(resultSet);

    }

    public List<Restaurant> searchRestaurantByManagerName(String managerName) {
        return restaurantRepository.findByManagerUserUsername(managerName);
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

        restaurantRepository.save(new Restaurant(restName, Parser.parseTime(startTime, TimeFormats.RESTAURANT_TIME_FORMAT),
                Parser.parseTime(endTime, TimeFormats.RESTAURANT_TIME_FORMAT), type, description,
                userHandler.getManagerUserByUsername(managerUsername), address, imageUrl, restaurantRepository.count() + 1));
    }

    public boolean dateIsInRestaurantRange(String restName, LocalDateTime dateTime) throws RestaurantNotFound {
        Restaurant restaurant = restaurantRepository.findFirstByName(restName).orElse(null);
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
        restaurantRepository.findFirstByName(restName).ifPresent(restaurant -> {
            restaurant.updateScores(foodScore, ambianceScore, serviceScore, overallScore);
            restaurantRepository.save(restaurant);
        });
    }

    public List<Restaurant> getRestaurants(boolean sorted) {
        if (sorted)
            return restaurantRepository.getRestaurantSortedByAvgOverallScore();
        return restaurantRepository.findAll();
    }

}
