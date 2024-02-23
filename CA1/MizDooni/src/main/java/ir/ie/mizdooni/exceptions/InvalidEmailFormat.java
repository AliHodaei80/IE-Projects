package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.INVALID_EMAIL_FORMAT;

public class InvalidEmailFormat extends Exception {
    public InvalidEmailFormat() {
        super(INVALID_EMAIL_FORMAT);
    }

}
