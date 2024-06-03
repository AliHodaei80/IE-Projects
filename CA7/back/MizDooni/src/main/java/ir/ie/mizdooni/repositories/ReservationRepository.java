package ir.ie.mizdooni.repositories;

import ir.ie.mizdooni.models.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.restaurant.name = :restaurantName " +
            "AND r.restaurantTable.tableNumber = :tableNumber " +
            "AND r.datetime = :dateTime")
    List<Reservation> findReservationsByRestaurantTableAndDateTime(String restaurantName, Long tableNumber, LocalDateTime dateTime);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.clientUser.username = :username")
    List<Reservation> findReservationsByUsername(String username);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.restaurant.name = :restaurantName")
    List<Reservation> findReservationsByRestaurantName(String restaurantName);

    @Modifying
    @Transactional
    @Query("UPDATE Reservation r " +
            "SET r.canceled = true " +
            "WHERE r.reservationId = :reservationId")
    void cancelReservation(Long reservationId);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.restaurant.name = :restaurantName " +
            "AND r.restaurantTable.tableNumber = :tableNumber")
    List<Reservation> findReservationsByRestaurantTable(String restaurantName, Long tableNumber);
}