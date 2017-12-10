package com.teksine.queryapplication.model;

/**
 * Created by abin on 09/12/2017.
 */

public class User {
    private String firstName;
    private String lastName;
    private String photUrl;

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getPhotUrl() {
        return photUrl;
    }

    public void setPhotUrl(String photUrl) {
        this.photUrl = photUrl;
    }

    private String googleId;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String role;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String email;
    private Integer phoneNumber;
}
