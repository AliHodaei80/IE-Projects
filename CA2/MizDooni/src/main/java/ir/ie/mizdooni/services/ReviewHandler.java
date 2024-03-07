package ir.ie.mizdooni.services;

import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.models.Reservation;
import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.models.Review;
import java.util.function.Function;
import ir.ie.mizdooni.storage.Reviews;
import ir.ie.mizdooni.utils.Parser;
import ir.ie.mizdooni.definitions.Locations;
import java.util.List;
import java.util.stream.Collectors;


public class ReviewHandler {
    private final UserHandler userHandler;
    private final RestaurantHandler restaurantHandler;
    private static ReviewHandler reviewHandler;
    private final Reviews reviews;

    private ReviewHandler() {
        userHandler = UserHandler.getInstance();
        restaurantHandler = RestaurantHandler.getInstance();
        reviews = new Reviews().loadFromFile(Locations.REVIEWS_LOCATION, Reviews.class);
    }

    public static ReviewHandler getInstance() {
        if (reviewHandler == null)
            reviewHandler = new ReviewHandler();
        return reviewHandler;
    }

    public boolean isClient(String username) {
        UserRole u = userHandler.getUserRole(username);
        return (u != null && u.equals(UserRole.CLIENT));
    }

    public boolean hadReservationBefore(String username, String restName) {
        List<Reservation> reservations = ReservationHandler.getInstance().showHistoryReservation(username);
        Reservation reservation = reservations.stream()
                .filter(reserve -> reserve.getRestaurantName().equals(restName))
                .filter(reserve -> (!ReservationHandler.getInstance().currentDateTimeIsBefore(reserve.getDatetime())))
                .findFirst().orElse(null);
        return reservation != null;
    }
    public Double scoreAverage(String restName, Function<Review, Double> scoreGetter) {
        List<Review> reviewList = reviews.getRestaurantReviews(restName);
        if (reviewList.isEmpty()) {
            return 0.0;
        }

        return reviewList.stream()
                .mapToDouble(scoreGetter::apply)
                .average()
                .orElse(0.0);
    }
    public Review addReview(String restName,
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
        Review r = reviews.addReview(restName,
                username,
                ambianceRate,
                overallRate,
                serviceRate,
                foodRate,
                comment);
        restaurantHandler.updateScores(restName,
                scoreAverage(restName,Review::getFoodRate)
                ,scoreAverage(restName,Review::getServiceRate)
                ,scoreAverage(restName,Review::getOverallRate)
                ,scoreAverage(restName,Review::getAmbianceRate));
        return r;
    }

    public List<Review> getRestReviews(String restName) {
        return reviews.getRestaurantReviews(restName);
    }

    public Reviews getReviews() {
        return reviews;
    }
}
