package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.INVALID_USERNAME_FORMAT;

public class InvalidUsernameFormat extends Exception {
    public InvalidUsernameFormat() {
        super(INVALID_USERNAME_FORMAT);
    }

}
