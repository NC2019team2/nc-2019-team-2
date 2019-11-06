package com.netcracker.edu.odelivery.model.item;

import com.netcracker.edu.odelivery.model.Entity;

import java.math.BigDecimal;

public class Item extends Entity implements ItemAttributes {
    private Integer calories;
    private BigDecimal price;
    private String composition;
    private Float weight;
    private Boolean isSpicy;

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
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

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Boolean isSpicy() {
        return isSpicy;
    }

    public void setSpicy(boolean spicy) {
        isSpicy = spicy;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
