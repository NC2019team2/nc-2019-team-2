package com.netcracker.edu.odelivery.controller;


import com.netcracker.edu.odelivery.model.Restaurant;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestaurantController {
    @GetMapping("/restaurant/{name}")
    public Restaurant getRestaurant(@PathVariable(name = "name") String name){
        //Поиск по имени всего ресторана
        return new Restaurant();
    }

    @PostMapping("/create-restaurant")
    public void createRestaurant(@RequestParam(name = "restaurant") Restaurant restaurant){

    }

    @PutMapping("/validate-restaurant-{name}")
    public void validateRestaurant(  @PathVariable(name = "name") String nameRestaurant){

    }

    @DeleteMapping("/delete-restaurant-{name}")
    public void createRestaurant(@RequestParam(name = "id") String id,@PathVariable(name = "name")String name){

    }
    @PutMapping("/update-restaurant")
    public void updateRestaurant(@RequestParam(name = "restaurant") Restaurant restaurant){

    }
}
