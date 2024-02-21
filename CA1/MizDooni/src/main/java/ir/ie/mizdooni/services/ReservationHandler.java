package ir.ie.mizdooni.services;

public class ReservationHandler {
    private static ReservationHandler reservationHandler;
    private ReservationHandler() {
    }

    public static ReservationHandler getInstance() {
        if (reservationHandler == null)
            reservationHandler = new ReservationHandler();
        return reservationHandler;
    }
}
