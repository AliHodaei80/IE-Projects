package ir.ie.mizdooni.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Map;


@Entity
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String city;
    private String country;

    public UserAddress() {
    }

    public UserAddress(String city, String country) {
        this.city = city;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @JsonIgnore
    public Map<String, String> getAddressMap() {
        return Map.of("city", city, "country", country);
    }
}
