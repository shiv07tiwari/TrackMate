package com.example.shivansh.trackmate;

public class Student {
    private String name, email, roll;
    private int type;

    public Student(String name, String email, String roll, Integer type) {
        this.name = name;
        this.email = email;
        this.roll = roll;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }
}
