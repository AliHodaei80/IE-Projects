package ir.ie.mizdooni.storage;

import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.RestaurantTable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class RestaurantTables {
    Map<String, Map<Integer, RestaurantTable>> restaurantTables;

    public Collection<RestaurantTable> getRestTables(String restName) {
        return restaurantTables.get(restName).values();
    }

    public void addRestaurantTable(String restName, String restManagerName, int tableNo, int seatsNo) {
        restaurantTables.get(restName).put(tableNo, new RestaurantTable(seatsNo, restName, restManagerName, tableNo));
    }
}
