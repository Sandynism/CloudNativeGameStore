package com.cognizant.adminapi.service;

import com.cognizant.adminapi.exception.NoSuchCustomerException;
import com.cognizant.adminapi.model.*;
import com.cognizant.adminapi.util.feign.CustomerClient;
import com.cognizant.adminapi.util.feign.LevelUpClient;
import com.cognizant.adminapi.util.feign.ProductClient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

public class ServiceLayerTest {

    private static final Integer C_ID = 1;
    private static final Customer C_INPUT1 = new Customer("Armin", "Van Buuren", "Trance", "Leiden", "2300-2334", "StateOfTrance@gmail.com", "123456789");
    private static final Customer C_OUTPUT1 = new Customer(1, "Armin", "Van Buuren", "Trance", "Leiden", "2300-2334", "StateOfTrance@gmail.com", "123456789");
    private static final Customer C_OUTPUT2 = new Customer(2, "Jai", "Wolf", "Broadway", "New York", "11001", "jaiwolf@gmail.com", "456789123");
    private static final Customer C_OUTPUT3 = new Customer(3, "Nick", "Miller", "10th St", "Denver", "54321", "illenium@gmail.com", "987654321");
    private static final CustomerViewModel CVM_INPUT1 = new CustomerViewModel("Armin", "Van Buuren", "Trance", "Leiden", "2300-2334", "StateOfTrance@gmail.com", "123456789");

    private static final Integer P_ID = 1;
    private static final Product P_INPUT1 = new Product("Macbook Pro", "Greatest laptop ever", new BigDecimal("2900.00"), new BigDecimal("2400.00"));
    private static final Product P_OUTPUT1 = new Product(1, "Macbook Pro", "Greatest laptop ever", new BigDecimal("2900.00"), new BigDecimal("2400.00"));
    private static final Product P_OUTPUT2 = new Product(2, "iPhone", "X Max Model", new BigDecimal("1200.00"), new BigDecimal("900.00"));
    private static final Product P_OUTPUT3 = new Product(3, "Cardigan", "Cotton Sweater", new BigDecimal("55.00"), new BigDecimal("25.00"));
    private static final ProductViewModel PVM_INPUT1 = new ProductViewModel("Macbook Pro", "Greatest laptop ever", new BigDecimal("2900.00"), new BigDecimal("2400.00"));

    private static final Integer L_ID = 1;
    private static final LevelUp L_INPUT1 = new LevelUp(1, 100, LocalDate.of(2012, 1, 1));
    private static final LevelUp L_OUTPUT1 = new LevelUp(1,1, 100, LocalDate.of(2012, 1, 1));
    private static final LevelUp L_OUTPUT2 = new LevelUp(2,2, 100, LocalDate.of(2019, 12, 12));
    private static final LevelUp L_OUTPUT3 = new LevelUp(3,3, 100, LocalDate.of(2017, 5, 10));
    private static final LevelUpViewModel LVM_INPUT1 = new LevelUpViewModel(1, 100, LocalDate.of(2012, 1, 1));
    private static final LevelUpViewModel LVM_OUTPUT3 = new LevelUpViewModel(3,3, 100, LocalDate.of(2017, 5, 10));

    private CustomerClient customerClient;
    private ProductClient productClient;
    private LevelUpClient levelUpClient;
    private ServiceLayer sl;

    @Before
    public void setUp() throws Exception {
        setUpCustomerClientMocks();
        setUpProductClientMocks();
        setUpLevelUpClientMocks();

        sl = new ServiceLayer(customerClient, productClient, levelUpClient);
    }

    private void setUpCustomerClientMocks() {
        customerClient = mock(CustomerClient.class);

        //add
        doReturn(C_OUTPUT1).when(customerClient).createCustomer(C_INPUT1);

        //get by id
        doReturn(C_OUTPUT1).when(customerClient).getCustomer(C_ID);

        //get all
        List<Customer> cList = new ArrayList<>();
        cList.add(C_OUTPUT1);
        cList.add(C_OUTPUT2);
        doReturn(cList).when(customerClient).getAllCustomers();

        //update
        doNothing().when(customerClient).updateCustomer(C_OUTPUT3, 3);
        doReturn(C_OUTPUT3).when(customerClient).getCustomer(3);

        //delete
        doNothing().when(customerClient).deleteCustomer(10);
        doReturn(null).when(customerClient).getCustomer(10);
    }

    private void setUpProductClientMocks() {
        productClient = mock(ProductClient.class);

        //add
        doReturn(P_OUTPUT1).when(productClient).createProduct(P_INPUT1);

        //get by id
        doReturn(P_OUTPUT1).when(productClient).getProduct(P_ID);

        //get all
        List<Product> pList = new ArrayList<>();
        pList.add(P_OUTPUT1);
        pList.add(P_OUTPUT2);
        doReturn(pList).when(productClient).getAllProducts();

        //update
        doNothing().when(productClient).updateProduct(P_OUTPUT3, 3);
        doReturn(P_OUTPUT3).when(productClient).getProduct(3);

        //delete
        doNothing().when(productClient).deleteProduct(10);
        doReturn(null).when(productClient).getProduct(10);
    }

