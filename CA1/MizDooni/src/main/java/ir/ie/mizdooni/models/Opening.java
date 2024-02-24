package ir.ie.mizdooni.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Opening {
    Integer seatNumber;
    String tableNumber;
    ArrayList<LocalDateTime> availableTimes;

    public Opening(String tableNumber, Integer seatsNumber, ArrayList<LocalDateTime> availableTimes) {
        this.availableTimes = availableTimes;
        this.seatNumber = seatsNumber;
        this.tableNumber = tableNumber;
    }

}
