package ir.ie.mizdooni.models;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;

@Entity
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long tableNumber;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    Restaurant restaurant;
    @ManyToOne
    @JoinColumn(name = "manager_user_id")
    ManagerUser managerUser;
    int seatsNumber;

    public RestaurantTable(Restaurant restaurant , ManagerUser managerUser, int seatsNumber) {
        this.restaurant = restaurant;
        this.seatsNumber = seatsNumber;
        this.managerUser = managerUser;
    }

    public RestaurantTable() {
    }

    public Long getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Long tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getRestaurantName() {
        return restaurant.getName();
    }

    public String getManagerUsername() {
        return managerUser.getUsername();
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }
}
