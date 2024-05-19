package ir.ie.mizdooni.services;

import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.models.*;
import ir.ie.mizdooni.repositories.ReservationRepository;
import ir.ie.mizdooni.utils.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static ir.ie.mizdooni.definitions.TimeFormats.RESERVE_DATETIME_FORMAT;
import static java.lang.Integer.max;

@Service
public class ReservationHandler {
    private final UserHandler userHandler;
    private final RestaurantHandler restaurantHandler;
    private final RestaurantTableHandler restaurantTableHandler;
    private final ReservationRepository reservationRepository;

    @Autowired
    private ReservationHandler(UserHandler userHandler, RestaurantHandler restaurantHandler,
                               RestaurantTableHandler restaurantTableHandler, ReservationRepository reservationRepository) {
        this.userHandler = userHandler;
        this.restaurantHandler = restaurantHandler;
        this.restaurantTableHandler = restaurantTableHandler;
        this.reservationRepository = reservationRepository;
    }

    public boolean isClient(String username) {
        UserRole u = userHandler.getUserRole(username);
        return (u != null && u.equals(UserRole.CLIENT));
    }

    private boolean userExists(String username) {
        UserRole u = userHandler.getUserRole(username);
        return (u != null);
    }

    private boolean tableIsAvailable(String restName, long tableNumber, LocalDateTime dateTime) {
        List<Reservation> reservations1 = reservationRepository.findReservationsByRestaurantTableAndDateTime(restName, tableNumber, dateTime);
        if (reservations1 == null || reservations1.isEmpty()) {
            return true;
        }
        List<Reservation> notCancelledReservations = reservations1.stream().filter(reservation -> !reservation.isCanceled()).toList();
        ;
        return notCancelledReservations.isEmpty();
    }

    public boolean currentDateTimeIsBefore(LocalDateTime dateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return dateTime.isAfter(currentDateTime);
    }

    private boolean checkReservationIsForUser(String username, long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        return reservation != null && reservation.getUsername().equals(username);
    }

