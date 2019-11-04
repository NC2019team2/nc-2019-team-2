package com.netcracker.edu.nc2019team2.delivery.controller;

import com.netcracker.edu.nc2019team2.delivery.model.Client;
import com.netcracker.edu.nc2019team2.delivery.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    ClientService clientService;

    @RequestMapping("/{id}")
    public Client getUser(@PathVariable String id) {
        return clientService.getClientByID(id);
    }

    @RequestMapping("/")
    public List<Client> getListUser(@RequestParam(value = "from", defaultValue = "1") String from,
                                    @RequestParam(value = "to", defaultValue = "1") String to) {
        return clientService.getFirstNumberUser(from, to);
    }

    @RequestMapping("/createUser")
    public void createUser(@RequestParam(value = "id") String id,
                           @RequestParam(value = "name") String name) {
        clientService.createClient(name, id);
    }

    @RequestMapping("/updateUser")
    public void updateUser(@RequestParam(value = "id") String id,
                           @RequestParam(value = "name") String name) {
        clientService.updateClient(name, id);
    }

    @RequestMapping("/deleteUser")
    public void deleteUser(@RequestParam(value = "id") String id) {
        clientService.deleteClient(id);
    }
}
