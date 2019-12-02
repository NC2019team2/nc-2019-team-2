package com.netcracker.edu.odelivery.controller;

import com.netcracker.edu.odelivery.model.Menu;
import org.springframework.web.bind.annotation.*;

public class MenuController {
    @GetMapping("/restaurant-{id}/menu-{name}")
    public Menu getMenu(@PathVariable(name = "id") Long idRestaurant,
                        @PathVariable(name = "name") String nameMenu){
        //Поиск меню
        return new Menu();
    }
    @PostMapping("/create-menu")
    public void createMenu(@RequestParam(name = "menu")Menu menu){

    }
    @PutMapping("/restaurant-{id}/menu-{name}")
    public Menu updateMenu(@PathVariable(name = "id") Long idRestaurant,
                        @PathVariable(name = "name") String nameMenu){
        //Обновление меню
        return new Menu();
    }
    @DeleteMapping("/restaurant-{id}/menu-{name}-delete")
    public boolean deleteMenu(@PathVariable(name = "id") Long idRestaurant,
                              @PathVariable(name = "name") String nameMenu){
        return false;
    }
}
