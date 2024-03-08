package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.USERNAME_ALREADY_TAKEN;

public class UserNameAlreadyTaken extends Exception {
    public UserNameAlreadyTaken() {
        super(USERNAME_ALREADY_TAKEN);
    }

}
