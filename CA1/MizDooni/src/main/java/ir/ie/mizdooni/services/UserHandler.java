package ir.ie.mizdooni.services;

import ir.ie.mizdooni.storage.Users;

import java.util.ArrayList;
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

    public void addUser(String username, String email, String role, String password, Map<String, String> address) throws Exception {
        if (!isUsernameUnique(username)) {
            throw new Exception("Username should be unique. Choose another one.");
        }
        if (!isEmailUnique(email)) {
            throw new Exception("Email should be unique. Choose another one.");
        }
        if (!isUserRoleValid(role)) {
            throw new Exception("User's Role is invalid.");
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
