package ir.ie.mizdooni.models;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;
import java.util.Date;

public class Reservation {
    @Expose(serialize = false)
    String username;
    @Expose()
    String restaurantName;
    @Expose()
    Long tableNumber;
    @Expose()
    LocalDateTime date;

    public Reservation(String username, String restaurantName, Long tableNumber, LocalDateTime date) {
        this.username = username;
        this.restaurantName = restaurantName;
        this.tableNumber = tableNumber;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Long getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Long tableNumber) {
        this.tableNumber = tableNumber;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
