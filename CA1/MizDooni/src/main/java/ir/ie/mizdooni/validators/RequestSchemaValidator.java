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

    public static boolean checkKeyInclusion(Map<String, Object> data, List<String> keys) {
        for (String key : keys) {
            if (!data.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    public static boolean patternMatches(String str, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(str)
                .matches();
    }

    public static boolean emailCheck(String email) {
        String regexPattern = "^(.+)@(\\S+)$";
        return patternMatches(email, regexPattern);
    }

    public static boolean validateTime(String timeString) {
        String regexPattern = "^([0-1]?[0-9]|2[0-3]):[0][0]$";
        return patternMatches(timeString, regexPattern);
    }

    public static boolean usernameCheck(String username) {
        return !(username.contains("!") || username.contains("@")
                || username.contains("&") || username.contains(" ")); // TODO complete if there is more
    }

    public static boolean validateAddUser(Map<String, Object> data) {
        return checkKeyInclusion(data, userAdditionKeys) // TODO fix this(some cases will fail)
                && checkKeyInclusion((Map<String, Object>) (data.get("address")), userAdditionAddressKeys);
    }

    public static boolean validateAddRest(Map<String, Object> data) {
        return checkKeyInclusion(data, restAdditionAddressKeys) // TODO fix this(some cases will fail)
                && checkKeyInclusion((Map<String, Object>) (data.get("address")), restAdditionAddressKeys)
                && validateTime((String) data.get("endTime")) && validateTime((String) data.get("startTime"));

    }

    public static boolean validate(Request r) {
        String op = r.getOperation();

        return false;
    }
}
