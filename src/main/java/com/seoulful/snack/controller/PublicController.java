package com.seoulful.snack.controller;

import com.seoulful.snack.exception.ResourceNotFoundException;
import com.seoulful.snack.model.Product;
import com.seoulful.snack.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/public/api/")
@CrossOrigin("*")
public class PublicController {

    // access to ProductRepository (aka dependency injection)
    @Autowired
    private ProductRepository productRepository;

    // Get all products by public users
    @GetMapping("/product")
    public ResponseEntity<Object> getAllProducts(@RequestParam(required = false) String name){

        try{
            List<Product> result = new ArrayList<>();

            if(name == null){
                result.addAll(productRepository.findAll());
            }else{
                result.addAll(productRepository.findByNameContaining(name));
            }

            if(result.isEmpty()){
                throw new ResourceNotFoundException("No products found");
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // TODO get product by id
    // Get all products by public users
    @GetMapping("/product/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") Long id){

        try{
            Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No product found"));
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
