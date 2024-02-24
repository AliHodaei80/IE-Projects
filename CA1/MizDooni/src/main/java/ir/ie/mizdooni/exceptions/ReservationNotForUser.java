package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.RESERVATION_NOT_FOR_USER;

public class ReservationNotForUser extends Exception {
    public ReservationNotForUser() {
        super(RESERVATION_NOT_FOR_USER);
    }

}
