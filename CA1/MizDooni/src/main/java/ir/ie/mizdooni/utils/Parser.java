package ir.ie.mizdooni.utils;

import ir.ie.mizdooni.commons.Request;

public class Parser {

    public static Request parse(String input) {
        int spaceIndex = input.indexOf(' ');
        String command = input.substring(0, spaceIndex);
        String data = input.substring(spaceIndex + 1);
        return new Request(command, data);
    }
}