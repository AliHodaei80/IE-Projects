package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.TABLE_ALREADY_EXISTS;

public class TableAlreadyExists extends Exception {
    public TableAlreadyExists() {
        super(TABLE_ALREADY_EXISTS);
    }

}
