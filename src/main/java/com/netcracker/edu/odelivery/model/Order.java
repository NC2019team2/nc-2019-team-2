package com.netcracker.edu.odelivery.model;

import com.netcracker.edu.odelivery.database.annotation.Attribute;
import com.netcracker.edu.odelivery.database.annotation.ObjectType;
import com.netcracker.edu.odelivery.database.annotation.Reference;
import com.netcracker.edu.odelivery.model.attributes.OrderAttributes;

import java.math.BigDecimal;

@ObjectType(objType = OrderAttributes.OBJECT_TYPE_ID)
public class Order extends Entity{
    @Reference(attrId = OrderAttributes.CLIENT_ID)
    private Long clientId;

    @Reference(attrId = OrderAttributes.RESTAURANT_ID)
    private Long restaurantId;

    @Reference(attrId = OrderAttributes.COURIER_ID)
    private Long courierId;

    @Attribute(attrId = OrderAttributes.ORDER_STATUS)
    private String orderStatus;

    @Attribute(attrId = OrderAttributes.ORDER_PRICE)
    private BigDecimal orderPrice;

    @Attribute(attrId = OrderAttributes.CLIENT_LOCATION)
    private String location;

    @Attribute(attrId = OrderAttributes.PAYMENT_METHOD)
    private String paymentMethod;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
