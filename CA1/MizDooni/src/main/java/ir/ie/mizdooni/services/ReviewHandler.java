package ir.ie.mizdooni.services;

import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.models.Reservation;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.storage.Reservations;
import ir.ie.mizdooni.utils.Parser;

import java.time.LocalDateTime;
import java.util.List;

import static ir.ie.mizdooni.definitions.TimeFormats.RESERVE_DATETIME_FORMAT;

public class ReviewHandler {
    private static ReviewHandler reservationHandler;
    private final UserHandler userHandler;
    private final RestaurantHandler restaurantHandler;
    private final RestaurantTableHandler restaurantTableHandler;
    private final Reservations reservations;


    private ReviewHandler() {
        userHandler = UserHandler.getInstance();
        restaurantHandler = RestaurantHandler.getInstance();
        restaurantTableHandler = RestaurantTableHandler.getInstance();
        reservations = new Reservations();
    }

    public static ReviewHandler getInstance() {
        if (reservationHandler == null)
            reservationHandler = new ReviewHandler();
        return reservationHandler;
    }

    public boolean isClient(String username) {
        UserRole u = userHandler.getUserRole(username);
        return (u != null && u.equals(UserRole.CLIENT));
    }

    private boolean userExists(String username) {
        UserRole u = userHandler.getUserRole(username);
        return (u != null);
    }

    private boolean tableIsAvailable(String restName, int tableNumber, LocalDateTime dateTime) {
        return reservations.getReservation(restName, tableNumber, dateTime) == null;
    }

    private boolean tableDateIsValid(LocalDateTime dateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return dateTime.isAfter(currentDateTime);
    }

    private boolean checkReservationIsForUser(String username, long reservationId) {
        Reservation reservation = reservations.getReservation(reservationId);
        return reservation != null && reservation.getUsername().equals(username);
    }

    private boolean checkCancellationTimeValid(long reservationId) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Reservation reservation = reservations.getReservation(reservationId);
        return reservation != null && currentDateTime.isBefore(reservation.getDate());
    }

    public long addReservation(String restName, String username, int tableNumber, String dateTime)
            throws RestaurantManagerNotFound, InvalidUserRole, RestaurantNotFound, TableDoesntExist, TableAlreadyReserved, InvalidDateTime, DateTimeNotInRange {
        if (!userExists(username)) {
            throw new RestaurantManagerNotFound();
        }
        if (!(isClient(username))) {
            throw new InvalidUserRole();
        }
        if (!(restaurantHandler.restaurantExists(restName))) {
            throw new RestaurantNotFound();
        }
        if (!restaurantTableHandler.doesTableExist(restName, (long) tableNumber)) {
            throw new TableDoesntExist();
        }
        if (!tableIsAvailable(restName, tableNumber, Parser.parseDateTime(dateTime, RESERVE_DATETIME_FORMAT))) {
            throw new TableAlreadyReserved();
        }
        if (!tableDateIsValid(Parser.parseDateTime(dateTime, RESERVE_DATETIME_FORMAT))) {
            throw new InvalidDateTime();
        }
        if (!restaurantHandler.dateIsInRestaurantRange(restName, Parser.parseDateTime(dateTime, RESERVE_DATETIME_FORMAT))) {
            throw new DateTimeNotInRange();
        }
        return reservations.addReservation(username, restName, tableNumber, Parser.parseDateTime(dateTime, RESERVE_DATETIME_FORMAT));
    }

    public void cancelReservation(String username, long reservationId) throws ReservationNotForUser, CancellationTimePassed {
        if (!checkReservationIsForUser(username, reservationId)) {
            throw new ReservationNotForUser();
        }
        if (!checkCancellationTimeValid(reservationId)) {
            throw new CancellationTimePassed();
        }
        reservations.removeReservation(reservationId);
    }

    public List<Reservation> showHistoryReservation(String username) {
        return reservations.getUserReservations(username);
    }
}
