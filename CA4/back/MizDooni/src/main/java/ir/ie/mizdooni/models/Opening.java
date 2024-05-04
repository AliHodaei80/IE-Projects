package ir.ie.mizdooni.models;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Opening {
    @Expose
    int seatNumber;
    @Expose
    Long tableNumber;
    @Expose
    ArrayList<LocalDateTime> availableTimes;

    public Opening(Long tableNumber, Integer seatsNumber, ArrayList<LocalDateTime> availableTimes) {
        this.availableTimes = availableTimes;
        this.seatNumber = seatsNumber;
        this.tableNumber = tableNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public Long getTableNumber() {
        return tableNumber;
    }

    public ArrayList<LocalDateTime> getAvailableTimes() {
        return availableTimes;
    }
}
