package ir.ie.mizdooni.exceptions;
import static ir.ie.mizdooni.defines.Errors.REST_NAME_NOT_UNIQUE;

public class RestaurentExists extends Exception {
    public RestaurentExists() {
        super(REST_NAME_NOT_UNIQUE);
    }

}