    private boolean checkCancellationTimeValid(long reservationId) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        return reservation != null && currentDateTime.isBefore(reservation.getDatetime());
    }

    public ArrayList<LocalDateTime> generateOpeningDateTimes(Restaurant res, LocalDateTime date, long nextDays) {
        LocalTime startTime = res.getStartTime();
        LocalTime endTime = res.getEndTime();
        ArrayList<LocalDateTime> openingDateTimes = new ArrayList<>();

        LocalDateTime currentDateTime;
        for (int day = 0; day <= nextDays; day++) {
            currentDateTime = date.plusDays(day);
            int startHour = (LocalDateTime.now().toLocalDate().isEqual(date.toLocalDate())) ?
                    max(startTime.getHour(), LocalDateTime.now().getHour() + 1) :
                    startTime.getHour();
            for (int time = startHour; time <= endTime.getHour(); time = time + 1) {
                openingDateTimes.add(LocalDateTime.of(currentDateTime.toLocalDate(), LocalTime.of(time, 0)));
            }
        }
        return openingDateTimes;
    }

    public ArrayList<LocalDateTime> findAvailableDateTimes(String restName, RestaurantTable table,
                                                           LocalDateTime desiredDate) {
        Restaurant rest = restaurantHandler.getRestaurant(restName);
        if (desiredDate.toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) {
            return new ArrayList<>();
        }
        List<Reservation> tableReserves = reservationRepository.findReservationsByRestaurantTable(restName, table.getTableNumber());

        Set<LocalDateTime> reservedDateTimes = tableReserves.stream().filter(reservation -> !reservation.isCanceled()).toList().stream().map(Reservation::getDatetime).collect(Collectors.toSet());

        ArrayList<LocalDateTime> restOpeningDateTimes = generateOpeningDateTimes(rest, desiredDate, 0);
        Set<LocalDateTime> openingDateTimesSet = new HashSet<>(restOpeningDateTimes);
        openingDateTimesSet.removeAll(reservedDateTimes);
        ArrayList<LocalDateTime> availableDateTimes = new ArrayList<>(openingDateTimesSet);
        return availableDateTimes;
    }


    public ArrayList<Opening> findAvailableTables(String restName, LocalDateTime desiredDate) throws RestaurantNotFound {
        if (!restaurantHandler.restaurantExists(restName)) {
            throw new RestaurantNotFound();
        }

        Collection<RestaurantTable> tables = restaurantTableHandler.getRestTables(restName);
        ArrayList<Opening> resultOpenings = new ArrayList<Opening>();
        for (RestaurantTable table : tables) {
            ArrayList<LocalDateTime> availableDatetimes = findAvailableDateTimes(restName, table, desiredDate);
            Opening opening = new Opening(table.getTableNumber(), table.getSeatsNumber(), availableDatetimes);
            resultOpenings.add(opening);
        }
        return resultOpenings;
    }

    public ArrayList<Opening> findAvailableTables(String restName, long seatNum, LocalDateTime desiredDate) throws RestaurantNotFound {
        ArrayList<Opening> resultOpenings = findAvailableTables(restName, desiredDate);
        resultOpenings.removeIf(opening -> opening.getSeatNumber() < seatNum);
        return resultOpenings;
    }

    public ArrayList<Opening> findAvailableTables(String restName, long seatNum, LocalDateTime desiredDate, LocalTime desiredTime) throws RestaurantNotFound {
        ArrayList<Opening> resultOpenings = findAvailableTables(restName, seatNum, desiredDate);
        ArrayList<Opening> resultOpeningsFinal = new ArrayList<>(resultOpenings.stream().filter(opening -> opening.getAvailableTimes().stream().anyMatch(dateTime -> dateTime.toLocalTime().equals(desiredTime))).toList());
        return resultOpeningsFinal;
    }

    public Reservation addReservation(String restName, String username, long tableNumber, String dateTime, long restaurantId, long seatsReserved)
            throws InvalidUserRole, RestaurantNotFound, TableDoesntExist,
            TableAlreadyReserved, InvalidDateTime, DateTimeNotInRange, UserNotExists {
        if (!userExists(username)) {
            throw new UserNotExists();
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
        if (!currentDateTimeIsBefore(Parser.parseDateTime(dateTime, RESERVE_DATETIME_FORMAT))) {
            throw new InvalidDateTime();
        }
        if (!restaurantHandler.dateIsInRestaurantRange(restName,
                Parser.parseDateTime(dateTime, RESERVE_DATETIME_FORMAT))) {
            throw new DateTimeNotInRange();
        }
        RestaurantTable table = restaurantTableHandler.getRestaurantTable(restName, tableNumber);
        Restaurant restaurant = restaurantHandler.getRestaurant(restName);
        ClientUser clientUser = userHandler.getClientUserByUsername(username);
        return reservationRepository.save(new Reservation(clientUser, restaurant, table,
                Parser.parseDateTime(dateTime, RESERVE_DATETIME_FORMAT), seatsReserved));
    }

    public void cancelReservation(String username, long reservationId)
            throws ReservationNotForUser, CancellationTimePassed {
        if (!checkReservationIsForUser(username, reservationId)) {
            throw new ReservationNotForUser();
        }
        if (!checkCancellationTimeValid(reservationId)) {
            throw new CancellationTimePassed();
        }
        reservationRepository.cancelReservation(reservationId);
    }

    public List<Reservation> showHistoryReservation(String username) {
        return reservationRepository.findReservationsByUsername(username);
    }

    public List<Reservation> getRestaurantReservation(String restName) {
        return reservationRepository.findReservationsByRestaurantName(restName);
    }

    public List<Reservation> getTableReservations(String restName, long tableNumber) throws RestaurantNotFound {
        return reservationRepository.findReservationsByRestaurantTable(restName, tableNumber);
    }
}
