package ir.ie.mizdooni.models;

import jakarta.persistence.Entity;

import java.util.Map;

@Entity
public class ManagerUser extends User {
    public ManagerUser(String username, String password, String email, UserAddress address) {
        super(username, password, email, address, UserRole.MANAGER);
    }

    public ManagerUser(String username, String password, String email, Map<String, String> address) {
        super(username, password, email, address, UserRole.MANAGER);
    }

    public ManagerUser() {
    }
}
