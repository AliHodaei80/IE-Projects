package ir.ie.mizdooni.services;

import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.models.Reservation;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.models.Review;
import ir.ie.mizdooni.storage.Reviews;
import ir.ie.mizdooni.utils.Parser;
import ir.ie.mizdooni.definitions.Locations;


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

    public Review addReview(String restName,
            String username,
            Double ambianceRate,
            Double overallRate,
            Double serviceRate,
            Double foodRate,
            String comment)
            throws InvalidUserRole, UserNotFound, RestaurantNotFound {
        if (!(userHandler.doesUserExist(username))) {
            throw new UserNotFound();
        }
        if (!(isClient(username))) {
            throw new InvalidUserRole();
        }
        if (!(restaurantHandler.restaurantExists(restName))) {
            throw new RestaurantNotFound();
        }
        return reviews.addReview(restName,
                username,
                ambianceRate,
                overallRate,
                serviceRate,
                foodRate,
                comment);
    }

    // public List<Reservation> showReviewsForRestaurant(String restName) {
    // //TODO Implement int he future
    // return reservations.getUserReservations(username);
    // }


    public Reviews getReviews() {
        return reviews;
    }
}
