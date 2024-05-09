package ir.ie.mizdooni.storage;

import ir.ie.mizdooni.commons.HttpRequestSender;
import ir.ie.mizdooni.models.ClientUser;
import ir.ie.mizdooni.models.ManagerUser;
import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.storage.commons.Container;
import ir.ie.mizdooni.definitions.Locations;
import ir.ie.mizdooni.utils.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.definitions.RequestKeys.SEATS_NUM_KEY;

public class Users extends Container<Users>{
    Map<String, User> users;

    public Users() {
        users = new HashMap<>();
    }

    @Override
    public Users loadFromUrl(String urlPath) {
        String response = HttpRequestSender.sendGetRequest(urlPath);
        List<Map<String, Object>> usersList = Parser.parseStringToJsonArray(response);
        Users usersObject = new Users();
        for (var userMap : usersList) {
            usersObject.addUser(
                    (String) userMap.get(USERNAME_KEY),
                    (String) userMap.get(EMAIL_KEY),
                    UserRole.getUserRole((String) userMap.get(USER_ROLE_KEY)),
                    (String) userMap.get(PASSWORD_KEY),
                    (Map<String, String>) userMap.get(USER_ADDRESS_KEY)
            );
        }
        return usersObject;
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
        users.put(username, role.equals(UserRole.CLIENT) ?
                new ClientUser(username, password, email, address) :
                new ManagerUser(username, password, email, address));
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }
}
