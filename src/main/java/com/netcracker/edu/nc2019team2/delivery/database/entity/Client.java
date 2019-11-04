package com.netcracker.edu.nc2019team2.delivery.database.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

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
