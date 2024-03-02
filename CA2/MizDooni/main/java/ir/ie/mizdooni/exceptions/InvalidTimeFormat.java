package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.INVALID_TIME_FORMAT;

public class InvalidTimeFormat extends Exception {
    public InvalidTimeFormat(String timeFormat) {
        super(INVALID_TIME_FORMAT + timeFormat);
    }

}
