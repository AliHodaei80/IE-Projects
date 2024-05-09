package ir.ie.mizdooni.services;

import ir.ie.mizdooni.definitions.DataBaseUrlPath;
import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.repositories.UserRepository;
import ir.ie.mizdooni.storage.Users;
import ir.ie.mizdooni.definitions.Locations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class UserHandler {
//    private final Users users;
    private UserRepository userRepository;
    private User currentUser;

    @Autowired
    private UserHandler(UserRepository userRepository) {
//        users = new Users().loadFromUrl(DataBaseUrlPath.USERS_DATABASE_URL);
        this.userRepository = userRepository;
        userRepository.saveAll(new Users().loadFromUrl(DataBaseUrlPath.USERS_DATABASE_URL).getUsers().values());
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
        userRepository.save(new User(username, password, email, address, UserRole.getUserRole(role)));
//        users.addUser(username, email, UserRole.getUserRole(role), password, address);
    }

    public User getUserByUsername(String username) {
        return userRepository.findById(username).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
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
        return getUserByUsername(username) == null;
    }

    private boolean isEmailUnique(String email) {
        return getUserByEmail(email) == null;
    }

    private boolean isUserRoleValid(String role) {
        return UserRole.getUserRole(role) != null;
    }
    public boolean doesUserExist(String username) {return getUserByUsername(username) != null;}

//    public static UserHandler getInstance() {
//        if (userHandler == null)
//            userHandler = new UserHandler();
//        return userHandler;
//    }

//    public Users getUsers() {
//        return users;
//    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isUserLoggedIn() {
        return currentUser != null;
    }

    public void logoutUser() {
        currentUser = null;
    }

    public boolean isPasswordCorrect(String username, String password) {
        User user = getUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    public void loginUser(String username, String password) throws UserNotExists, AuthenticationFailed {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new UserNotExists();
        }
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationFailed();
        }
        currentUser = user;
    }
}
