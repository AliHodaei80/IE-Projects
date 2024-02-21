package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.defines.Errors.INVALID_EMAIL_FORMAT;
import static ir.ie.mizdooni.defines.Errors.INVALID_TIME_FORMAT;

public class InvalidTimeFormat extends Exception {
    public InvalidTimeFormat() {
        super(INVALID_TIME_FORMAT);
    }

}
