package ir.ie.mizdooni.validators;

import ir.ie.mizdooni.commons.Request;
import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.utils.NumberRange;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.lang3.math.NumberUtils;
import static ir.ie.mizdooni.definitions.Commands.*;
import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.definitions.TimeFormats.RESERVE_DATETIME_FORMAT;
import static ir.ie.mizdooni.definitions.TimeFormats.RESTAURANT_TIME_FORMAT;

public class RequestSchemaValidator {
    final static Set<String> userAdditionKeys = Set.of(USERNAME_KEY, PASSWORD_KEY, USER_ROLE_KEY, EMAIL_KEY,
            USER_ADDRESS_KEY);
    final static Set<String> restAdditionKeys = Set.of(ADD_RESTAURANT_NAME_KEY, MANAGER_USERNAME_KEY,
            START_TIME_KEY, END_TIME_KEY, RESTAURANT_ADDRESS_KEY, DESCRIPTION_KEY, RESTAURANT_TYPE_KEY);
    final static Set<String> tableAdditionKeys = Set.of(TABLE_NUM_KEY, RESTAURANT_NAME_KEY,
            MANAGER_USERNAME_KEY, SEATS_NUM_KEY);
    final static Set<String> tableAReserveKeys = Set.of(USERNAME_KEY, RESTAURANT_NAME_KEY,
            TABLE_NUM_KEY, DATETIME_KEY);
    final static Set<String> userAdditionAddressKeys = Set.of(CITY_KEY, COUNTRY_KEY);
    final static Set<String> restAdditionAddressKeys = Set.of(CITY_KEY, COUNTRY_KEY, STREET_KEY);
    final static Set<String> searchRestaurantByTypeKeys = Set.of(RESTAURANT_TYPE_KEY);
    final static Set<String> searchRestaurantByNameKeys = Set.of(RESTAURANT_SEARCH_NAME_KEY);
    final static Set<String> showReservationHistoryKeys = Set.of(USERNAME_KEY);
    final static Set<String> addReviewKeys = Set.of(AMBIANCE_RATE_KEY, OVERALL_RATE_KEY, FOOD_RATE_KEY,
            SERVICE_RATE_KEY, COMMENT_KEY, RESTAURANT_NAME_KEY, USERNAME_KEY);
    final static Set<String> cancelReservationKeys = Set.of(USERNAME_KEY, RESERVATION_NUM_KEY);
    final static Set<String> mapTypesKeyName = Set.of(USER_ADDRESS_KEY);
    final static Set<String> stringTypesKeyName = Set.of(USERNAME_KEY, PASSWORD_KEY,
            RESTAURANT_NAME_KEY, USER_ROLE_KEY, EMAIL_KEY, ADD_RESTAURANT_NAME_KEY, MANAGER_USERNAME_KEY,
            START_TIME_KEY, END_TIME_KEY, DESCRIPTION_KEY, RESTAURANT_TYPE_KEY, DATETIME_KEY, CITY_KEY, COUNTRY_KEY,
            STREET_KEY, COMMENT_KEY);
    final static Set<String> numTypesKeyName = Set.of(TABLE_NUM_KEY, SEATS_NUM_KEY, AMBIANCE_RATE_KEY, OVERALL_RATE_KEY,
            FOOD_RATE_KEY, SERVICE_RATE_KEY, RESERVATION_NUM_KEY);


    public static void checkKeyInclusion(Map<String, Object> data, Set<String> keys) throws InvalidRequestFormat, InvalidRequestTypeFormat {
        for (String key : keys) {
            if (!data.containsKey(key) || data.get(key) == null) {
                throw new InvalidRequestFormat(key);
            }
            if (mapTypesKeyName.contains(key) && !(data.get(key) instanceof Map)) {
                throw new InvalidRequestTypeFormat(key);
            }
            if (numTypesKeyName.contains(key) && !(data.get(key) instanceof Number) &&
                    !((data.get(key) instanceof String && NumberUtils.isParsable((String) data.get(key))))) {
                throw new InvalidRequestTypeFormat(key);
            }
            if (stringTypesKeyName.contains(key) && !(data.get(key) instanceof String)) {
                throw new InvalidRequestTypeFormat(key);
            }

        }
    }

    public static void checkIsNatural(double num) throws InvalidNumType {
        if (num != (int) num || num < 1)
            throw new InvalidNumType();
    }

