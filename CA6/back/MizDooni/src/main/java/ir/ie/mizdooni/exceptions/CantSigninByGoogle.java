package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.CAN_NOT_SIGNIN_BY_GOOGLE;

public class CantSigninByGoogle extends Exception{
    public CantSigninByGoogle() {
        super(CAN_NOT_SIGNIN_BY_GOOGLE);
    }
}
