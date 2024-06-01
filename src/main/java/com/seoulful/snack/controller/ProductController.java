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
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.UUID;

@Controller
@RequestMapping("/admin/api/product")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    private final Path fileStorageLocation = Paths.get("./public/product_photos").toAbsolutePath().normalize();

    public ProductController() {
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    // Validate the file submitted (only JPEG, JPG or PNG allowed)
    private boolean fileType(MultipartFile file){
        if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/jpg") && !file.getContentType().equals("image/png")) {
            return false;
        }
        return true;
    }

    // Add a new product
    @PostMapping("/add")  // Specific path for adding products
    public ResponseEntity<Object> addNewProduct(@RequestParam("data") String data, @Nullable @RequestParam("file") MultipartFile file) throws Exception {

        // Ask for an image to be uploaded
        if (file == null) {
            return new ResponseEntity<>("Please upload an image.", HttpStatus.BAD_REQUEST);
        }

        try {
            // Convert parameterised data to JSON for the product
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(data, Product.class);

            Product newProduct = new Product( // Modify constructor based on your Product class
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    "");

            // Validate File type for JPEG, JPG and PNG via function fileType(file)
            if (!fileType(file)) {
                return new ResponseEntity<>("Image format is not JPEG, JPG or PNG.", HttpStatus.BAD_REQUEST);
            }

            // If the file is NOT empty
            if (file != null && !file.isEmpty()) {

                // Generate a random file name
                String randomFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

                // Save the product image
                Path targetLocation = this.fileStorageLocation.resolve(randomFileName);
                Files.copy(file.getInputStream(), targetLocation);
                newProduct.setImagePath("/product_photos/" + randomFileName);
            }

            Product savedProduct = productRepository.save(newProduct);

            if(savedProduct.getId() <= 0)
                throw new ResourceNotFoundException("Unable to create product.");

            return new ResponseEntity<>("Product added successfully.", HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // TODO - update product (implemented)
//    @PutMapping("/{id}")
//    public ResponseEntity<Object> updateProduct(@PathVariable("id") Long id, @RequestParam("data") String data, @Nullable @RequestParam("file") MultipartFile file) throws Exception {
//
//        // Convert parameterised data to JSON for the product
//        ObjectMapper objectMapper = new ObjectMapper();
//        Product product = objectMapper.readValue(data, Product.class);
//
//        // If the file is NOT empty
//        if(file != null){
//            // Validate the file type
//            if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/jpg") ) {
//                return new ResponseEntity<>("Image format is not JPEG or JPG.", HttpStatus.BAD_REQUEST);
//            }
//        }
//
//        try {
//            Product result = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
//
//            result.setId(id);
//            result.setName(product.getName());
//            result.setDescription(product.getDescription());
//            result.setPrice(product.getPrice());
//            result.setImagePath(result.getImagePath());
//            productRepository.save(result);
//
//            // Save the product image
//            if (file != null && !file.isEmpty()) {
//                String fileName = file.getOriginalFilename();
//                Path targetLocation = this.fileStorageLocation.resolve(fileName);
//                Files.copy(file.getInputStream(), targetLocation);
//                result.setImagePath("/product_photos/" + fileName);
//            }
//
//            productRepository.save(result);
//
//            return new ResponseEntity<>(result, HttpStatus.OK);
//
//        } catch (NoSuchElementException ex) {
//            throw new ResourceNotFoundException("Resource not found. Unable to update product.");
//        }
//    }

    // TODO - delete product (implemented)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id) {

        Product result = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());

        productRepository.deleteById(id);

        boolean productExists = productRepository.existsById(id);

        if (productExists) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}

