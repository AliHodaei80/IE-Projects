package ir.ie.mizdooni.validators;

import ir.ie.mizdooni.commons.Request;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.List;

public class RequestSchemaValidator {
    final static List<String> userAdditionKeys = Arrays.asList("username", "password", "role", "email", "address");
    final static List<String> restAdditionKeys = Arrays.asList("name", "managerUsername", "startTime", "endTime",
            "address", "description", "type");
    final static List<String> userAdditionAddressKeys = Arrays.asList("city", "country");
    final static List<String> restAdditionAddressKeys = Arrays.asList("city", "country", "street");
    final static String opAddUser = "addUser";
    final static String opAddRestaurant = "addRestaurant";
    final static String opAddTable = "addUser";

    public static void checkKeyInclusion(Map<String, Object> data, List<String> keys) throws Exception {
        for (String key : keys) {
            if (!data.containsKey(key)) {
                throw new Exception(String.format("%s field was missing", key));
            }
        }
    }

    public static boolean patternMatches(String str, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(str)
                .matches();
    }

    public static void emailCheck(String email) throws Exception {
        String regexPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!patternMatches(email, regexPattern)) {
            throw new Exception("Email format was wrong!");
        }
    }

    public static void validateTime(String timeString) throws Exception {
        String regexPattern = "^([0-1]?[0-9]|2[0-3]):[0][0]$";
        if (!patternMatches(timeString, regexPattern)) {
            throw new Exception("Time Format was wrong");
        }
    }

    public static void usernameCheck(String username) throws Exception {
        boolean invalid = (username.contains("!") || username.contains("@")
                || username.contains("&") || username.contains(" "));
        if (invalid) {
            throw new Exception("Invalid username format");
        }

    }

    public static void validateAddUser(Map<String, Object> data) throws Exception {
        // TODO fix this(some cases will fail)
        checkKeyInclusion(data, userAdditionKeys);
        usernameCheck((String) data.get("username"));
        emailCheck((String) data.get("email"));
        checkKeyInclusion((Map<String, Object>) (data.get("address")), userAdditionAddressKeys);
    }

    public static void validateAddRest(Map<String, Object> data) throws Exception {
        checkKeyInclusion(data, restAdditionAddressKeys); // TODO fix this(some cases will fail)
        checkKeyInclusion((Map<String, Object>) (data.get("address")), restAdditionAddressKeys);
        validateTime((String) data.get("endTime"));
        validateTime((String) data.get("startTime"));

    }

    public static void validate(Request r) throws Exception {
        String op = r.getOperation();
        Map<String, Object> data = r.getData();
        switch (op) {
            case opAddUser:
                System.out.println("Calling Validate add user");
                validateAddUser(data);
                break;
            case opAddRestaurant:
                validateAddRest(data);
                break;
        }
    }
}
