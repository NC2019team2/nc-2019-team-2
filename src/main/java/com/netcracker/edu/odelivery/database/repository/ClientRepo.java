package com.netcracker.edu.odelivery.database.repository;

import org.springframework.data.repository.CrudRepository;
import com.netcracker.edu.odelivery.model.client.Client;

public interface ClientRepo extends CrudRepository<Client, Long> {

    /*List<Client> findClientsById(long id);

    List<Client> findClientsBetween(long min, long max);

    void deleteClientById(long id);*/
}
