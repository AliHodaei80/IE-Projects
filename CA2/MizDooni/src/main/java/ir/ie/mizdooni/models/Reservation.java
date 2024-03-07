package ir.ie.mizdooni.models;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ir.ie.mizdooni.definitions.TimeFormats.RESERVE_DATETIME_FORMAT;
import static ir.ie.mizdooni.definitions.TimeFormats.RESTAURANT_TIME_FORMAT;

public class Reservation {
    @Expose(serialize = false)
    String username;
    @Expose()
    String restaurantName;
    @Expose()
    Long tableNumber;
    @Expose()
    LocalDateTime datetime;
    @Expose()
    Long reservationId;

    public Reservation(String username, String restaurantName, Long tableNumber, LocalDateTime date, Long reservationNumber) {
        this.username = username;
        this.restaurantName = restaurantName;
        this.tableNumber = tableNumber;
        this.datetime = date;
        this.reservationId = reservationNumber;
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

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public String getDatetimeString() {
        return DateTimeFormatter.ofPattern(RESERVE_DATETIME_FORMAT).format(datetime);
    }


    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public Long getReservationId() {
        return reservationId;
    }
}
