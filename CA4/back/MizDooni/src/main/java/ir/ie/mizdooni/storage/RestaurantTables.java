package ir.ie.mizdooni.storage;

import ir.ie.mizdooni.commons.HttpRequestSender;
import ir.ie.mizdooni.definitions.TimeFormats;
import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.RestaurantTable;
import ir.ie.mizdooni.storage.commons.Container;
import ir.ie.mizdooni.definitions.Locations;
import ir.ie.mizdooni.utils.Parser;

import java.util.*;
import java.util.stream.Collectors;

import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.definitions.RequestKeys.RESTAURANT_IMAGE_URL_KEY;

public class RestaurantTables  extends Container<RestaurantTables>{
    Map<String, Map<Long, RestaurantTable>> restaurantTables;

    public RestaurantTables() {
        restaurantTables = new HashMap<>();
    }
    @Override
    public RestaurantTables loadFromUrl(String urlPath) {
        String response = HttpRequestSender.sendGetRequest(urlPath);
        List<Map<String, Object>> restaurantTablesList = Parser.parseStringToJsonArray(response);
        RestaurantTables restaurantTablesObject = new RestaurantTables();
        for (var restaurantTableMap : restaurantTablesList) {
            restaurantTablesObject.addRestaurantTable(
                    (String) restaurantTableMap.get(RESTAURANT_NAME_KEY),
                    (String) restaurantTableMap.get(MANAGER_USERNAME_KEY),
                    (Long) (Math.round((Double) (restaurantTableMap.get(TABLE_NUM_KEY)))),
                    (int) (Math.round((Double) (restaurantTableMap.get(SEATS_NUM_KEY))))
            );

        }
        return restaurantTablesObject;
    }

    public Collection<RestaurantTable> getRestTables(String restName) {
        return (restaurantTables.containsKey(restName)) ? restaurantTables.get(restName).values() : new ArrayList<>();
    }



    public RestaurantTable getRestaurantTable(String restName, Long tableNo) {
        if (restaurantTables.get(restName) != null) {
            return restaurantTables.get(restName).get(tableNo);
        }
        return null;
    }

    public Map<String, Map<Long, RestaurantTable>> getAllTables() {
        return restaurantTables;
    }

    public void addRestaurantTable(String restName, String restManagerName, Long tableNo, int seatsNo) {
        if (restaurantTables.get(restName) == null) {
            restaurantTables.put(restName, new HashMap<>());
        }
        long tableID = restaurantTables.get(restName).size() + 1;
        restaurantTables.get(restName).put(tableNo, new RestaurantTable(tableNo, restName, restManagerName, seatsNo));
        this.saveToFile(Locations.RESTAURANT_TABLES_LOCATION);
    }

    public Long addRestaurantTable(String restName, String restManagerName, int seatsNo) {
        if (restaurantTables.get(restName) == null) {
            restaurantTables.put(restName, new HashMap<>());
        }
        long tableID = restaurantTables.get(restName).size() + 1;
        restaurantTables.get(restName).put(tableID, new RestaurantTable(tableID, restName, restManagerName, seatsNo));
        this.saveToFile(Locations.RESTAURANT_TABLES_LOCATION);
        return tableID;
    }

    public Map<String, Map<Long, RestaurantTable>> getRestaurantTables() {
        return restaurantTables;
    }

    public void setRestaurantTables(Map<String, Map<Long, RestaurantTable>> restaurantTables) {
        this.restaurantTables = restaurantTables;
    }

    public List<RestaurantTable> getRestaurantTables(String restName) {
        return restaurantTables.get(restName) !=null ? new ArrayList<>(restaurantTables.get(restName).values()) : new ArrayList<>();
    }

    public Map<String, Map<Long, RestaurantTable>> getRestaurantTables(List<String> restaurantNames) {
        return restaurantTables.entrySet().stream()
                .filter(entry -> restaurantNames.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
