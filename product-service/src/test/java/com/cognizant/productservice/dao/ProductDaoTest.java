package com.cognizant.productservice.dao;

import com.cognizant.productservice.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductDaoTest {

    @Autowired
    ProductDao pd;

    private static final Integer P_ID = 1;
    private static final Product P_INPUT1 = new Product("Macbook Pro", "Greatest laptop ever", new BigDecimal("2900.00"), new BigDecimal("2400.00"));
    private static final Product P_OUTPUT1 = new Product(1, "Macbook Pro", "Greatest laptop ever", new BigDecimal("2900.00"), new BigDecimal("2400.00"));
    private static final Product P_INPUT2 = new Product("iPhone", "X Max Model", new BigDecimal("1200.00"), new BigDecimal("900.00"));

    @Before
    public void setUp() throws Exception {
        List<Product> productList = pd.getAllProducts();
        productList.stream().forEach(p -> pd.deleteProduct(p.getProductId()));
    }

    @Test
    public void createGetDeleteProduct() {
        Product product = pd.createProduct(P_INPUT1);

        Product product2 = pd.getProduct(product.getProductId());

        assertEquals(product, product2);

        pd.deleteProduct(product.getProductId());

        product2 = pd.getProduct(product.getProductId());

        assertNull(product2);
    }

    @Test
    public void updateProduct() {
        Product product = pd.createProduct(P_INPUT1);

        product.setProductDescription("Greatest laptop ever in 2019");

        pd.updateProduct(product);

        Product updatedProduct = pd.getProduct(product.getProductId());
        assertEquals(product, updatedProduct);
    }


    @Test
    public void getAllProducts() {
        Product product1 = pd.createProduct(P_INPUT1);
        Product product2 = pd.createProduct(P_INPUT2);

        List<Product> productList = pd.getAllProducts();
        assertEquals(productList.size(), 2);
    }
}