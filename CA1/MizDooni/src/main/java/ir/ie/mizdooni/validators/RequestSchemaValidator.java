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

import static ir.ie.mizdooni.definitions.Commands.*;
import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.definitions.TimeFormats.RESERVE_DATETIME_FORMAT;
import static ir.ie.mizdooni.definitions.TimeFormats.RESTAURANT_TIME_FORMAT;

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
    final static List<String> searchRestaurantByTypeKeys = Arrays.asList(RESTAURANT_TYPE_KEY);
    final static List<String> searchRestaurantByNameKeys = Arrays.asList(RESTAURANT_SEARCH_NAME_KEY);
    final static List<String> showReservationHistoryKeys = Arrays.asList(USERNAME_KEY);

    public static void checkKeyInclusion(Map<String, Object> data, List<String> keys) throws InvalidRequestFormat {
        for (String key : keys) {
            if (!data.containsKey(key)) {
                throw new InvalidRequestFormat(key);
            }
        }
    }

    public static void checkBeNaturalNumber(double num) throws InvalidNumType {
        if (num != (int) num || num < 1)
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
        checkKeyInclusion(data, userAdditionKeys);
        usernameCheck((String) data.get(USERNAME_KEY));
        emailCheck((String) data.get(EMAIL_KEY));
        checkKeyInclusion((Map<String, Object>) (data.get(USER_ADDRESS_KEY)), userAdditionAddressKeys);
    }

    public static void validateAddRest(Map<String, Object> data) throws InvalidTimeFormat, InvalidRequestFormat {
        checkKeyInclusion(data, restAdditionKeys); 
        checkKeyInclusion((Map<String, Object>) (data.get(RESTAURANT_ADDRESS_KEY)), restAdditionAddressKeys);
        validateTime((String) data.get(END_TIME_KEY));
        validateTime((String) data.get(START_TIME_KEY));
    }

    public static void validateAddTable(Map<String, Object> data)
            throws InvalidTimeFormat, InvalidRequestFormat, InvalidNumType {
        checkKeyInclusion(data, tableAdditionKeys);
        checkBeNaturalNumber((double) data.get(SEATS_NUM_KEY));
    }

    public static void validateReserveTable(Map<String, Object> data) throws InvalidTimeFormat, InvalidRequestFormat {
        checkKeyInclusion(data, tableAReserveKeys);
        validateDateTime((String) data.get(DATETIME_KEY));
    }

    public static void validateSearchRestaurantByType(Map<String, Object> data) throws InvalidRequestFormat {
        checkKeyInclusion(data, searchRestaurantByTypeKeys);
        if (data.get(RESTAURANT_TYPE_KEY) == null) {
            throw new InvalidRequestFormat(RESTAURANT_TYPE_KEY);
        }

    }

    public static void validateSearchRestaurantByName(Map<String, Object> data) throws InvalidRequestFormat {
        checkKeyInclusion(data, searchRestaurantByNameKeys);
        if (data.get(RESTAURANT_SEARCH_NAME_KEY) == null) {
            throw new InvalidRequestFormat(RESTAURANT_SEARCH_NAME_KEY);
        }

    }

    public static void validateShowReservationHistory(Map<String, Object> data) throws InvalidRequestFormat {
        checkKeyInclusion(data, showReservationHistoryKeys);
    }

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
            case OP_SEARCH_RESTAURANT_BY_TYPE:
                validateSearchRestaurantByType(data);
                break;
            case OP_SEARCH_RESTAURANT_BY_NAME:
                validateSearchRestaurantByName(data);
                break;
            case OP_SHOW_RESERVATION_HISTORY:
                validateShowReservationHistory(data);
                break;
        }
    }
}
