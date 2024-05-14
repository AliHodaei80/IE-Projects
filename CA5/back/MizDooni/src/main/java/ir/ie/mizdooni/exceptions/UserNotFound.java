package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.USER_NOT_FOUND;

public class UserNotFound extends Exception {
    public UserNotFound() {
        super(USER_NOT_FOUND);
    }

}
