package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.INVALID_RATING_FORMAT;

public class InvalidRatingFormat extends Exception {
    public InvalidRatingFormat(String field) {
        super(INVALID_RATING_FORMAT + "Field : " + field);
    }

}
