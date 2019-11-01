package com.netcracker.edu.nc2019team2.delivery.database.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import com.netcracker.edu.nc2019team2.delivery.model.Client;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepo extends CrudRepository<Client, Long> {

    @Query("select id, name from client where id=:id")
    List<Client> findClientsById(@Param("id") long id);

    @Query("select id, name from client where rownum>=:min AND rownum<=:max")
    List<Client> findClientsBetween(@Param("min") long min,@Param("max") long max);

    @Query("delete client where id=:id")
    void deleteClientById(@Param("id") long id);
}
