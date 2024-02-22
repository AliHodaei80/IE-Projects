package ir.ie.mizdooni.storage;

import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.models.UserRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Users {
    Map<String, User> users;

    public Users() {
        users = new HashMap<>();
    }

    public User getUserByUsername(String username) {
        return users.get(username);
    }


    public User getUserByEmail(String email) {
        return new ArrayList<>(users.values())
                .stream()
                .filter(user -> email.equals(user.getEmail()))
                .findAny()
                .orElse(null);

    }

    public void addUser(String username, String email, UserRole role, String password, Map<String, String> address) {
        users.put(username, new User(username, password, email, address, role));
    }
}
