package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.RESTUARANT_NOT_FOUND;

public class RestaurantNotFound extends Exception {
    public RestaurantNotFound() {
        super(RESTUARANT_NOT_FOUND);
    }

}
