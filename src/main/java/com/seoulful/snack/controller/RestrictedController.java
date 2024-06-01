package com.seoulful.snack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seoulful.snack.exception.ResourceNotFoundException;
import com.seoulful.snack.model.Product;
import com.seoulful.snack.repository.ProductRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.UUID;

@Controller
@RequestMapping("/restricted/api/")
@CrossOrigin("*")
public class RestrictedController {
    @Autowired
    ProductRepository productRepository;


    // TODO get product by id (DONE)
    @GetMapping("/product/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id){
        try{
            Product result = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

