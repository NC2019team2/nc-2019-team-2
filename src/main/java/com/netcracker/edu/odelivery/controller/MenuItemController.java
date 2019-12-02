package com.netcracker.edu.odelivery.controller;

import com.netcracker.edu.odelivery.model.MenuItem;
import org.springframework.web.bind.annotation.*;

public class MenuItemController {
    @PostMapping("/create-item")
    public void createItem(@RequestParam(name = "item") MenuItem menuItem){

    }
    @PutMapping("/item-change-status")
    public void changeStatus(@RequestParam(name = "item") MenuItem menuItem){

    }
    @PutMapping("/item-update")
    public void updateItem(@RequestParam(name = "item") MenuItem menuItem){

    }
    @GetMapping("/item-{name}")
    public MenuItem getMenuItem(@RequestParam(name = "id")String id, @PathVariable(name = "name")String name){
        return new MenuItem();
    }
    @DeleteMapping("/item-{name}-delete")
    public boolean deleteMenuItem(@RequestParam(name = "id")String id, @PathVariable(name = "name")String name){
        return false;
    }
}
