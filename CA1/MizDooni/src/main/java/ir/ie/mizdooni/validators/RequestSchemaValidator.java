package ir.ie.mizdooni.validators;

import ir.ie.mizdooni.commons.Request;
import ir.ie.mizdooni.exceptions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static ir.ie.mizdooni.defines.Commands.*;
import static ir.ie.mizdooni.defines.RequestKeys.*;
import static ir.ie.mizdooni.defines.TimeFormats.RESERVE_DATETIME_FORMAT;
import static ir.ie.mizdooni.defines.TimeFormats.RESTAURANT_TIME_FORMAT;

public class RequestSchemaValidator {
    final static List<String> userAdditionKeys = Arrays.asList(USERNAME_KEY, PASSWORD_KEY, USER_ROLE_KEY, EMAIL_KEY,
            USER_ADDRESS_KEY);
    final static List<String> restAdditionKeys = Arrays.asList(ADD_RESTAURANT_NAME_KEY, MANAGER_USERNAME_KEY,
            START_TIME_KEY, END_TIME_KEY, RESTAURANT_ADDRESS_KEY, DESCRIPTION_KEY, RESTAURANT_TYPE_KEY);
    final static List<String> tableAdditionKeys = Arrays.asList(TABLE_NUM_KEY, RESTAURANT_NAME_KEY,
            MANAGER_USERNAME_KEY, SEATS_NUM_KEY);
    final static List<String> tableAReserveKeys = Arrays.asList(USERNAME_KEY, RESTAURANT_NAME_KEY,
            TABLE_NUM_KEY, DATETIME_KEY);
    final static List<String> userAdditionAddressKeys = Arrays.asList(CITY_KEY, COUNTRY_KEY);
    final static List<String> restAdditionAddressKeys = Arrays.asList(CITY_KEY, COUNTRY_KEY, STREET_KEY);


    public static void checkKeyInclusion(Map<String, Object> data, List<String> keys) throws InvalidRequestFormat {
        for (String key : keys) {
            if (!data.containsKey(key)) {
                throw new InvalidRequestFormat(key);
            }
        }
    }

    public static void checkBeNaturalNumber(double num) throws InvalidNumType {
        if (num != (int) num && num >= 1)
            throw new InvalidNumType();
    }

    public static boolean patternMatches(String str, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(str)
                .matches();
    }

    public static void emailCheck(String email) throws InvalidEmailFormat {
        String regexPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!patternMatches(email, regexPattern)) {
            throw new InvalidEmailFormat();
        }
    }

    public static void validateTime(String timeString) throws InvalidTimeFormat {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(RESTAURANT_TIME_FORMAT);
            LocalTime.parse(timeString, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidTimeFormat(RESTAURANT_TIME_FORMAT);
        }
    }

    public static void validateDateTime(String dateTimeString) throws InvalidTimeFormat {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(RESERVE_DATETIME_FORMAT);
            LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidTimeFormat(RESTAURANT_TIME_FORMAT);
        }
    }

    public static void usernameCheck(String username) throws InvalidUsernameFormat {
        boolean invalid = (username.contains("!") || username.contains("@")
                || username.contains("&") || username.contains(" "));
        if (invalid) {
            throw new InvalidUsernameFormat();
        }

    }

    public static void validateAddUser(Map<String, Object> data)
            throws InvalidEmailFormat, InvalidRequestFormat, InvalidUsernameFormat {
        // TODO fix this(some cases will fail)
        checkKeyInclusion(data, userAdditionKeys);
        usernameCheck((String) data.get(USERNAME_KEY));
        emailCheck((String) data.get(EMAIL_KEY));
        checkKeyInclusion((Map<String, Object>) (data.get(USER_ADDRESS_KEY)), userAdditionAddressKeys);
    }

    public static void validateAddRest(Map<String, Object> data) throws InvalidTimeFormat, InvalidRequestFormat {
        checkKeyInclusion(data, restAdditionKeys); // TODO fix this(some cases will fail)
        checkKeyInclusion((Map<String, Object>) (data.get(RESTAURANT_ADDRESS_KEY)), restAdditionAddressKeys);
        validateTime((String) data.get(END_TIME_KEY));
        validateTime((String) data.get(START_TIME_KEY));
    }

    public static void validateAddTable(Map<String, Object> data) throws InvalidTimeFormat, InvalidRequestFormat, InvalidNumType {
        checkKeyInclusion(data, tableAdditionKeys);
        checkBeNaturalNumber((double) data.get(SEATS_NUM_KEY));
    }

    public static void validateReserveTable(Map<String, Object> data) throws InvalidTimeFormat, InvalidRequestFormat {
        checkKeyInclusion(data, tableAReserveKeys);
        validateDateTime((String) data.get(DATETIME_KEY));
    }

    // TODO add validate request for adding restaurant table
    public static void validate(Request r)
            throws InvalidTimeFormat, InvalidUsernameFormat, InvalidRequestFormat, InvalidEmailFormat, InvalidNumType {
        String op = r.getOperation();
        Map<String, Object> data = r.getData();
        switch (op) {
            case OP_ADD_USER:
                validateAddUser(data);
                break;
            case OP_ADD_RESTAURANT:
                validateAddRest(data);
                break;
            case OP_ADD_TABLE:
                validateAddTable(data);
                break;
            case OP_RESERVE_TABLE:
                validateReserveTable(data);
                break;
        }
    }
}
