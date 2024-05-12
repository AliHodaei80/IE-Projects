package ir.ie.mizdooni.repositories;

import ir.ie.mizdooni.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r WHERE r.type = :type")
    List<Restaurant> findByType(String type);

    @Query("SELECT r FROM Restaurant r WHERE r.id = :id")
    Optional<Restaurant> findByIdNum(long id);

    @Query("SELECT r FROM Restaurant r JOIN r.address a WHERE a.city = :city")
    List<Restaurant> findByAddressCity(String city);

    @Query("SELECT r FROM Restaurant r JOIN r.address a WHERE a.country = :country")
    List<Restaurant> findByAddressCountry(String country);

    @Query("SELECT r FROM Restaurant r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Restaurant> findByNameContainingIgnoreCase(String name);

    @Query("SELECT r FROM Restaurant r WHERE r.managerUser.username = :managerUsername")
    List<Restaurant> findByManagerUserUsername(String managerUsername);

    @Query("SELECT r FROM Restaurant r WHERE r.name = :name")
    Optional<Restaurant> findFirstByName(String name);

    @Query("SELECT r FROM Restaurant r ORDER BY r.avgOverallScore DESC")
    List<Restaurant> getRestaurantSortedByAvgOverallScore();
}
