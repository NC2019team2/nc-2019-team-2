package com.netcracker.edu.odelivery.service;

import com.netcracker.edu.odelivery.database.manager.EntityManager;
import com.netcracker.edu.odelivery.model.Client;
import com.netcracker.edu.odelivery.model.Entity;
import com.netcracker.edu.odelivery.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope("prototype")
public class ClientService {

    @Autowired
    EntityManager entityManager;

    OrderService orderService;

    public Client getClientByID(String id) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return (Client) entityManager.getEntityById(Long.parseLong(id), Client.class);
    }

    public List<Client> getFirstNumberUser(String from, String to) {
        return new ArrayList<Client>();
    }

    public List<Entity> getAllObjects() {
        return entityManager.getAllObjects();
    }

    public void createClient(String name, String id) {
        Client client =new Client();
        client.setEmail("olegdobrev@gmail.com");
        client.setDefaultLocation("Odessa");
        client.setLastName("Dobrev1");
        client.setName("Oleg");
        //client.setId(13L);
        //client.setName("Oleg");
        client.setPhone(970535827L);
        //client.setRating(5);

        List<Entity> a=entityManager.getAllObjects();
        for (Entity entity:a){
            System.out.println(entity.getId()+" Name: "+entity.getName());
        }

    }

    public void updateClient(String name, String id) {
    }

    public void deleteClient(String id) {
    }

    public void makeOrder(Long clientId, Long restaurantId) {
        Order order = new Order();
        Long id = order.getId();
        order.setClientId(clientId);
        order.setRestaurantId(restaurantId);
        order.setOrderPrice(orderService.calculateCost(id));
        // orderStatus is NEW right now, so restaurant manager must validate order and then change status to READY TO DELIVERY
        // free courier can accept order from list of available orders and change status to ACCEPTED

        entityManager.save(order);
    }

    public void updateOrder(Order order) {

    }

    public void confirmOrder(Order order) {
        orderService.changeStatus(order.getId(), 1L);
    }

    public void cancelOrder() {

    }
}
