package com.netcracker.edu.odelivery.model;

import java.math.BigDecimal;

public class OrderItem {
    Order order;
    MenuItem menuItem;
    BigDecimal price;

    public OrderItem(Order order, MenuItem menuItem, BigDecimal price) {
        this.order = order;
        this.menuItem = menuItem;
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
