package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.INVALID_DATETIME;

public class InvalidDateTime extends Exception {
    public InvalidDateTime() {
        super(INVALID_DATETIME);
    }

}
