package com.example.recyclerview;

import com.google.firebase.firestore.Exclude;

import java.util.Map;

public class Model {


    String firstName;

    String lastName;

    public Model(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }



    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }



}
