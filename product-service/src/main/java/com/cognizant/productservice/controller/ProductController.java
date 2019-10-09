package com.cognizant.productservice.controller;

import com.cognizant.productservice.exception.NotFoundException;
import com.cognizant.productservice.model.ProductViewModel;
import com.cognizant.productservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    ServiceLayer sl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductViewModel createProduct(@RequestBody ProductViewModel pvm) {
        return sl.createProduct(pvm);
    }

    @GetMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductViewModel getProduct(@PathVariable Integer productId) {
        ProductViewModel pvm = sl.getProduct(productId);

        if (pvm == null)
            throw new NotFoundException("Product with ID " + productId + " does not exist.");

        return pvm;
    }

    @PutMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody ProductViewModel pvm, @PathVariable Integer productId) {
        ProductViewModel product = sl.getProduct(productId);

        if (product == null)
            throw new NotFoundException("Product with ID " + productId + " does not exist.");

        sl.updateProduct(pvm);
    }

    @DeleteMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Integer productId) {
        sl.deleteProduct(productId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductViewModel> getAllProducts() {
        List<ProductViewModel> productList = sl.getAllProducts();

        if (productList != null && productList.size() == 0) {
            throw new NotFoundException("There are no products available.");
        }

        return productList;
    }

}
