package com.seoulful.snack.controller;

import com.seoulful.snack.model.Product;
import com.seoulful.snack.repository.ProductRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")

public class Products {
    @Autowired
    ProductRepository productRepository;

// Add a new product
    @PostMapping(path = "/add")  // Specific path for adding products
    public ResponseEntity<Object> addNewProduct(@Nullable @RequestParam String name,
                                                @Nullable @RequestParam Double price,
                                                @Nullable @RequestParam String description) throws Exception {
        try {
            Product product = new Product(name, price, description);  // Modify constructor based on your Product class
            productRepository.save(product);
            return new ResponseEntity<>("Product added successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to add product.", HttpStatus.BAD_REQUEST);
        }
    }
}

