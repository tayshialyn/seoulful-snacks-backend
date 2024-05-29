package com.seoulful.snack.controller;

import com.seoulful.snack.exception.ResourceNotFoundException;
import com.seoulful.snack.model.Product;
import com.seoulful.snack.repository.ProductRepository;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
@RequestMapping("/admin/api/product")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    private final Path fileStorageLocation = Paths.get("./profile_photos").toAbsolutePath().normalize();

    public ProductController() {
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    // Add a new product
    @PostMapping(path = "/add")  // Specific path for adding products
    public ResponseEntity<Object> addNewProduct(@Valid @RequestBody Product product) throws Exception {
        try {

            Product newProduct = new Product( // Modify constructor based on your Product class
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    "");

            Product savedProduct = productRepository.save(newProduct);

            if(savedProduct.getId() <= 0)
                throw new ResourceNotFoundException("Unable to create product.");

            return new ResponseEntity<>("Product added successfully.", HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // TODO - update product
    // TODO - delete product
    // TODO - get product by id
}