package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.USER_NOT_EXISTS;

public class UserNotExists extends Exception {
    public UserNotExists() {
        super(USER_NOT_EXISTS);
    }

}
