package ir.ie.mizdooni.storage;

import ir.ie.mizdooni.models.RestaurantTable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RestaurantTables {
    Map<String, Map<Long, RestaurantTable>> restaurantTables;

    public Collection<RestaurantTable> getRestTables(String restName) {
        return restaurantTables.get(restName).values();
    }

    public RestaurantTables() {
        restaurantTables = new HashMap<>();
    }

    public RestaurantTable getRestaurantTable(String restName, Long tableNo) {
        System.out.println(restaurantTables);
        if (restaurantTables.get(restName) != null) {
            return restaurantTables.get(restName).get(tableNo);
        }
        return null;
    }

    public Map<String, Map<Long, RestaurantTable>> getAllTables() {
        return restaurantTables;
    }

    public boolean tableExists(String restName, Long tableNo) {
        return getRestaurantTable(restName, tableNo) != null;
    }

    public void addRestaurantTable(String restName, String restManagerName, Long tableNo, Long seatsNo) {
        if (restaurantTables.get(restName) == null) {
            restaurantTables.put(restName, new HashMap<>());
        }
        restaurantTables.get(restName).put(tableNo, new RestaurantTable(seatsNo, restName, restManagerName, tableNo));
    }
}
