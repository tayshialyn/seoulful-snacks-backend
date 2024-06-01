package com.seoulful.snack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data // getters and setters as well toString, etc
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @Column(nullable = false, columnDefinition="LONGTEXT")
    @NotBlank(message = "Description cannot be blank.")
    private String description;

    @Column(nullable = false)
    @Min(value = 0, message = "Price must be greater than zero.")
    private BigDecimal price;

    @Column(name = "image_path")
    private String imagePath;

    public Product(String name, String description, BigDecimal price, String imagePath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
    }
}
