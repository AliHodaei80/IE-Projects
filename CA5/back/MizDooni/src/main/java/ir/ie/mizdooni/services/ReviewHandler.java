package ir.ie.mizdooni.services;

import ir.ie.mizdooni.definitions.DataBaseUrlPath;
import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.models.Reservation;
import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.models.Review;
import java.util.function.Function;
import ir.ie.mizdooni.storage.Reviews;
import ir.ie.mizdooni.utils.Parser;
import ir.ie.mizdooni.definitions.Locations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewHandler {
    private final UserHandler userHandler;
    private final RestaurantHandler restaurantHandler;
    private ReservationHandler reservationHandler;
    private final Reviews reviews;

    @Autowired
    private ReviewHandler(UserHandler userHandler, RestaurantHandler restaurantHandler, ReservationHandler reservationHandler) {
        this.userHandler = userHandler;
        this.restaurantHandler = restaurantHandler;
        this.reservationHandler = reservationHandler;
        reviews = new Reviews().loadFromUrl(DataBaseUrlPath.REVIEWS_DATABASE_URL);
        List<Restaurant> restaurantList = restaurantHandler.getRestaurants(false);
        for (Restaurant restaurant : restaurantList) {
            restaurantHandler.updateScores(restaurant.getName(),
                    scoreAverage(restaurant.getName(),Review::getFoodRate)
                    ,scoreAverage(restaurant.getName(),Review::getServiceRate)
                    ,scoreAverage(restaurant.getName(),Review::getOverallRate)
                    ,scoreAverage(restaurant.getName(),Review::getAmbianceRate));
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
