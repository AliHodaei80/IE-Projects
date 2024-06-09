package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.AUTHENTICATION_FAILED;

public class AuthenticationFailed extends Exception{
    public AuthenticationFailed() {
        super(AUTHENTICATION_FAILED);
    }
}
