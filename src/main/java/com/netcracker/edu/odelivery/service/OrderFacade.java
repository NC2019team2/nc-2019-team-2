package com.netcracker.edu.odelivery.service;

import com.netcracker.edu.odelivery.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderFacade {
    public Order placeOrder(List<Long> menuItemsList){
        return new Order();
    }
}
