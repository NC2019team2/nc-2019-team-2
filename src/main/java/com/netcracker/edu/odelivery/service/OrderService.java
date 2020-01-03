package com.netcracker.edu.odelivery.service;

import com.netcracker.edu.odelivery.database.manager.EntityManager;
import com.netcracker.edu.odelivery.model.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    EntityManager<Order> entityManager;

    public void changeStatus(Long orderId, Long orderStatus) {
        //Order order = entityManager.getEntityById(orderId, Order.class);
    }

    public BigDecimal calculateCost(Long orderId) {
        BigDecimal cost = new BigDecimal("0");
        return cost;
    }
}
