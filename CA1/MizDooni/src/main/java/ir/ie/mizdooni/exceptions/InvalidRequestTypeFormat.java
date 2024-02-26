package ir.ie.mizdooni.exceptions;

import java.util.List;

import static ir.ie.mizdooni.definitions.Errors.INVALID_REQUEST_TYPE_FORMAT;

public class InvalidRequestTypeFormat extends Exception {
    public InvalidRequestTypeFormat(String field) {
        super(INVALID_REQUEST_TYPE_FORMAT + field);
    }

    public InvalidRequestTypeFormat(List<String> fields) {
        super(INVALID_REQUEST_TYPE_FORMAT + fields);
    }
}
