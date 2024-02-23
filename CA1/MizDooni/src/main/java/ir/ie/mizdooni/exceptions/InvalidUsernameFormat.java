package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.defines.Errors.INVALID_USERNAME_FORMAT;

public class InvalidUsernameFormat extends Exception {
    public InvalidUsernameFormat() {
        super(INVALID_USERNAME_FORMAT);
    }

}
