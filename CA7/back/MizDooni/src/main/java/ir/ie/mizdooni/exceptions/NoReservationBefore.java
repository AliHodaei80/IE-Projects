package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.NO_RESERVATION_BEFORE;

public class NoReservationBefore extends Exception {
    public NoReservationBefore() {
        super(NO_RESERVATION_BEFORE);
    }

}
