package ir.ie.mizdooni.models;

import com.google.gson.annotations.Expose;

import java.time.LocalTime;
import java.util.Map;

public class Restaurant {
    @Expose()
    String name;
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

    public Restaurant(String name, LocalTime startTime, LocalTime endTime, String type, String description,
                      String managerUsername, Map<String, String> address) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.description = description;
        this.managerUsername = managerUsername;
        this.address = address;
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

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
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

    public void setAddress(Map<String, String> address) {
        this.address = address;
    }
}
