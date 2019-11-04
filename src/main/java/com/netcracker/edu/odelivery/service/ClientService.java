package com.netcracker.edu.odelivery.service;

import com.netcracker.edu.odelivery.model.Client;
import com.netcracker.edu.odelivery.database.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("prototype")
public class ClientService {
    @Autowired
    ClientRepo clientRepo;

    public Client getClientByID(String id) {
        return new Client();
    }

    public List<Client> getFirstNumberUser(String from, String to) {
        return new ArrayList<Client>();
    }

    public void createClient(String name, String id) {
    }

    public void updateClient(String name, String id) {
    }

    public void deleteClient(String id) {
    }
}
