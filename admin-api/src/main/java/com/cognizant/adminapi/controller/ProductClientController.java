package com.cognizant.adminapi.controller;

import com.cognizant.adminapi.exception.NoSuchProductException;
import com.cognizant.adminapi.exception.NotFoundException;
import com.cognizant.adminapi.service.ServiceLayer;
import com.cognizant.adminapi.model.ProductViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value="/products")
public class ProductClientController {

    @Autowired
    ServiceLayer sl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductViewModel createProduct(@RequestBody ProductViewModel pvm) {
        return sl.createProduct(pvm);
    }

    @GetMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductViewModel getProduct(@PathVariable(name="productId") Integer productId) throws NoSuchProductException{
        ProductViewModel pvm = sl.getProduct(productId);

        if (pvm == null)
            throw new NoSuchProductException(productId);

        return pvm;
    }

    @PutMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody ProductViewModel pvm, @PathVariable(name="productId") Integer productId) throws NoSuchProductException{
        ProductViewModel product = sl.getProduct(pvm.getProductId());

        if (product == null)
            throw new NoSuchProductException(productId);

        sl.updateProduct(pvm);
    }

    @DeleteMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable(name="productId") Integer productId) throws NoSuchProductException{
        ProductViewModel product = sl.getProduct(productId);

        if (product == null)
            throw new NoSuchProductException(productId);

        sl.deleteProduct(productId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductViewModel> getAllProducts() throws NotFoundException {
        List<ProductViewModel> productList = sl.getAllProducts();

        if (productList != null && productList.size() == 0) {
            throw new NotFoundException("There are no products available.");
        }

        return productList;
    }

}
