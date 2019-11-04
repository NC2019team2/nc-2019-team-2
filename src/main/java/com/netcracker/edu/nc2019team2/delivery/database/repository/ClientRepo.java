package com.netcracker.edu.nc2019team2.delivery.database.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import com.netcracker.edu.nc2019team2.delivery.database.entity.Client;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ClientRepo extends CrudRepository<Client, Long> {

    List<Client> findClientsById(long id);

    List<Client> findClientsBetween(long min, long max);

    void deleteClientById(long id);
}
