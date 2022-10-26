package com.cagatayiba.todoapp.modal;

import com.cagatayiba.todoapp.enums.Gender;

public class User implements UserDTO{
    private String fullName;
    private String username;
    private String password;
    private String  gender;
    private String location;

    public User() {
    }

    public User(String fullName, String username, String password, String gender, String location) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.location = location;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