    public static void checkIsNatural(int num) throws InvalidNumType {
        if (num < 1)
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
            throw new InvalidTimeFormat(RESERVE_DATETIME_FORMAT);
        }
    }

    public static void usernameCheck(String username) throws InvalidUsernameFormat {
        if (!username.matches("^\\w+$")) {
            throw new InvalidUsernameFormat();
        }
    }

    public static void validateAddUser(Map<String, Object> data)
            throws InvalidEmailFormat, InvalidRequestFormat, InvalidUsernameFormat, InvalidRequestTypeFormat {
        checkKeyInclusion(data, userAdditionKeys);
        usernameCheck((String) data.get(USERNAME_KEY));
        emailCheck((String) data.get(EMAIL_KEY));
        checkKeyInclusion((Map<String, Object>) (data.get(USER_ADDRESS_KEY)), userAdditionAddressKeys);
    }

    public static void validateAddRest(Map<String, Object> data) throws InvalidTimeFormat, InvalidRequestFormat, InvalidRequestTypeFormat {
        checkKeyInclusion(data, restAdditionKeys);
        checkKeyInclusion((Map<String, Object>) (data.get(RESTAURANT_ADDRESS_KEY)), restAdditionAddressKeys);
        validateTime((String) data.get(END_TIME_KEY));
        validateTime((String) data.get(START_TIME_KEY));
    }

    public static void validateAddTable(Map<String, Object> data)
            throws InvalidRequestFormat, InvalidNumType, InvalidRequestTypeFormat {
        checkKeyInclusion(data, tableAdditionKeys);
        try {
            checkIsNatural((double) data.get(SEATS_NUM_KEY));
        } catch (ClassCastException e) {
            try {
                checkIsNatural((int) data.get(SEATS_NUM_KEY));
            } catch (ClassCastException e2) {
                checkIsNatural(Double.parseDouble((String) data.get(SEATS_NUM_KEY)));
            }
        }
    }

    public static void validateReserveTable(Map<String, Object> data) throws InvalidTimeFormat, InvalidRequestFormat, InvalidRequestTypeFormat {
        checkKeyInclusion(data, tableAReserveKeys);
        validateDateTime((String) data.get(DATETIME_KEY));
    }

    public static void validateSearchRestaurantByType(Map<String, Object> data) throws InvalidRequestFormat, InvalidRequestTypeFormat {
        checkKeyInclusion(data, searchRestaurantByTypeKeys);
        if (data.get(RESTAURANT_TYPE_KEY) == null) {
            throw new InvalidRequestFormat(RESTAURANT_TYPE_KEY);
        }

    }

    public static void validateSearchRestaurantByName(Map<String, Object> data) throws InvalidRequestFormat, InvalidRequestTypeFormat {
        checkKeyInclusion(data, searchRestaurantByNameKeys);
        if (data.get(RESTAURANT_SEARCH_NAME_KEY) == null) {
            throw new InvalidRequestFormat(RESTAURANT_SEARCH_NAME_KEY);
        }

    }

    public static void validateShowReservationHistory(Map<String, Object> data) throws InvalidRequestFormat, InvalidRequestTypeFormat {
        checkKeyInclusion(data, showReservationHistoryKeys);
    }

    public static void checkRatingFieldRange(Map<String, Object> data, String field)  throws InvalidRatingFormat{
        Double number;
        if (data.get(field) instanceof String) {
            number = Double.parseDouble((String) data.get(field));
        } else {
            number = (Double) data.get(field);
        }
        if (!NumberRange.isInRange(number)) {
            throw new InvalidRatingFormat(field);
        }
    }

    public static void validateAddReview(Map<String, Object> data) throws InvalidRequestFormat, InvalidRatingFormat, InvalidRequestTypeFormat {
        checkKeyInclusion(data, addReviewKeys);
        checkRatingFieldRange(data, FOOD_RATE_KEY);
        checkRatingFieldRange(data, SERVICE_RATE_KEY);
        checkRatingFieldRange(data, OVERALL_RATE_KEY);
        checkRatingFieldRange(data, AMBIANCE_RATE_KEY);

    }
    public static void validateCancelReservation(Map<String, Object> data) throws InvalidRequestFormat, InvalidRequestTypeFormat {
        checkKeyInclusion(data, cancelReservationKeys);
    }

    public static void validate(Request r)
            throws InvalidTimeFormat, InvalidUsernameFormat, InvalidRequestFormat, InvalidEmailFormat, InvalidNumType, InvalidRatingFormat, InvalidRequestTypeFormat {
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
            case OP_ADD_REVIEW:
                validateAddReview(data);
                break;
            case OP_SHOW_RESERVATION_HISTORY:
                validateShowReservationHistory(data);
                break;
            case OP_CANCEL_RESERVATION:
                validateCancelReservation(data);
                break;

        }
    }
}
