package ir.ie.mizdooni.utils;

import ir.ie.mizdooni.commons.Request;

public class Parser {

    public static Request parse(String input) {
        System.out.println("Parsing nigger!\n");
        int spaceIndex = input.indexOf(' ');
        String command = input.substring(0, spaceIndex);
        String data = input.substring(spaceIndex + 1);
        Request r = new Request(command, data);
        System.out.println("Parsed some niggers!");
        return r;
    }
}