package ir.ie.mizdooni.storage;

import ir.ie.mizdooni.models.Reservation;

import java.util.List;
import java.util.Date;
import java.util.Map;

public class Reservations {
    // Outter 3 is restaurent name
    // Outter 2 is table id
    // OUter 1 is the reservation date
    Map<String, Map<Integer, Map<Date, Reservation>>> reservations;
}
