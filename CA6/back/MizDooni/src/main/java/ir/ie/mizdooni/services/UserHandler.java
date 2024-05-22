package ir.ie.mizdooni.services;

import ir.ie.mizdooni.commons.HttpRequestSender;
import ir.ie.mizdooni.definitions.DataBaseUrlPath;
import ir.ie.mizdooni.exceptions.*;
import ir.ie.mizdooni.models.ClientUser;
import ir.ie.mizdooni.models.ManagerUser;
import ir.ie.mizdooni.models.User;
import ir.ie.mizdooni.models.UserRole;
import ir.ie.mizdooni.repositories.ClientRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ir.ie.mizdooni.repositories.ManagerRepository;
import ir.ie.mizdooni.utils.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ir.ie.mizdooni.definitions.RequestKeys.*;

@Service
public class UserHandler implements UserDetailsService {

    private final ClientRepository clientUserRepository;
    private final ManagerRepository managerUserRepository;
    private User currentUser;

    @Autowired
    private UserHandler(ClientRepository clientUserRepository, ManagerRepository managerUserRepository) {
        this.clientUserRepository = clientUserRepository;
        this.managerUserRepository = managerUserRepository;
        if (clientUserRepository.count() == 0 && managerUserRepository.count() == 0) {
            loadInitDatabase();
        }
    }

    private void loadInitDatabase() {
        List<ClientUser> clientUsers = new ArrayList<>();
        List<ManagerUser> managerUsers = new ArrayList<>();
        String response = HttpRequestSender.sendGetRequest(DataBaseUrlPath.USERS_DATABASE_URL);
        List<Map<String, Object>> usersList = Parser.parseStringToJsonArray(response);
        for (var userMap : usersList) {
            if (UserRole.getUserRole((String) userMap.get(USER_ROLE_KEY)) == UserRole.CLIENT) {
                clientUsers.add(new ClientUser(
                        (String) userMap.get(USERNAME_KEY),
                        (String) userMap.get(PASSWORD_KEY),
                        (String) userMap.get(EMAIL_KEY),
                        (Map<String, String>) userMap.get(USER_ADDRESS_KEY)
                ));
            } else {
                managerUsers.add(new ManagerUser(
                        (String) userMap.get(USERNAME_KEY),
                        (String) userMap.get(PASSWORD_KEY),
                        (String) userMap.get(EMAIL_KEY),
                        (Map<String, String>) userMap.get(USER_ADDRESS_KEY)
                ));
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

    public void updateOrAddUser(String username, String email, String role, String password, Map<String, String> address)
            throws UserNameAlreadyTaken, EmailAlreadyTaken, InvalidUserRole, CantSigninByGoogle {
         User user = getUserByEmail(username);
        if (user == null) {
            User userByUsername = getUserByUsername(username);
            if (userByUsername == null) {
                addUser(username, email, role, password, address);
            } else {
                throw new CantSigninByGoogle();
            }
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= getUserByUsername(username);
        if ( user == null){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        if (!user.getUsername().equals(username)) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        else {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                    .roles(user.getRole().getValue())
                    .build();
        }
        }
}
