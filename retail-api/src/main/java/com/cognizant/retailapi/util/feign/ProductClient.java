package com.cognizant.retailapi.util.feign;

import com.cognizant.retailapi.model.ProductViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {

    @PostMapping(value = "/products")
    ProductViewModel createProduct(@RequestBody ProductViewModel productViewModel);

    @GetMapping(value = "/products/{productId}")
    ProductViewModel getProduct(@PathVariable Integer productId);

    @PutMapping(value = "/products/{productId}")
    void updateProduct(@RequestBody ProductViewModel productViewModel, @PathVariable Integer productId);

    @DeleteMapping(value = "/products/{productId}")
    void deleteProduct(@PathVariable Integer productId);

    @GetMapping(value = "/products")
    List<ProductViewModel> getAllProducts();
}
