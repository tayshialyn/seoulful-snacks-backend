package com.seoulful.snack.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restricted/subscription")
public class RestrictedSubscriptionController {

    // checkout product
    @PostMapping("/checkout")
    public String checkout() {
        return "checkout";
    }


}
