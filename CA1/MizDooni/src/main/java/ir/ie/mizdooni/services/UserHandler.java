package ir.ie.mizdooni.services;

public class UserHandler {
    private static UserHandler userHandler;
    private UserHandler() {
    }

    public static UserHandler getInstance() {
        if (userHandler == null)
            userHandler = new UserHandler();
        return userHandler;
    }
}
