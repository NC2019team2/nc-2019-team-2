package com.netcracker.edu.odelivery.controller;

import com.netcracker.edu.odelivery.model.Client;
import com.netcracker.edu.odelivery.model.Entity;
import com.netcracker.edu.odelivery.model.User;
import com.netcracker.edu.odelivery.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    ClientService clientService;


    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return clientService.getClientByID(id);
    }

    @GetMapping("/get-all")
    public List<Entity> getAllObjects() {
        return clientService.getAllObjects();
    }

    @GetMapping("/")
    public List<Client> getListClient(@RequestParam(value = "from", defaultValue = "1") String from,
                                    @RequestParam(value = "limit", defaultValue = "1") String limit) {
        return clientService.getFirstNumberUser(from, limit);
    }

    @PostMapping("/create-user")
    public void createUser(@RequestParam(value = "user") User user){
        //В зависимости какой User тот сервис и вызываем
    }

    @PutMapping("/update-user")
    public void updateUser(@RequestParam(value = "id") String id,
                           @RequestParam(value = "name") String name) {
        clientService.updateClient(name, id);
    }


    @DeleteMapping("/delete-user")
    public void deleteUser(@RequestParam(value = "user") User user) {
        //В зависимости какой User
    }
}
