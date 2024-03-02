package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.DATETIME_NOT_IN_RANGE;

public class DateTimeNotInRange extends Exception {
    public DateTimeNotInRange() {
        super(DATETIME_NOT_IN_RANGE);
    }

}
