package ir.ie.mizdooni.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Review {
    String username;
    String restaurantName;
    Double ambianceRate;
    Double overallRate;
    Double serviceRate;
    Double foodRate;

    String comment;
    LocalDateTime submitDate;

    public Review(String username, String restaurantName, Double ambianceRate, Double foodRate, Double overallRate,
            Double serviceRate,
            String comment, LocalDateTime submitDate) {
        this.username = username;
        this.restaurantName = restaurantName;
        this.ambianceRate = ambianceRate;
        this.foodRate = foodRate;
        this.overallRate = overallRate;
        this.serviceRate = serviceRate;
        this.comment = comment;
        this.submitDate = submitDate;
    }

    public String getUsername() {
        return username;
    }

    public String getRestaurantName() {
        return restaurantName;
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
}
