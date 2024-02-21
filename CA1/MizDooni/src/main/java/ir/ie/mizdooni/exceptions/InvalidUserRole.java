package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.defines.Errors.INVALID_USER_ROLE;

public class InvalidUserRole extends Exception {
    public InvalidUserRole() {
        super(INVALID_USER_ROLE);
    }

}
