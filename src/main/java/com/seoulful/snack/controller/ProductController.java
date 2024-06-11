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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Controller
@RequestMapping("/admin/api/product")
@CrossOrigin("*")
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

    // TODO - Add a new product by Admin
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

            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // TODO - update product by Admin (implemented)
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable("id") Long id, @RequestParam("data") String data, @Nullable @RequestParam("file") MultipartFile file) throws Exception {

        // Ask for an image to be uploaded
        if (file == null) {
            return new ResponseEntity<>("Please upload an image.", HttpStatus.BAD_REQUEST);
        }

        try{

            // Convert parameterised data to JSON for the product
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(data, Product.class);

            Product result = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found. Unable to update product."));

            result.setId(id);
            result.setName(product.getName());
            result.setDescription(product.getDescription());
            result.setPrice(product.getPrice());
            result.setImagePath(result.getImagePath());
            productRepository.save(result);

            // If the file is NOT empty
            if (file != null && !file.isEmpty()) {

                // Generate a random file name
                String randomFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

                // Save the product image
                Path targetLocation = this.fileStorageLocation.resolve(randomFileName);
                Files.copy(file.getInputStream(), targetLocation);
                result.setImagePath("/product_photos/" + randomFileName);
            }

            productRepository.save(result);

            return new ResponseEntity<>(result, HttpStatus.OK);

        }catch(ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    // TODO - get all products by Admin (implemented)
    @GetMapping("")
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

    // TODO - delete product by Admin (implemented)
    @DeleteMapping("/delete/{id}")
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

