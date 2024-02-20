package com.example.recyclerview;

public class Activity2Model {
    String firstName;
    String idUrl;
    String lastName;

    public Activity2Model(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getIdUrl() {
        return idUrl;
    }
}
