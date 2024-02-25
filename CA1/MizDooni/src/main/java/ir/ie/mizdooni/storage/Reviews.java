package ir.ie.mizdooni.storage;

import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.RestaurantTable;
import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.models.Review;
import ir.ie.mizdooni.services.UserHandler;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;

public class Reviews {
    Map<String, Map<String, Review>> Reviews; // first is restaurant second is user

    public Reviews() {
        Reviews = new HashMap<>();
    }

    public Review addReview(
            String restName,
            String username,
            Double ambianceRate,
            Double overallRate,
            Double serviceRate,
            Double foodRate,
            String comment) {
        {
            if (Reviews.get(restName) == null) {
                Reviews.put(restName, new HashMap<>());
            }
            Review r = new Review(username, restName, ambianceRate, foodRate, overallRate,
                    serviceRate, comment, LocalDateTime.now());
            Reviews.get(restName).put(username, r);
            System.out.println(Reviews);
            System.out.println(r);
            return r;
        }
    }

    public Map<String, Map<String, Review>> getReviews() {
        return Reviews;
    }

    public void setReviews(Map<String, Map<String, Review>> reviews) {
        Reviews = reviews;
    }
}