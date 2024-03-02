package ir.ie.mizdooni.models;

public enum UserRole {
    MANAGER("manager"),
    CLIENT("client");

    private final String value;

    private UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return this.value;
    }

    public static UserRole getUserRole(String role) {
        switch (role) {
            case "manager":
                return MANAGER;
            case "client":
                return CLIENT;
            default:
                return null;
        }
    }
}
