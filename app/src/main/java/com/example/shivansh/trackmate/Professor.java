package com.example.shivansh.trackmate;

public class Professor {
    private String name, email, location, nextAvailable;
    private Boolean isOnline;
    private int type;

    Professor(String name, String email, String location, String nextAvailable, Boolean isOnline, Integer type) {
        this.name = name;
        this.email = email;
        this.location = location;
        this.nextAvailable = nextAvailable;
        this.isOnline = isOnline;
        this.type = type;
    }
    Professor() {}

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNextAvailable(String nextAvailable) {
        this.nextAvailable = nextAvailable;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public String getNextAvailable() {
        return nextAvailable;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }
}
