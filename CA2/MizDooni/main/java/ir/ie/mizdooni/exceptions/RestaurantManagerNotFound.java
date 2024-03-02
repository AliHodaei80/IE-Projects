package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.REST_M_NOT_FOUND;

public class RestaurantManagerNotFound extends Exception {
    public RestaurantManagerNotFound() {
        super(REST_M_NOT_FOUND);
    }

}
