package com.netcracker.edu.odelivery.model;

import com.netcracker.edu.odelivery.database.annotation.Attribute;
import com.netcracker.edu.odelivery.database.annotation.ObjectType;
import com.netcracker.edu.odelivery.model.attributes.CourierAttributes;

@ObjectType(objType = CourierAttributes.OBJECT_TYPE_ID)
public class Courier extends User {
    @Attribute(attrId = CourierAttributes.VEHICLE)
    private String vehicle;
    @Attribute(attrId = CourierAttributes.COURIER_RATING)
    private Integer rating;

    public Courier() {

    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
