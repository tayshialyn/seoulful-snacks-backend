package com.seoulful.snack.controller;

import com.seoulful.snack.exception.ResourceNotFoundException;
import com.seoulful.snack.model.Product;
import com.seoulful.snack.model.Subscription;
import com.seoulful.snack.model.User;
import com.seoulful.snack.repository.ProductRepository;
import com.seoulful.snack.repository.SubscriptionRepository;
import com.seoulful.snack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")   // Allow requests to load resources on other servers. https://spring.io/guides/gs/rest-service-cors
@RequestMapping("/user/api/subscription")
public class SubscriptionController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{email}")
    public ResponseEntity<Object> getSubscriptionsByUserId(@PathVariable String email) {
        try {
            Optional<User> users = userRepository.findByEmail(email);
            if (users.isEmpty()) {
                throw new ResourceNotFoundException("Customer Not Found");
            }

            List<Subscription> subscriptions = subscriptionRepository.findByUser(users.get());
            return new ResponseEntity(subscriptions, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<Object> addNewSubscription(
            @PathVariable("id") Long id,
            @RequestBody Map<String, String> subscriptionRequest)
            throws Exception {

        try {
            User subscribedUser = userRepository.findByEmail(subscriptionRequest.get("email")).orElseThrow(() -> new ResourceNotFoundException("Customer Not Found"));

            Subscription subscription = new Subscription();
            subscription.setUser(subscribedUser);

            Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
            subscription.setProduct(product);

            subscription.setQuantity(Integer.parseInt(subscriptionRequest.get("qty")));
            subscription.setMailing_address(subscriptionRequest.get("mailing_address"));
            subscription.setSubscribed_on(java.time.LocalDateTime.now());
            subscription.setExpired_on(subscription.getSubscribed_on().plusDays(30));

            subscriptionRepository.save(subscription);

            return new ResponseEntity<>(subscription, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }
}
