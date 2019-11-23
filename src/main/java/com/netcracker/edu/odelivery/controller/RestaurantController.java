package com.netcracker.edu.odelivery.controller;

import com.netcracker.edu.odelivery.model.Item;
import com.netcracker.edu.odelivery.model.Menu;
import com.netcracker.edu.odelivery.model.Restaurant;
import com.netcracker.edu.odelivery.model.Review;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestaurantController {
    @GetMapping("/restaurant/{name}")
    public Restaurant getRestaurant(@PathVariable(name = "name") String name){
        //Поиск по имени всего ресторана
        return new Restaurant();
    }
    @GetMapping("/restaurant-{id}/menu-{name}")
    public Menu getMenu(@PathVariable(name = "id") Long idRestaurant,
                        @PathVariable(name = "name") String nameMenu){
        //Поиск меню
        return new Menu();
    }
    @PostMapping("/create-menu")
    public void createMenu(@RequestParam(name = "menu")Menu menu){

    }
    @PostMapping("/create-restaurant")
    public void createRestaurant(@RequestParam(name = "restaurant") Restaurant restaurant){

    }
    @PostMapping("/create-item")
    public void createItem(@RequestParam(name = "item") Item item){

    }
    @PutMapping("/validate-restaurant-{name}")
    public void validateRestaurant(  @PathVariable(name = "name") String nameRestaurant){

    }
    @PutMapping("/item-change-status")
    public void changeStatus(@RequestParam(name = "item")Item item){

    }
    @PostMapping("/review-create")
    public void createReview(@RequestParam(name = "review")Review review){

    }
}
