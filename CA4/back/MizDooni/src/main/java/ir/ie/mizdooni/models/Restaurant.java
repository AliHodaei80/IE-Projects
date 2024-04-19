package ir.ie.mizdooni.models;

import com.google.gson.annotations.Expose;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static ir.ie.mizdooni.definitions.RequestKeys.*;
import static ir.ie.mizdooni.definitions.TimeFormats.RESTAURANT_TIME_FORMAT;

public class Restaurant {
    @Expose()
    Long id;
    @Expose()
    String name;
    @Expose()
    Double avgAmbianceScore;
    @Expose()
    Double avgOverallScore;
    @Expose()
    Double avgServiceScore;
    @Expose()
    Double avgFoodScore;
    @Expose()
    LocalTime startTime;
    @Expose()
    LocalTime endTime;
    @Expose()
    String type;
    @Expose()
    String description;
    @Expose(serialize = false)
    String managerUsername;
    @Expose()
    Map<String, String> address;
    @Expose()
    String imageUrl;

    public Restaurant(String name, LocalTime startTime, LocalTime endTime, String type, String description,
                      String managerUsername, Map<String, String> address, String imageUrl, Long id) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.description = description;
        this.managerUsername = managerUsername;
        this.address = address;
        this.id = id;
        this.imageUrl = imageUrl;
        this.avgOverallScore = 0.0;
        this.avgServiceScore = 0.0;
        this.avgFoodScore = 0.0;
        this.avgAmbianceScore = 0.0;
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

    public String getStartTimeString() {
        return DateTimeFormatter.ofPattern(RESTAURANT_TIME_FORMAT).format(startTime);
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

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
        return managerUsername;
    }

    public void setManagerUsername(String managerUsername) {
        this.managerUsername = managerUsername;
    }

    public Map<String, String> getAddress() {
        return address;
    }

    public String getAddressString() {
        return address.get(STREET_KEY) + ", " + address.get(CITY_KEY) + ", " + address.get(COUNTRY_KEY);
    }

    public String getCity() {
        return address.get("city");
    }

    public String getCountry() {
        return address.get("country");
    }

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
        this.address = address;
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
}