    private void setUpLevelUpClientMocks() {
        levelUpClient = mock(LevelUpClient.class);

        //add
        doReturn(L_OUTPUT1).when(levelUpClient).createLevelUp(L_INPUT1);

        //get by id
        doReturn(L_OUTPUT1).when(levelUpClient).getLevelUp(L_ID);

        //get all
        List<LevelUp> lList = new ArrayList<>();
        lList.add(L_OUTPUT1);
        lList.add(L_OUTPUT2);
        doReturn(lList).when(levelUpClient).getAllLevelUps();

        //update
        doNothing().when(levelUpClient).updateLevelUp(L_OUTPUT3, 3);
        doReturn(L_OUTPUT3).when(levelUpClient).getLevelUp(3);

        //delete
        doNothing().when(levelUpClient).deleteLevelUp(10);
        doReturn(null).when(levelUpClient).getLevelUp(10);

        //get by customer id
        doReturn(L_OUTPUT3).when(levelUpClient).getLevelUpByCustomerId(3);
    }

    @Test
    public void createGetUpdateCustomer() {
        CustomerViewModel cvm = sl.createCustomer(CVM_INPUT1);

        CustomerViewModel fromService = sl.getCustomer(cvm.getCustomerId());

        assertEquals(cvm, fromService);

        Customer customer = C_OUTPUT3;

        cvm = new CustomerViewModel();
        cvm.setCustomerId(C_OUTPUT3.getCustomerId());
        cvm.setFirstName(C_OUTPUT3.getFirstName());
        cvm.setLastName(C_OUTPUT3.getLastName());
        cvm.setStreet(C_OUTPUT3.getStreet());
        cvm.setCity(C_OUTPUT3.getCity());
        cvm.setZip(C_OUTPUT3.getZip());
        cvm.setEmail(C_OUTPUT3.getEmail());
        cvm.setPhone(C_OUTPUT3.getPhone());

        sl.updateCustomer(cvm);

        fromService = sl.getCustomer(cvm.getCustomerId());

        assertEquals(cvm, fromService);
    }

    @Test(expected = NullPointerException.class)
    public void deleteCustomer() {

        sl.deleteCustomer(10);

        CustomerViewModel cvm = sl.getCustomer(10);
    }

    @Test
    public void getAllCustomers() {
        List<CustomerViewModel> cvmList = sl.getAllCustomers();
        assertEquals(cvmList.size(), 2);
    }

    @Test
    public void createGetUpdateProduct() {
        ProductViewModel pvm = sl.createProduct(PVM_INPUT1);

        ProductViewModel fromService = sl.getProduct(pvm.getProductId());

        assertEquals(pvm, fromService);

        Product product = P_OUTPUT3;

        pvm = new ProductViewModel();
        pvm.setProductId(P_OUTPUT3.getProductId());
        pvm.setProductName(P_OUTPUT3.getProductName());
        pvm.setProductDescription(P_OUTPUT3.getProductDescription());
        pvm.setListPrice(P_OUTPUT3.getListPrice());
        pvm.setUnitCost(P_OUTPUT3.getUnitCost());

        sl.updateProduct(pvm);

        fromService = sl.getProduct(pvm.getProductId());

        assertEquals(pvm, fromService);
    }


    @Test(expected = NullPointerException.class)
    public void deleteProduct() {
        sl.deleteProduct(10);

        ProductViewModel pvm = sl.getProduct(10);
    }

    @Test
    public void getAllProducts() {
        List<ProductViewModel> pvmList = sl.getAllProducts();
        assertEquals(pvmList.size(), 2);
    }

    @Test
    public void createGetUpdateLevelUp() {
        LevelUpViewModel lvm = sl.createLevelUp(LVM_INPUT1);

        LevelUpViewModel fromService = sl.getLevelUp(lvm.getLevelUpId());

        assertEquals(lvm, fromService);

        LevelUp levelUp = L_OUTPUT3;

        lvm = new LevelUpViewModel();
        lvm.setLevelUpId(L_OUTPUT3.getLevelUpId());
        lvm.setCustomerId(L_OUTPUT3.getCustomerId());
        lvm.setPoints(L_OUTPUT3.getPoints());
        lvm.setMemberDate(L_OUTPUT3.getMemberDate());

        sl.updateLevelUp(lvm);

        fromService = sl.getLevelUp(lvm.getLevelUpId());

        assertEquals(lvm, fromService);
    }


    @Test(expected = NullPointerException.class)
    public void deleteLevelUp() {

        sl.deleteLevelUp(10);

        LevelUpViewModel lvm = sl.getLevelUp(10);
    }

    @Test
    public void getAllLevelUps() {
        List<LevelUpViewModel> lvmList = sl.getAllLevelUps();
        assertEquals(lvmList.size(), 2);
    }

    @Test
    public void getLevelUpByCustomerId() {
        LevelUpViewModel levelUp = LVM_OUTPUT3;

        LevelUpViewModel fromService = sl.getLevelUpByCustomerId(3);

        assertEquals(levelUp, fromService);
    }
}