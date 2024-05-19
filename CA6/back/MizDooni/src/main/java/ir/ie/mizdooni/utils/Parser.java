package ir.ie.mizdooni.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ir.ie.mizdooni.commons.Request;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Parser {

    public static Request parseCommand(String input) {
        int spaceIndex = input.indexOf(' ');
        String command = input.substring(0, spaceIndex);
        String data = input.substring(spaceIndex + 1);
        return new Request(command, data);
    }

    public static LocalTime parseTime(String timeString, String timeFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
        return LocalTime.parse(timeString, formatter);
    }

    public static LocalDateTime parseDateTime(String dateTimeString, String dateTimeFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    public static List<Map<String, Object>> parseStringToJsonArray(String jsonString) {
        Gson gson = new Gson();
        Type mapListType = new TypeToken<List<Map<String, Object>>>() {}.getType();
        return gson.fromJson(jsonString, mapListType);
    }

    public static Map<String, Object> parseStringToJsonObject(String jsonString) {
        Gson gson = new Gson();
        Type mapListType = new TypeToken<Map<String, Object>>() {}.getType();
        return gson.fromJson(jsonString, mapListType);
    }
}