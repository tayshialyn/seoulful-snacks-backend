package com.seoulful.snack.controller;

import com.seoulful.snack.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSubscriptionsByCustomerId(@PathVariable Long id) {
        try{
            Optional<Customer> customer = customerService.getCustomerByID(id);
            if (customer.isEmpty()) {
                throw new ResourceNotFoundException("Customer Not Found");
            }

            List<Subscription> subscriptions = subscriptionService.getSubscriptionsByCustomer(customer.get());
            return new ResponseEntity(subscriptions, HttpStatus.OK);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> addNewSubscription(
            @RequestParam("customer_id") Long cid,
            @RequestParam("product_id") Long pid,
            @RequestParam("quantity") int qty,
            int quantity) throws Exception {
        try{
            Optional<Subscription> createdSubscription = subscriptionService.createSubscription(cid, pid, qty);
            if(createdSubscription.isEmpty())
                throw new Exception("Unable to create subscription");

            return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
