package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.defines.Errors.DATETIME_NOT_IN_RANGE;

public class DateTimeNotInRange extends Exception {
    public DateTimeNotInRange() {
        super(DATETIME_NOT_IN_RANGE);
    }

}
