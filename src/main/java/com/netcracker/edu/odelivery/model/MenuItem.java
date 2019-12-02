package com.netcracker.edu.odelivery.model;

import com.netcracker.edu.odelivery.database.annotation.Attribute;
import com.netcracker.edu.odelivery.database.annotation.ObjectType;
import com.netcracker.edu.odelivery.model.attributes.ItemAttributes;

import java.math.BigDecimal;

@ObjectType(objType = ItemAttributes.OBJECT_TYPE_ID)
public class MenuItem extends Entity  {
    @Attribute(attrId = ItemAttributes.CALORIES)
    private Integer calories;
    @Attribute(attrId = ItemAttributes.ITEM_PRICE)
    private BigDecimal price;
    @Attribute(attrId = ItemAttributes.ITEM_COMPOSITION)
    private String composition;
    @Attribute(attrId = ItemAttributes.WEIGHT)
    private Float weight;
    @Attribute(attrId = ItemAttributes.SPICY)
    private Boolean isSpicy;
    @Attribute(attrId = ItemAttributes.ITEM_DESCRIPTION)
    private String description;

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Boolean getSpicy() {
        return isSpicy;
    }

    public void setSpicy(Boolean spicy) {
        isSpicy = spicy;
    }
}
