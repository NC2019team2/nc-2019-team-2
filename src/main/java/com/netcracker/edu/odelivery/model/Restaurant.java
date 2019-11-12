package com.netcracker.edu.odelivery.model;

import com.netcracker.edu.odelivery.database.annotation.Attribute;
import com.netcracker.edu.odelivery.database.annotation.AttributeList;
import com.netcracker.edu.odelivery.database.annotation.ObjectType;
import com.netcracker.edu.odelivery.database.annotation.Reference;
import com.netcracker.edu.odelivery.model.attributes.RestaurantAttributes;

import java.util.List;

@ObjectType(objType = RestaurantAttributes.OBJECT_TYPE_ID)
public class Restaurant extends Entity{
    @AttributeList(attrId = RestaurantAttributes.RESTAURANT_STATUS)
    private Boolean status;
    @Attribute(attrId = RestaurantAttributes.RESTAURANT_LOCATION)
    private String location;
    @Reference(attrId = RestaurantAttributes.MENU_REFERENCE)
    private List<Menu> menu;
    @Attribute(attrId = RestaurantAttributes.RESTAURANT_DESCRIPTION)
    private String description;
    @Reference(attrId = RestaurantAttributes.MANAGER_ID)
    private RestaurantManager restaurantManager;
    @Attribute(attrId = RestaurantAttributes.CUISINE)
    private String cuisine;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RestaurantManager getRestaurantManager() {
        return restaurantManager;
    }

    public void setRestaurantManager(RestaurantManager restaurantManager) {
        this.restaurantManager = restaurantManager;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }
}
