package ir.ie.mizdooni.services;

import ir.ie.mizdooni.exceptions.EmailAlreadyTaken;
import ir.ie.mizdooni.exceptions.InvalidUserRole;
import ir.ie.mizdooni.exceptions.UserNameAlreadyTaken;
import ir.ie.mizdooni.storage.Users;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UserHandler {
    private static UserHandler userHandler;
    private final Users users;
    private final List<String> usersRole;

    private UserHandler() {
        users = new Users();
        usersRole = Arrays.asList("manager", "client");
    }

    public void addUser(String username, String email, String role, String password, Map<String, String> address)
            throws UserNameAlreadyTaken, EmailAlreadyTaken, InvalidUserRole {
        if (!isUsernameUnique(username)) {
            throw new UserNameAlreadyTaken();
        }
        if (!isEmailUnique(email)) {
            throw new EmailAlreadyTaken();
        }
        if (!isUserRoleValid(role)) {
            throw new InvalidUserRole();
        }
        users.addUser(username, email, role, password, address);
    }

    private boolean isUsernameUnique(String username) {
        return users.getUserByUsername(username) == null;
    }

    private boolean isEmailUnique(String username) {
        return users.getUserByEmail(username) == null;
    }

    private boolean isUserRoleValid(String role) {
        return usersRole.contains(role);
    }

    public static UserHandler getInstance() {
        if (userHandler == null)
            userHandler = new UserHandler();
        return userHandler;
    }
}
