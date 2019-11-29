package com.netcracker.edu.odelivery.model;

import com.netcracker.edu.odelivery.database.annotation.Attribute;
import com.netcracker.edu.odelivery.database.annotation.ObjectType;
import com.netcracker.edu.odelivery.model.attributes.ClientAttributes;


@ObjectType(objType = ClientAttributes.OBJECT_TYPE_ID,parentId = 1)
public class Client extends User {
    @Attribute(attrId = ClientAttributes.CLIENT_RATING)
    private Integer rating;
    @Attribute(attrId = ClientAttributes.DEFAULT_LOCATION)
    private String defaultLocation;

    public Client(){

    }

    public String getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(String defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
