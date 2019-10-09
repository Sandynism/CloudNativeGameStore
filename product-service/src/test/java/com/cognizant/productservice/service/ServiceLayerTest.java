package com.cognizant.productservice.service;

import com.cognizant.productservice.dao.ProductDao;
import com.cognizant.productservice.dao.ProductDaoJdbcTemplateImpl;
import com.cognizant.productservice.model.Product;
import com.cognizant.productservice.model.ProductViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

public class ServiceLayerTest {

    private ProductDao pd;
    private ServiceLayer sl;

    private static final Integer P_ID = 1;
    private static final Product P_INPUT1 = new Product("Macbook Pro", "Greatest laptop ever", new BigDecimal("2900.00"), new BigDecimal("2400.00"));
    private static final Product P_OUTPUT1 = new Product(1, "Macbook Pro", "Greatest laptop ever", new BigDecimal("2900.00"), new BigDecimal("2400.00"));
    private static final Product P_INPUT2 = new Product("iPhone", "X Max Model", new BigDecimal("1200.00"), new BigDecimal("900.00"));
    private static final Product P_OUTPUT2 = new Product(2, "iPhone", "X Max Model", new BigDecimal("1200.00"), new BigDecimal("900.00"));
    private static final Product P_OUTPUT3 = new Product(3, "Cardigan", "Cotton Sweater", new BigDecimal("55.00"), new BigDecimal("25.00"));
    private static final ProductViewModel PVM_INPUT1 = new ProductViewModel("Macbook Pro", "Greatest laptop ever", new BigDecimal("2900.00"), new BigDecimal("2400.00"));

    @Before
    public void setUp() throws Exception {
        setUpProductDaoMocks();

        sl = new ServiceLayer(pd);
    }

    private void setUpProductDaoMocks() {
        pd = mock(ProductDaoJdbcTemplateImpl.class);

        //add
        doReturn(P_OUTPUT1).when(pd).createProduct(P_INPUT1);

        //get by id
        doReturn(P_OUTPUT1).when(pd).getProduct(P_ID);

        //get all
        List<Product> pList = new ArrayList<>();
        pList.add(P_OUTPUT1);
        pList.add(P_OUTPUT2);
        doReturn(pList).when(pd).getAllProducts();

        //update
        doNothing().when(pd).updateProduct(P_OUTPUT3);
        doReturn(P_OUTPUT3).when(pd).getProduct(3);

        //delete
        doNothing().when(pd).deleteProduct(10);
        doReturn(null).when(pd).getProduct(10);
    }

    @Test
    public void createGetProduct() {
        ProductViewModel pvm = sl.createProduct(PVM_INPUT1);

        ProductViewModel fromService = sl.getProduct(pvm.getProductId());

        assertEquals(pvm, fromService);
    }


    @Test
    public void updateProduct() {
        Product product = P_OUTPUT3;

        ProductViewModel pvm = new ProductViewModel();
        pvm.setProductId(P_OUTPUT3.getProductId());
        pvm.setProductName(P_OUTPUT3.getProductName());
        pvm.setProductDescription(P_OUTPUT3.getProductDescription());
        pvm.setListPrice(P_OUTPUT3.getListPrice());
        pvm.setUnitCost(P_OUTPUT3.getUnitCost());

        sl.updateProduct(pvm);

        ProductViewModel fromService = sl.getProduct(pvm.getProductId());

        assertEquals(pvm, fromService);
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteProduct() {
        sl.deleteProduct(10);

        ProductViewModel pvm = sl.getProduct(10);
    }

    @Test
    public void getAllProducts() {
        List<ProductViewModel> pvmList = sl.getAllProducts();
        assertEquals(pvmList.size(), 2);
    }
}