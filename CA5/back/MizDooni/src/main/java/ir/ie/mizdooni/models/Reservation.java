package ir.ie.mizdooni.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ir.ie.mizdooni.definitions.TimeFormats.RESERVE_DATETIME_FORMAT;

@Entity
public class Reservation {
    //    String username;
    @ManyToOne
    @JoinColumn(name = "client_user_id")
    ClientUser clientUser;
    //    String restaurantName;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    Restaurant restaurant;
    @ManyToOne
    @JoinColumn(name = "table_id")
    RestaurantTable restaurantTable;
    LocalDateTime datetime;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long reservationId;
    boolean canceled;
    Long seatsReserved;

    public Reservation(ClientUser clientUser, Restaurant restaurant, RestaurantTable restaurantTable, LocalDateTime date, Long seatsReserved) {
        this.clientUser = clientUser;
        this.restaurant = restaurant;
        this.restaurantTable = restaurantTable;
        this.datetime = date;
        this.canceled = false;
        this.seatsReserved = seatsReserved;
    }

    public Reservation() {
    }

    public String getUsername() {
        return clientUser.getUsername();
    }

//    public void setUsername(String username) {
//        this.username = username;
//    }

    public String getRestaurantName() {
        return restaurant.getName();
    }

//    public void setRestaurantName(String restaurantName) {
//        this.restaurantName = restaurantName;
//    }

    public Long getTableNumber() {
        return restaurantTable.getTableNumber();
    }

//    public void setTableNumber(Long tableNumber) {
//        this.tableNumber = tableNumber;
//    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    @JsonIgnore
    public String getDatetimeString() {
        return DateTimeFormatter.ofPattern(RESERVE_DATETIME_FORMAT).format(datetime);
    }


    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public Long getRestaurantId() {
        return restaurant.getId();
    }

    public Long getSeatsReserved() {
        return seatsReserved;
    }
}
