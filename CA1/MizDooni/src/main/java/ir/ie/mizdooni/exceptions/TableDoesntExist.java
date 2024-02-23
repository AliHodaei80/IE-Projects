package ir.ie.mizdooni.exceptions;

import static ir.ie.mizdooni.defines.Errors.TABLE_NOT_FOUND;

public class TableDoesntExist extends Exception {
    public TableDoesntExist() {
        super(TABLE_NOT_FOUND);
    }

}
