package com.seoulful.snack.repository;

import com.seoulful.snack.controller.Products;
import com.seoulful.snack.model.Product;

public interface ProductRepository {
    void save(Product product);
}
