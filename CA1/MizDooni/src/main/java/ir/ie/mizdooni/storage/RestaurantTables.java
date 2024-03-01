package ir.ie.mizdooni.storage;

import ir.ie.mizdooni.models.RestaurantTable;
import ir.ie.mizdooni.storage.commons.Container;
import ir.ie.mizdooni.definitions.Locations;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RestaurantTables  extends Container<RestaurantTables>{
    Map<String, Map<Long, RestaurantTable>> restaurantTables;

    public Collection<RestaurantTable> getRestTables(String restName) {
        return (restaurantTables.containsKey(restName)) ? restaurantTables.get(restName).values() : new ArrayList<>();
    }

    public RestaurantTables() {
        restaurantTables = new HashMap<>();
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
        restaurantTables.get(restName).put(tableNo, new RestaurantTable(tableNo, restName, restManagerName, seatsNo));
        this.saveToFile(Locations.RESTAURANT_TABLES_LOCATION);
    }

    public Map<String, Map<Long, RestaurantTable>> getRestaurantTables() {
        return restaurantTables;
    }

    public void setRestaurantTables(Map<String, Map<Long, RestaurantTable>> restaurantTables) {
        this.restaurantTables = restaurantTables;
    }
}
