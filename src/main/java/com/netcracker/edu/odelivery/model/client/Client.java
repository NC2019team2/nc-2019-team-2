package com.netcracker.edu.odelivery.model.client;


import com.netcracker.edu.odelivery.model.Person;

import java.util.Date;

public class Client extends Person implements ClientAttributes {

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
