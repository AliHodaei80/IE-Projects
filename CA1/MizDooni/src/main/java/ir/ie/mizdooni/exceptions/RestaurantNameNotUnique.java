package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.defines.Errors.REST_NAME_NOT_UNIQUE;

public class RestaurantNameNotUnique extends Exception {
    public RestaurantNameNotUnique() {
        super(REST_NAME_NOT_UNIQUE);
    }

}
