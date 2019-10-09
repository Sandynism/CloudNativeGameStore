package com.cognizant.productservice.dao;

import com.cognizant.productservice.model.Product;

import java.util.List;

public interface ProductDao {

    Product createProduct(Product product);

    Product getProduct(Integer productId);

    void updateProduct(Product product);

    void deleteProduct(Integer productId);

    List<Product> getAllProducts();
}
