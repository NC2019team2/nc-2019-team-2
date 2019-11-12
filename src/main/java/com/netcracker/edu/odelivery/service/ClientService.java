package com.netcracker.edu.odelivery.service;

import com.netcracker.edu.odelivery.database.manager.EntityManager;
import com.netcracker.edu.odelivery.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("prototype")
public class ClientService {

    @Autowired
    EntityManager<Client> entityManager;
    public Client getClientByID(String id) {
        return new Client();
    }

    public List<Client> getFirstNumberUser(String from, String to) {
        return new ArrayList<Client>();
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

        entityManager.save(client);

    }

    public void updateClient(String name, String id) {
    }

    public void deleteClient(String id) {
    }
}
