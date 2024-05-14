package ir.ie.mizdooni.repositories;

import ir.ie.mizdooni.models.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {

    @Query("SELECT t FROM RestaurantTable t WHERE t.restaurant.name = :restName")
    List<RestaurantTable> findByRestaurantName(String restName);

    @Query("SELECT t FROM RestaurantTable t WHERE t.restaurant.name = :restName AND t.tableNumber = :tableNo")
    Optional<RestaurantTable> findByRestaurantNameAndTableNumber(String restName, Long tableNo);
}
