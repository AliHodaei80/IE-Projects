package ir.ie.mizdooni.services;

import ir.ie.mizdooni.commons.HttpRequestSender;
import ir.ie.mizdooni.definitions.DataBaseUrlPath;
import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.models.RestaurantTable;
import ir.ie.mizdooni.repositories.RestaurantTableRepository;
import ir.ie.mizdooni.utils.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static ir.ie.mizdooni.definitions.RequestKeys.*;

@Service
public class RestaurantTableHandler {

    private final RestaurantHandler restaurantsHandler;
    private final UserHandler userHandler;
    private final RestaurantTableRepository restaurantTableRepository;

    @Autowired
    private RestaurantTableHandler(RestaurantHandler restaurantsHandler, UserHandler userHandler, RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
        this.restaurantsHandler = restaurantsHandler;
        this.userHandler = userHandler;
        if (restaurantTableRepository.count() == 0) {
            loadInitDatabase();
        }
    }

    private void loadInitDatabase() {
        String response = HttpRequestSender.sendGetRequest(DataBaseUrlPath.RESTAURANT_TABLES_DATABASE_URL);
        List<Map<String, Object>> restaurantTablesList = Parser.parseStringToJsonArray(response);
        List<RestaurantTable> restaurantTableList = new ArrayList<>();
        for (var restaurantTableMap : restaurantTablesList) {
            restaurantTableList.add(new RestaurantTable(
                    restaurantsHandler.getRestaurant((String) restaurantTableMap.get(RESTAURANT_NAME_KEY)),
                    userHandler.getManagerUserByUsername((String) restaurantTableMap.get(MANAGER_USERNAME_KEY)),
                    (int) (Math.round((Double) (restaurantTableMap.get(SEATS_NUM_KEY))))));
        }
        restaurantTableRepository.saveAll(restaurantTableList);
    }

    public Long addRestaurantTable(String restName, int seatsNo, String managerUsername)
            throws RestaurantManagerNotFound, InvalidUserRole, RestaurantNotFound, TableAlreadyExists, ManagerUsernameNotMatch {
        if (!restaurantsHandler.restaurantExists(restName)) {
            throw new RestaurantNotFound();
        }
        if (!userHandler.doesUserExist(managerUsername)) {
            throw new RestaurantManagerNotFound();
        }
        if (!(restaurantsHandler.isManager(managerUsername))) {
            throw new InvalidUserRole();
        }
        if (!restaurantsHandler.getRestaurant(restName).getManagerUsername().equals(managerUsername)) {
            throw new ManagerUsernameNotMatch();
        }
        RestaurantTable restaurantTable = new RestaurantTable(restaurantsHandler.getRestaurant(restName),
                userHandler.getManagerUserByUsername(managerUsername), seatsNo);
        restaurantTableRepository.save(restaurantTable);
        return restaurantTable.getTableNumber();
    }

    public List<RestaurantTable> getRestaurantTables(String restName) {
        return restaurantTableRepository.findByRestaurantName(restName);
    }

    public Collection<RestaurantTable> getRestTables(String restName) throws RestaurantNotFound {
        if (restaurantsHandler.restaurantExists(restName)) {
            return restaurantTableRepository.findByRestaurantName(restName);
        } else {
            throw new RestaurantNotFound();
        }
    }

    public boolean doesTableExist(String restName, Long tableNumber) {
        return restaurantTableRepository.findByRestaurantNameAndTableNumber(restName, tableNumber).isPresent();
    }

    public RestaurantTable getRestaurantTable(String restName, Long tableNumber) {
        return restaurantTableRepository.findByRestaurantNameAndTableNumber(restName, tableNumber).orElse(null);
    }
}
