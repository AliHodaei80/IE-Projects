package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.INVALID_ACCESS;

public class InvalidAccess extends Exception{
    public InvalidAccess() {
        super(INVALID_ACCESS);
    }
}
