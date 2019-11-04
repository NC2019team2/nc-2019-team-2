package com.netcracker.edu.odelivery.model;


import java.util.Date;

public class Client extends Person {

    private String defaultLocation;
    private Date registrationDate;

    public String getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(String defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }
}
