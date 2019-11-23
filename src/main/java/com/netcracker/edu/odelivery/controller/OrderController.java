package com.netcracker.edu.odelivery.controller;

import com.netcracker.edu.odelivery.model.Order;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    @PostMapping("/create-order")
    public void createOrder(@RequestParam(name = "order")Order order){

    }

    @PutMapping("/update-order")
    public void updateOrder(@RequestParam(name = "order")Order order){

    }
    @GetMapping("/history-order")
    public List<Order> getHistory(@RequestParam(value = "from", defaultValue = "1") String from,
                                  @RequestParam(value = "limit", defaultValue = "10") String limit){
        return new ArrayList<>();
    }
    @GetMapping("/order-{id}-status")
    public Order checkStatusOfOrder(@PathVariable(name = "id") Long id){
        //Проверить статус заказа
        return new Order();
    }
    @PutMapping("/courier-{id}-change-order-status")
    public Order courierChangeStatus(@PathVariable(name = "id")Long id,
                                     @RequestParam(name = "order") Order order){
        //Курьер когда выполняет заказ меняет статусы заказа
        return new Order();
    }
    @PostMapping("/take-order")
    public void takeOrder(@RequestParam(name = "idCourier")Long id,
                          @RequestParam(name = "order") Order order){
        //Курьер взял заказ
    }
}
