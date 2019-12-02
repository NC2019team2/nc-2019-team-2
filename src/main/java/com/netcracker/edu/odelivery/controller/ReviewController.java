package com.netcracker.edu.odelivery.controller;

import com.netcracker.edu.odelivery.model.Review;
import org.springframework.web.bind.annotation.*;

public class ReviewController {
    @PostMapping("/review-create")
    public void createReview(@RequestParam(name = "review") Review review){

    }
    @GetMapping("/review-{name}")
    public Review getReview(@RequestParam(name = "id")String id, @PathVariable(name = "name")String name){
        return new Review();
    }
    @PutMapping("/review-{name}-update")
    public Review updateReview(@RequestParam(name = "id")String id, @PathVariable(name = "name")String name){
        return new Review();
    }
    @DeleteMapping("/review-{name}-delete")
    public boolean deleteReview(@RequestParam(name = "id")String id, @PathVariable(name = "name")String name){
        return false;
    }
}
