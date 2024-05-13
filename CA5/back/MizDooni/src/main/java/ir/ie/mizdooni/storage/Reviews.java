package ir.ie.mizdooni.storage;

import ir.ie.mizdooni.commons.HttpRequestSender;
import ir.ie.mizdooni.definitions.Locations;
import ir.ie.mizdooni.models.*;
import ir.ie.mizdooni.storage.commons.Container;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ir.ie.mizdooni.utils.Parser;
import org.apache.logging.log4j.core.util.ArrayUtils;

import static ir.ie.mizdooni.definitions.RequestKeys.*;

public class Reviews extends Container<Reviews> {
    Map<String, Map<String, Review>> reviews; // first is restaurant second is user

    public Reviews() {
        reviews = new HashMap<>();
    }

//    @Override
//    public Reviews loadFromUrl(String urlPath) {
//        String response = HttpRequestSender.sendGetRequest(urlPath);
//        List<Map<String, Object>> reviewsList = Parser.parseStringToJsonArray(response);
//        Reviews reviewsObject = new Reviews();
//        for (var reviewMap : reviewsList) {
//            reviewsObject.addReview(
//                    (String) reviewMap.get(RESTAURANT_NAME_KEY),
//                    (String) reviewMap.get(USERNAME_KEY),
//                    (Double) reviewMap.get(AMBIANCE_RATE_KEY),
//                    (Double) reviewMap.get(OVERALL_RATE_KEY),
//                    (Double) reviewMap.get(SERVICE_RATE_KEY),
//                    (Double) reviewMap.get(FOOD_RATE_KEY),
//                    (String) reviewMap.get(COMMENT_KEY)
//            );
//        }
//        return reviewsObject;
//    }

//    public Review addReview(
//            String restName,
//            String username,
//            Double ambianceRate,
//            Double overallRate,
//            Double serviceRate,
//            Double foodRate,
//            String comment) {
//        {
//            if (reviews.get(restName) == null) {
//                reviews.put(restName, new HashMap<>());
//            }
//            Review r = new Review(username, restName, ambianceRate, foodRate, overallRate,
//                    serviceRate, comment, LocalDateTime.now());
//            reviews.get(restName).put(username, r);
//            this.saveToFile(Locations.REVIEWS_LOCATION);
//            return r;
//        }
//    }

    public List<Review> getAllReviews() {
        return reviews.values().stream()
                .flatMap(innerMap -> innerMap.values().stream())
                .collect(Collectors.toList());
    }

    public Map<String, Map<String, Review>> getReviews() {
        return reviews;
    }

    public List<Review> getRestaurantReviews(String restName) {
        if (reviews.get(restName) != null) {
            return new ArrayList<>(reviews.get(restName).values());
        } else {
            return new ArrayList<>();
        }
    }

    public void setReviews(Map<String, Map<String, Review>> reviews) {
        this.reviews = reviews;
    }
}