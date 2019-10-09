package com.cognizant.adminapi.util.feign;

import com.cognizant.adminapi.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {

    @PostMapping(value = "/products")
    Product createProduct(@RequestBody Product product);

    @GetMapping(value = "/products/{productId}")
    Product getProduct(@PathVariable Integer productId);

    @PutMapping(value = "/products/{productId}")
    void updateProduct(@RequestBody Product product, @PathVariable Integer productId);

    @DeleteMapping(value = "/products/{productId}")
    void deleteProduct(@PathVariable Integer productId);

    @GetMapping(value = "/products")
    List<Product> getAllProducts();
}
