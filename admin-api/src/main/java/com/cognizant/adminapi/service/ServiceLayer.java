package com.cognizant.adminapi.service;

import com.cognizant.adminapi.exception.NoSuchProductException;
import com.cognizant.adminapi.model.Product;
import com.cognizant.adminapi.model.ProductViewModel;
import com.cognizant.adminapi.util.feign.*;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class ServiceLayer {

    CustomerClient customerClient;
    ProductClient productClient;
    LevelUpClient levelUpClient;
    InventoryClient inventoryClient;
    InvoiceClient invoiceClient;

//    @Autowired
//    public ServiceLayer(CustomerClient customerClient, ProductClient productClient, LevelUpClient levelUpClient, InventoryClient inventoryClient, InvoiceClient invoiceClient) {
//        this.customerClient = customerClient;
//        this.productClient = productClient;
//        this.levelUpClient = levelUpClient;
//        this.inventoryClient = inventoryClient;
//        this.invoiceClient = invoiceClient;
//    }

    @Autowired
    public ServiceLayer(ProductClient productClient) {
//        this.customerClient = customerClient;
        this.productClient = productClient;
//        this.levelUpClient = levelUpClient;
//        this.inventoryClient = inventoryClient;
//        this.invoiceClient = invoiceClient;
    }

    public ProductViewModel createProduct(ProductViewModel pvm) {
        Product product = new Product();
        product.setProductName(pvm.getProductName());
        product.setProductDescription(pvm.getProductDescription());
        product.setListPrice(pvm.getListPrice());
        product.setUnitCost(pvm.getUnitCost());

        product = productClient.createProduct(product);

        return buildProductViewModel(product);
    }

    public ProductViewModel getProduct(Integer productId) throws NoSuchProductException {
        Product product;

        try {
            product = productClient.getProduct(productId);
        } catch (FeignException.NotFound fe) {
            System.out.println("Product " + productId + " could not be found. " + fe.getMessage());
            throw new NoSuchProductException(productId);
        }

        return buildProductViewModel(product);
    }


    public void updateProduct(ProductViewModel pvm) throws NoSuchProductException {
        try {
            int id = productClient.getProduct(pvm.getProductId()).getProductId();
        } catch (FeignException.NotFound fe) {
            System.out.println("Product " + pvm.getProductId() + " could not be found. " + fe.getMessage());
            throw new NoSuchProductException(pvm.getProductId());
        }

        Product product = new Product();
        product.setProductId(pvm.getProductId());
        product.setProductName(pvm.getProductName());
        product.setProductDescription(pvm.getProductDescription());
        product.setListPrice(pvm.getListPrice());
        product.setUnitCost(pvm.getUnitCost());

        productClient.updateProduct(product, product.getProductId());
    }

    public void deleteProduct(Integer productId) throws NoSuchProductException {
        try {
            int id = productClient.getProduct(productId).getProductId();
        } catch (FeignException.NotFound fe) {
            System.out.println("Product " + productId + " could not be found. " + fe.getMessage());
            throw new NoSuchProductException(productId);
        }

        productClient.deleteProduct(productId);
    }

    public List<ProductViewModel> getAllProducts() {
        List<Product> productList = productClient.getAllProducts();
        List<ProductViewModel> pvmList = new ArrayList<>();

        for (Product p : productList) {
            pvmList.add(buildProductViewModel(p));
        }

        return pvmList;
    }

    private ProductViewModel buildProductViewModel(Product product) {
        ProductViewModel pvm = new ProductViewModel();
        pvm.setProductId(product.getProductId());
        pvm.setProductName(product.getProductName());
        pvm.setProductDescription(product.getProductDescription());
        pvm.setListPrice(product.getListPrice());
        pvm.setUnitCost(product.getUnitCost());

        return pvm;
    }

}
