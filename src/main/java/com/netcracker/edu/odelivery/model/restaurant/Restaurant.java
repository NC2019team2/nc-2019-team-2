package com.netcracker.edu.odelivery.model.restaurant;

import com.netcracker.edu.odelivery.model.Entity;
import com.netcracker.edu.odelivery.model.item.Item;

import java.math.BigDecimal;
import java.util.Map;

public class Restaurant extends Entity implements RestaurantAttributes {
    private Boolean status;
    private String location;
    private Map<Item, BigDecimal> menu;
}
