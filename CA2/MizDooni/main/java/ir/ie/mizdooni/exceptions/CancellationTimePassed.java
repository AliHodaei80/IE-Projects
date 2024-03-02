package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.CANCELLATION_TIME_PASSED;

public class CancellationTimePassed extends Exception {
    public CancellationTimePassed() {
        super(CANCELLATION_TIME_PASSED);
    }

}
