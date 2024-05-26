package com.seoulful.snack.repository;

import com.seoulful.snack.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
