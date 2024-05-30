package ir.ie.mizdooni.models;

import jakarta.persistence.Entity;

import java.util.Map;

@Entity
public class ClientUser extends User {
    public ClientUser(String username, String password, String email, UserAddress address) {
        super(username, password, email, address, UserRole.CLIENT);
    }

    public ClientUser(String username, String password, String email, Map<String, String> address) {
        super(username, password, email, address, UserRole.CLIENT);
    }

    public ClientUser() {
    }

}
