package ir.ie.mizdooni.definitions;

public class Errors {
    public final static String UNSUPPORTED_COMMAND = "Unsupported command!";
    public static final String INVALID_REQUEST_FORMAT = "Invalid format for request. you should provide all necessary fields. Missing Fields: ";
    public static final String INVALID_REQUEST_TYPE_FORMAT = "Invalid format for request. Invalid type for field: ";
    public static final String INVALID_USER_ROLE = "User Role is invalid.";
    public static final String NO_TABLE_AVAILABLE = "There is no table left for reservation";
    public static final String USER_NOT_FOUND = "User Not found";
    public static final String USERNAME_ALREADY_TAKEN = "Username should be unique. Choose another one.";
    public static final String EMAIL_ALREADY_TAKEN = "Email should be unique. Choose another one.";
    public static final String INVALID_USERNAME_FORMAT = "Invalid username format";
    public static final String INVALID_EMAIL_FORMAT = "Invalid email format";
    public static final String INVALID_TIME_FORMAT = "Invalid time format. Format should be: ";
    public static final String REST_M_NOT_FOUND = "Restaurant manager was not found";
    public static final String REST_NAME_NOT_UNIQUE = "Restaurant name was not unique";
    public static final String TABLE_ALREADY_EXISTS = "Table Already Exists";
    public static final String RESTUARANT_NOT_FOUND = "Restaurant was not found";
    public static final String TABLE_NOT_FOUND = "Table was not found";
    public static final String INVALID_NUM_TYPE = "Number type is invalid";
    public static final String TABLE_ALREADY_RESERVED = "The table is already reserved";
    public static final String INVALID_DATETIME = "The datetime is invalid. The datetime should pass current datetime";
    public static final String DATETIME_NOT_IN_RANGE = "The datetime should be in range of restaurant work time.";
    public static final String INVALID_RATING_FORMAT = "The rating was out of range or in invalid format.";
    public static final String RESERVATION_NOT_FOR_USER = "The reservation does not belong to user";
    public static final String CANCELLATION_TIME_PASSED = "Cancellation Period has expired";
    public static final String USER_NOT_EXISTS = "User not exists";
    public static final String MANAGER_USERNAME_NOT_MATCH = "Username of manager not match with restaurant manager";
    public static final String AUTHENTICATION_FAILED = "Authentication failed";
    public static final String NO_RESERVATION_BEFORE = "You had no reservation before to add review";
    public static final String CAN_NOT_SIGNIN_BY_GOOGLE = "Because of username conflict You can not sign in by google";
    public static final String INVALID_ACCESS = "Invalid access";


}
