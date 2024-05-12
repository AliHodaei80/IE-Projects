package ir.ie.mizdooni.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Map;

@Entity
public class RestaurantAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String city;
    private String country;
    private String street;

    public RestaurantAddress() {

    }

    public RestaurantAddress(String city, String country, String street) {
        this.city = city;
        this.country = country;
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getStreet() {
        return street;
    }

    @JsonIgnore
    public Map<String, String> getAddressMap() {
        return Map.of("city", city, "country", country, "street", street);
    }


}
