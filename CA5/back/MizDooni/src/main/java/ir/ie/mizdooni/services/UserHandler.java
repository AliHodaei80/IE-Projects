package ir.ie.mizdooni.services;

import ir.ie.mizdooni.definitions.DataBaseUrlPath;
import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.models.ClientUser;
import ir.ie.mizdooni.models.ManagerUser;
import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.repositories.ClientRepository;
import ir.ie.mizdooni.repositories.ManagerRepository;
import ir.ie.mizdooni.storage.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserHandler {

    private ClientRepository clientUserRepository;
    private ManagerRepository managerUserRepository;
    private User currentUser;

    @Autowired
    private UserHandler(ClientRepository clientUserRepository, ManagerRepository managerUserRepository) {
        this.clientUserRepository = clientUserRepository;
        this.managerUserRepository = managerUserRepository;
        List<User> users = new ArrayList<>(new Users().loadFromUrl(DataBaseUrlPath.USERS_DATABASE_URL).getUsers().values());
        List<ClientUser> clientUsers = new ArrayList<>();
        List<ManagerUser> managerUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getRole() == UserRole.CLIENT) {
                clientUsers.add(new ClientUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getAddress()));
            } else {
                managerUsers.add(new ManagerUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getAddress()));
            }
        }
        clientUserRepository.saveAll(clientUsers);
        managerUserRepository.saveAll(managerUsers);
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
        if (role.equals(UserRole.CLIENT.toString())) {
            clientUserRepository.save(new ClientUser(username, password, email, address));
        } else {
            managerUserRepository.save(new ManagerUser(username, password, email, address));
        }
    }

    public User getUserByUsername(String username) {
        ClientUser clientUser = getClientUserByUsername(username);
        return clientUser == null ? getManagerUserByUsername(username) : clientUser;
    }

    public ClientUser getClientUserByUsername(String username) {
        return clientUserRepository.findById(username).orElse(null);
    }

    public ManagerUser getManagerUserByUsername(String username) {
        return managerUserRepository.findById(username).orElse(null);
    }


    public User getUserByEmail(String email) {
        ClientUser clientUser = clientUserRepository.findByEmail(email);
        return clientUser == null ? managerUserRepository.findByEmail(email) : clientUser;
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

    public boolean doesUserExist(String username) {
        return getUserByUsername(username) != null;
    }

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
