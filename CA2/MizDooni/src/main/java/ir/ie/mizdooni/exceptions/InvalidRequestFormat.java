package ir.ie.mizdooni.exceptions;

import java.util.List;

import static ir.ie.mizdooni.definitions.Errors.INVALID_REQUEST_FORMAT;

public class InvalidRequestFormat extends Exception {
    public InvalidRequestFormat(String field) {
        super(INVALID_REQUEST_FORMAT + field);
    }

    public InvalidRequestFormat(List<String> fields) {
        super(INVALID_REQUEST_FORMAT + fields);
    }

}
