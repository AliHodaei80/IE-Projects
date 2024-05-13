package ir.ie.mizdooni.services;

import ir.ie.mizdooni.commons.HttpRequestSender;
import ir.ie.mizdooni.definitions.DataBaseUrlPath;
import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.models.Reservation;
import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.models.Review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import ir.ie.mizdooni.repositories.ReviewRepository;
import ir.ie.mizdooni.storage.Reviews;
import ir.ie.mizdooni.utils.Parser;
import ir.ie.mizdooni.definitions.Locations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static ir.ie.mizdooni.definitions.RequestKeys.*;

@Service
public class ReviewHandler {
    private final UserHandler userHandler;
    private final RestaurantHandler restaurantHandler;
    private ReservationHandler reservationHandler;
    private ReviewRepository reviewRepository;
//    private final Reviews reviews;

    @Autowired
    private ReviewHandler(UserHandler userHandler, RestaurantHandler restaurantHandler, ReservationHandler reservationHandler, ReviewRepository reviewRepository) throws UserNotFound, NoReservationBefore, InvalidUserRole, RestaurantNotFound {
        this.userHandler = userHandler;
        this.restaurantHandler = restaurantHandler;
        this.reservationHandler = reservationHandler;
        this.reviewRepository = reviewRepository;
//        reviews = new Reviews().loadFromUrl(DataBaseUrlPath.REVIEWS_DATABASE_URL);
        if (reviewRepository.count() == 0) {
            String response = HttpRequestSender.sendGetRequest(DataBaseUrlPath.REVIEWS_DATABASE_URL);
            List<Map<String, Object>> reviewsList = Parser.parseStringToJsonArray(response);
            for (var reviewMap : reviewsList) {
                Optional<Review> r = reviewRepository.findByClientUserAndRestaurant(
                        (String) reviewMap.get(USERNAME_KEY),
                        (String) reviewMap.get(RESTAURANT_NAME_KEY));
                if (r.isPresent()) {
                    reviewRepository.updateReview(r.get().getId(),
                            (Double) reviewMap.get(AMBIANCE_RATE_KEY),
                            (Double) reviewMap.get(OVERALL_RATE_KEY),
                            (Double) reviewMap.get(SERVICE_RATE_KEY),
                            (Double) reviewMap.get(FOOD_RATE_KEY),
                            (String) reviewMap.get(COMMENT_KEY)
                    );
                }
                else {
                    reviewRepository.save(new Review(
                            userHandler.getClientUserByUsername((String) reviewMap.get(USERNAME_KEY)),
                            restaurantHandler.getRestaurant((String) reviewMap.get(RESTAURANT_NAME_KEY)),
                            (Double) reviewMap.get(AMBIANCE_RATE_KEY),
                            (Double) reviewMap.get(FOOD_RATE_KEY),
                            (Double) reviewMap.get(OVERALL_RATE_KEY),
                            (Double) reviewMap.get(SERVICE_RATE_KEY),
                            (String) reviewMap.get(COMMENT_KEY),
                            LocalDateTime.now()
                    ));
                }
                restaurantHandler.updateScores((String) reviewMap.get(RESTAURANT_NAME_KEY),
                        scoreAverage((String) reviewMap.get(RESTAURANT_NAME_KEY),Review::getFoodRate)
                        ,scoreAverage((String) reviewMap.get(RESTAURANT_NAME_KEY),Review::getServiceRate)
                        ,scoreAverage((String) reviewMap.get(RESTAURANT_NAME_KEY),Review::getOverallRate)
                        ,scoreAverage((String) reviewMap.get(RESTAURANT_NAME_KEY),Review::getAmbianceRate));
            }
        }
    }

    public boolean isClient(String username) {
        UserRole u = userHandler.getUserRole(username);
        return (u != null && u.equals(UserRole.CLIENT));
    }

    public boolean hadReservationBefore(String username, String restName) {
        List<Reservation> reservations = reservationHandler.showHistoryReservation(username);
        Reservation reservation = reservations.stream()
                .filter(reserve -> reserve.getRestaurantName().equals(restName))
                .filter(reserve -> (!reservationHandler.currentDateTimeIsBefore(reserve.getDatetime())))
                .findFirst().orElse(null);
        return reservation != null;
    }
    public Double scoreAverage(String restName, Function<Review, Double> scoreGetter) {
//        List<Review> reviewList = reviews.getRestaurantReviews(restName);
        List<Review> reviewList = reviewRepository.findByRestaurantName(restName);
        if (reviewList.isEmpty()) {
            return 0.0;
        }

        return reviewList.stream()
                .mapToDouble(scoreGetter::apply)
                .average()
                .orElse(0.0);
    }
    public void addReview(String restName,
            String username,
            Double ambianceRate,
            Double overallRate,
            Double serviceRate,
            Double foodRate,
            String comment)
            throws InvalidUserRole, UserNotFound, RestaurantNotFound, NoReservationBefore {
        if (!(userHandler.doesUserExist(username))) {
            throw new UserNotFound();
        }
        if (!(isClient(username))) {
            throw new InvalidUserRole();
        }
        if (!(restaurantHandler.restaurantExists(restName))) {
            throw new RestaurantNotFound();
        }
        if (!hadReservationBefore(username, restName)) {
            throw new NoReservationBefore();
        }

//
//        Review r = reviews.addReview(restName,
//                username,
//                ambianceRate,
//                overallRate,
//                serviceRate,
//                foodRate,
//                comment);
        Optional<Review> r = reviewRepository.findByClientUserAndRestaurant(
                username,
                restName);
        if (r.isPresent()) {
            reviewRepository.updateReview(r.get().getId(),  ambianceRate, overallRate, serviceRate, foodRate, comment);
        }
        else {
            reviewRepository.save(new Review(
                    userHandler.getClientUserByUsername(username),
                    restaurantHandler.getRestaurant(restName),
                    ambianceRate,
                    foodRate,
                    overallRate,
                    serviceRate,
                    comment,
                    LocalDateTime.now()
            ));
        }
//        reviewRepository.save(new Review(
//                userHandler.getClientUserByUsername(username),
//                restaurantHandler.getRestaurant(restName),
//                ambianceRate,
//                foodRate,
//                overallRate,
//                serviceRate,
//                comment,
//                LocalDateTime.now()
//        ));
        restaurantHandler.updateScores(restName,
                scoreAverage(restName,Review::getFoodRate)
                ,scoreAverage(restName,Review::getServiceRate)
                ,scoreAverage(restName,Review::getOverallRate)
                ,scoreAverage(restName,Review::getAmbianceRate));
    }

    public List<Review> getRestReviews(String restName) {
//        return reviews.getRestaurantReviews(restName);
        return reviewRepository.findByRestaurantName(restName);
    }

}
