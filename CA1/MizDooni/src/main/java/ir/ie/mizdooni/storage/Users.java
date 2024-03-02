package ir.ie.mizdooni.storage;

import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.storage.commons.Container;
import ir.ie.mizdooni.definitions.Locations;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Users extends Container<Users>{
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
        this.saveToFile(Locations.USERS_LOCATION);
        users.put(username, new User(username, password, email, address, role));
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }
}
