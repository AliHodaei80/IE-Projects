package ir.ie.mizdooni.storage;

import ir.ie.mizdooni.models.Reservation;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Reservations {
    // Outter 3 is restaurent name
    // Outter 2 is table id
    // OUter 1 is the reservation date
    Map<String, Map<Integer, Map<LocalDateTime, Reservation>>> reservations;
    Map<Long, Reservation> reservationsIdIndex;
    long reservationCounts;


    public Reservations() {
        reservations = new HashMap<>();
        reservationsIdIndex = new HashMap<>();
        reservationCounts = 0;
    }

    private long generateReservationId() {
//        UUID uuid = UUID.randomUUID();
//        ByteBuffer bytebuffer = ByteBuffer.wrap(new byte[16]);
//        bytebuffer.putLong(uuid.getMostSignificantBits());
//        bytebuffer.putLong(uuid.getLeastSignificantBits());
//        bytebuffer.rewind();
//        return Math.abs(bytebuffer.getLong());
        return ++reservationCounts;
    }


    public long addReservation(String username, String restaurantName, int tableNumber, LocalDateTime dateTime) {
        if (reservations.get(restaurantName) == null) {
            reservations.put(restaurantName, new HashMap<>());
        }
        if (reservations.get(restaurantName).get(tableNumber) == null) {
            reservations.get(restaurantName).put(tableNumber, new HashMap<>());
        }
        Reservation reservation = new Reservation(username, restaurantName, tableNumber, dateTime);
        long reservationId = generateReservationId();
        reservations.get(restaurantName).get(tableNumber).put(dateTime, reservation);
        reservationsIdIndex.put(reservationId, reservation);
        return reservationId;
    }

    public Reservation getReservation(String restName, int tableNumber, LocalDateTime dateTime) {
        if (!reservations.containsKey(restName) || !reservations.get(restName).containsKey(tableNumber)) {
            return null;
        }
        return reservations.get(restName).get(tableNumber).get(dateTime);
    }
}
