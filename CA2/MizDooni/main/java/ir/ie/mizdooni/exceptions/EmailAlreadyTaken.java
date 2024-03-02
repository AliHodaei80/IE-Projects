package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.EMAIL_ALREADY_TAKEN;

public class EmailAlreadyTaken extends Exception {
    public EmailAlreadyTaken() {
        super(EMAIL_ALREADY_TAKEN);
    }

}
