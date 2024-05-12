package ir.ie.mizdooni.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.definitions.TimeFormats.RESTAURANT_TIME_FORMAT;

@Entity
public class Restaurant {
//    @Column(unique = true)
//    @GeneratedValue(strategy = GenerationType.AUTO)
@Column(unique = true)
    Long id;
    @Id
    String name;
    Double avgAmbianceScore;
    Double avgOverallScore;
    Double avgServiceScore;
    Double avgFoodScore;
    LocalTime startTime;
    LocalTime endTime;
    String type;
    @Column(columnDefinition = "TEXT")
    String description;
    @ManyToOne
    @JoinColumn(name = "manager_user_id")
    ManagerUser managerUser;
    @Expose()
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    RestaurantAddress address;
    String imageUrl;

    public Restaurant() {
    }

    public Restaurant(String name, LocalTime startTime, LocalTime endTime, String type, String description,
                      ManagerUser managerUser, Map<String, String> address, String imageUrl, long id) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.description = description;
        this.managerUser = managerUser;
        this.address = new RestaurantAddress(address.get(CITY_KEY), address.get(COUNTRY_KEY), address.get(STREET_KEY));
        this.imageUrl = imageUrl;
        this.avgOverallScore = 0.0;
        this.avgServiceScore = 0.0;
        this.avgFoodScore = 0.0;
        this.avgAmbianceScore = 0.0;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    @JsonIgnore
    public String getStartTimeString() {
        return DateTimeFormatter.ofPattern(RESTAURANT_TIME_FORMAT).format(startTime);
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @JsonIgnore
    public String getEndTimeString() {
        return DateTimeFormatter.ofPattern(RESTAURANT_TIME_FORMAT).format(endTime);
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public Double getAvgOverallScore() {
        return avgOverallScore;
    }

    public Double getAvgServiceScore() {
        return avgServiceScore;
    }

    public Double getAvgAmbianceScore() {
        return avgAmbianceScore;
    }

    public Double getAvgFoodScore() {
        return avgFoodScore;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManagerUsername() {
        return managerUser.getUsername();
    }

    public String getImageUrl() { return imageUrl;};

    public Map<String, String> getAddress() {
        return address.getAddressMap();
    }

    @JsonIgnore
    public String getAddressString() {
        return address.getStreet() + ", " + address.getCity() + ", " + address.getCountry();
    }

    @JsonIgnore
    public String getCity() {
        return address.getCity();
    }

    @JsonIgnore
    public String getCountry() {
        return address.getCountry();
    }

    @JsonIgnore
    public String getActivityPeriod() {
        return startTime.toString() + "-" + endTime.toString();
    }

    public Double avgUpdate(Double lastAverage, Double newValue, Integer lastCount) {
        return ((lastAverage * lastCount) + newValue) / (lastCount + 1);
    }

    public void updateScores(Double newFoodScore, Double newAmbianceScore, Double newServiceScore,
            Double newOverallRate) {
        this.avgOverallScore = newOverallRate;
        this.avgAmbianceScore = newAmbianceScore;
        this.avgFoodScore = newFoodScore;
        this.avgServiceScore = newServiceScore;
    }

    public void setAddress(Map<String, String> address) {
        this.address = new RestaurantAddress(address.get(CITY_KEY), address.get(COUNTRY_KEY), address.get(STREET_KEY));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvgAmbianceScore(Double avgAmbianceScore) {
        this.avgAmbianceScore = avgAmbianceScore;
    }

    public void setAvgOverallScore(Double avgOverallScore) {
        this.avgOverallScore = avgOverallScore;
    }

    public void setAvgServiceScore(Double avgServiceScore) {
        this.avgServiceScore = avgServiceScore;
    }

    public void setAvgFoodScore(Double avgFoodScore) {
        this.avgFoodScore = avgFoodScore;
    }

    public void setManagerUser(ManagerUser managerUser) {
        this.managerUser = managerUser;
    }

    public void setAddress(RestaurantAddress address) {
        this.address = address;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
