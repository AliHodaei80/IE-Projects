package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.MANAGER_USERNAME_NOT_MATCH;

public class ManagerUsernameNotMatch extends Exception {
    public ManagerUsernameNotMatch() {
        super(MANAGER_USERNAME_NOT_MATCH);
    }

}
