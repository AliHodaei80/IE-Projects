package ir.ie.mizdooni.models;

import com.google.gson.annotations.Expose;

public class RestaurantTable {
    @Expose()
    Long tableNumber;
    @Expose()
    String restaurantName;
    @Expose()
    String managerUsername;
    @Expose()
    Long seatsNumber;

    public RestaurantTable(Long tableNumber, String restaurantName, String managerUsername, Long seatsNumber) {
        this.tableNumber = tableNumber;
        this.restaurantName = restaurantName;
        this.managerUsername = managerUsername;
        this.seatsNumber = seatsNumber;
    }

    public Long getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Long tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getManagerUsername() {
        return managerUsername;
    }

    public void setManagerUsername(String managerUsername) {
        this.managerUsername = managerUsername;
    }

    public Long getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(Long seatsNumber) {
        this.seatsNumber = seatsNumber;
    }
}
