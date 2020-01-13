package com.netcracker.edu.odelivery.service;

import com.netcracker.edu.odelivery.database.manager.EntityManager;
import com.netcracker.edu.odelivery.model.Order;
import com.netcracker.edu.odelivery.model.Client;
import com.netcracker.edu.odelivery.model.RestaurantManager;
import com.netcracker.edu.odelivery.model.Restaurant;
import com.netcracker.edu.odelivery.model.Courier;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

@Service
public class OrderService {

    EntityManager<Order> entityManager;

    // stage allowing changes to the order
    public void createDraft(Client user, Restaurant restaurant) {
        Order order = new Order();
        order.setClientId(user.getId());
        order.setRestaurantId(restaurant.getId());
        order.setOrderStatus("DRAFT");
        entityManager.save(order);
    }

    // the order confirmation stage
    public void submitOrder(Long orderId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Order order = entityManager.getEntityById(orderId, Order.class);
        order.setOrderStatus("SUBMITTED");
        entityManager.save(order);
    }

    // the order is transferred to the restaurant and confirmed by the manager
    public void workOnOrder(Long orderId, RestaurantManager restaurantManager) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Order order = entityManager.getEntityById(orderId, Order.class);
        order.setManagerId(restaurantManager.getId());
        order.setOrderStatus("ON_WORK");
        entityManager.save(order);
    }

    // state when order is ready and located in restaurant
    public void readyForDelivery(Long orderId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Order order = entityManager.getEntityById(orderId, Order.class);
        order.setOrderStatus("READY_FOR_DELIVERY");
        entityManager.save(order);
    }

    // now order appears in the list of available orders for couriers, one of them chose the order
    public void waitForCourier(Long orderId, Courier courier) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Order order = entityManager.getEntityById(orderId, Order.class);
        order.setCourierId(courier.getId());
        order.setOrderStatus("WAIT_FOR_COURIER");
        entityManager.save(order);
    }

    // the courier takes the order and goes to the destination
    public void deliveringOrder(Long orderId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Order order = entityManager.getEntityById(orderId, Order.class);
        order.setOrderStatus("DELIVERING");
        entityManager.save(order);
    }

    // the courier is at the destination and waits for the client
    public void deliveredOrder(Long orderId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Order order = entityManager.getEntityById(orderId, Order.class);
        order.setOrderStatus("DELIVERED");
        entityManager.save(order);
    }

    // client received and paid for the order
    public void closeOrder(Long orderId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Order order = entityManager.getEntityById(orderId, Order.class);
        order.setOrderStatus("CLOSED");
        entityManager.save(order);
    }

    public BigDecimal calculateCost(Long orderId) {
        BigDecimal cost = new BigDecimal("0");
        return cost;
    }
}
