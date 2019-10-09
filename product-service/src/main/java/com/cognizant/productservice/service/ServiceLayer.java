package com.cognizant.productservice.service;

import com.cognizant.productservice.dao.ProductDao;
import com.cognizant.productservice.model.Product;
import com.cognizant.productservice.model.ProductViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class ServiceLayer {

    ProductDao pd;

    @Autowired
    public ServiceLayer(ProductDao pd) {
        this.pd = pd;
    }

    @Transactional
    public ProductViewModel createProduct(ProductViewModel pvm) {
        Product product = new Product();
        product.setProductName(pvm.getProductName());
        product.setProductDescription(pvm.getProductDescription());
        product.setListPrice(pvm.getListPrice());
        product.setUnitCost(pvm.getUnitCost());

        product = pd.createProduct(product);

        return buildProductViewModel(product);
    }

    public ProductViewModel getProduct(Integer productId) {
        Product product = pd.getProduct(productId);

        if (product == null) {
            return null;
        } else {
            return buildProductViewModel(product);
        }
    }

    public void updateProduct(ProductViewModel pvm) {
        Product product = new Product();
        product.setProductId(pvm.getProductId());
        product.setProductName(pvm.getProductName());
        product.setProductDescription(pvm.getProductDescription());
        product.setListPrice(pvm.getListPrice());
        product.setUnitCost(pvm.getUnitCost());

        pd.updateProduct(product);
    }

    public void deleteProduct(Integer productId) {
        Product product = pd.getProduct(productId);

        if (product == null)
            throw new NoSuchElementException("Product with ID " + productId + " does not exist.");

        pd.deleteProduct(productId);
    }

    public List<ProductViewModel> getAllProducts() {
        List<Product> productList = pd.getAllProducts();
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
