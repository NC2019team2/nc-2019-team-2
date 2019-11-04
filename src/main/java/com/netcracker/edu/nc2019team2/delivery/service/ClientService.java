package com.netcracker.edu.nc2019team2.delivery.service;

import com.netcracker.edu.nc2019team2.delivery.model.Client;
import com.netcracker.edu.nc2019team2.delivery.database.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("prototype")
public class ClientService {
    @Autowired
    ClientRepo clientRepo;

    public Client getClientByID(String id) {
        return clientRepo.findClientsById(Integer.parseUnsignedInt(id)).get(0);
    }

    public List<Client> getFirstNumberUser(String from, String to) {
        return clientRepo.findClientsBetween(Integer.parseUnsignedInt(from), Integer.parseUnsignedInt(to));
    }

    public void createClient(String name, String id) {
        clientRepo.save(new Client(Integer.parseUnsignedInt(id), name));
    }

    public void updateClient(String name, String id) {
        Client client = clientRepo.findClientsById(Integer.parseUnsignedInt(id)).get(0);
        client.setName(name);
        clientRepo.save(client);
    }

    public void deleteClient(String id) {
        clientRepo.deleteClientById(Integer.parseUnsignedInt(id));
    }
}
