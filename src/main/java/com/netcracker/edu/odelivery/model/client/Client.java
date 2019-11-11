package com.netcracker.edu.odelivery.model.client;


import com.netcracker.edu.odelivery.database.annotation.Attribute;
import com.netcracker.edu.odelivery.database.annotation.ObjProperties;
import com.netcracker.edu.odelivery.model.Person;

import java.util.Date;

@ObjProperties(objType = ClientAttributes.OBJECT_TYPE_ID,parentId = 1)
public class Client extends Person {
    @Attribute(attrId = ClientAttributes.DEFAULT_LOCATION)
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
