package ir.ie.mizdooni.utils;

import ir.ie.mizdooni.commons.Request;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
}