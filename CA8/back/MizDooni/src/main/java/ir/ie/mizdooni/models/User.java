package ir.ie.mizdooni.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Map;

import static ir.ie.mizdooni.commons.PasswordHasher.hashAndEncodePassword;

@MappedSuperclass
public abstract class User {
    @Id
    String username;
    String password;
    String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    UserAddress address;
    @Enumerated(EnumType.STRING)
    UserRole role;

    public User(String username, String password, String email, Map<String, String> address, UserRole role) {
        this.username = username;
        this.password = hashAndEncodePassword(password);
        this.email = email;
        this.address = new UserAddress(address.get("city"), address.get("country"));
        this.role = role;
    }

    public User(String username, String password, String email, UserAddress address, UserRole role) {
        this.username = username;
        this.password = hashAndEncodePassword(password);
        this.email = email;
        this.address = address;
        this.role = role;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public Map<String, String> getAddressMap() {
        return address.getAddressMap();
    }

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(Map<String, String> address) {
        this.address = new UserAddress(address.get("city"), address.get("country"));
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
