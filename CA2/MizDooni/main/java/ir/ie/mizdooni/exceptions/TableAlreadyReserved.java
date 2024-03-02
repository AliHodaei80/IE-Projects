package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.TABLE_ALREADY_RESERVED;

public class TableAlreadyReserved extends Exception {
    public TableAlreadyReserved() {
        super(TABLE_ALREADY_RESERVED);
    }

}
