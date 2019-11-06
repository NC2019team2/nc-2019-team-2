package com.netcracker.edu.odelivery.model.courier;

import com.netcracker.edu.odelivery.model.Person;

public class Courier extends Person implements CourierAttributes {
    private String vehicle;

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
}
