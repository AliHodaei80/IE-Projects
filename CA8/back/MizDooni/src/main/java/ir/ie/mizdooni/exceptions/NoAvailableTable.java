package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.definitions.Errors.NO_TABLE_AVAILABLE;

public class NoAvailableTable extends Exception {
    public NoAvailableTable() {
        super(NO_TABLE_AVAILABLE);
    }

}
