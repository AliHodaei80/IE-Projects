package ir.ie.mizdooni.services;

import ir.ie.mizdooni.exceptions.EmailAlreadyTaken;
import ir.ie.mizdooni.exceptions.InvalidUserRole;
import ir.ie.mizdooni.exceptions.UserNameAlreadyTaken;
import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.storage.Users;
import ir.ie.mizdooni.definitions.Locations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UserHandler {
    private static UserHandler userHandler;
    private final Users users;
    private final List<String> usersRole;
    private User currentUser;

    private UserHandler() {
        users = new Users().loadFromFile(Locations.USERS_LOCATION, Users.class);
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
        users.addUser(username, email, UserRole.getUserRole(role), password, address);
    }

    public User getUserByUsername(String username) {
        return users.getUserByUsername(username);
    }

    public UserRole getUserRole(String username) {
        User u = getUserByUsername(username);
        if (u == null) {
            return null;
        } else {
            return u.getRole();
        }
    }

    private boolean isUsernameUnique(String username) {
        return users.getUserByUsername(username) == null;
    }

    private boolean isEmailUnique(String username) {
        return users.getUserByEmail(username) == null;
    }

    private boolean isUserRoleValid(String role) {
        return UserRole.getUserRole(role) != null;
    }
    public boolean doesUserExist(String username) {return users.getUserByUsername(username) != null;}

    public static UserHandler getInstance() {
        if (userHandler == null)
            userHandler = new UserHandler();
        return userHandler;
    }

    public Users getUsers() {
        return users;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isUserLoggedIn() {
        return currentUser != null;
    }

    public void logout() {
        currentUser = null;
    }

    public boolean isPasswordCorrect(String username, String password) {
        User user = users.getUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    public void loginUser(String username) {
        currentUser = users.getUserByUsername(username);
    }
}
