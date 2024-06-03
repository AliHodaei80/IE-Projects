package ir.ie.mizdooni.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Review {
    //    String username;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @ManyToOne
    @JoinColumn(name = "client_user_id")
    ClientUser clientUser;
    //    String restaurantName;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    Restaurant restaurant;
    Double ambianceRate;
    Double overallRate;
    Double serviceRate;
    Double foodRate;
    @Column(columnDefinition = "TEXT")
    String comment;
    LocalDateTime submitDate;

    public Review(ClientUser clientUser, Restaurant restaurant, Double ambianceRate, Double foodRate, Double overallRate,
                  Double serviceRate,
                  String comment, LocalDateTime submitDate) {
        this.clientUser = clientUser;
        this.restaurant = restaurant;
        this.ambianceRate = ambianceRate;
        this.foodRate = foodRate;
        this.overallRate = overallRate;
        this.serviceRate = serviceRate;
        this.comment = comment;
        this.submitDate = submitDate;
    }

    public Review() {
    }

    public String getUsername() {
        return clientUser.getUsername();
    }

    public String getRestaurantName() {
        return restaurant.getName();
    }

    public Double getAmbianceRate() {
        return ambianceRate;
    }

    public Double getOverallRate() {
        return overallRate;
    }

    public Double getServiceRate() {
        return serviceRate;
    }

    public Double getFoodRate() {
        return foodRate;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getSubmitDate() {
        return submitDate;
    }

    public String getSubmitDateString() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(submitDate);
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }
}
