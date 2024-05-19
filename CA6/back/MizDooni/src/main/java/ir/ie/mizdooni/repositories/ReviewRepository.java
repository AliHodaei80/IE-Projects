package ir.ie.mizdooni.repositories;

import ir.ie.mizdooni.models.ClientUser;
import ir.ie.mizdooni.models.Restaurant;
import ir.ie.mizdooni.models.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.restaurant.name = :restName")
    List<Review> findByRestaurantName(@Param("restName") String restName);

    @Transactional
    @Modifying
    @Query("UPDATE Review r " +
            "SET r.ambianceRate = :ambianceRate, " +
            "r.overallRate = :overallRate, " +
            "r.serviceRate = :serviceRate, " +
            "r.foodRate = :foodRate, " +
            "r.comment = :comment " +
            "WHERE r.id = :reviewId")
    void updateReview(Long reviewId,
                      Double ambianceRate,
                      Double overallRate,
                      Double serviceRate,
                      Double foodRate,
                      String comment);

    @Query("SELECT r FROM Review r " +
            "WHERE r.clientUser.username = :clientUsername " +
            "AND r.restaurant.name = :restaurantName")
    Optional<Review> findByClientUserAndRestaurant(@Param("clientUsername") String clientUsername,
                                                   @Param("restaurantName") String restaurantName);

    // Define other query methods as needed...
}
