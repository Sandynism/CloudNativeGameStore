package com.cognizant.adminapi.service;

import com.cognizant.adminapi.exception.NoSuchCustomerException;
import com.cognizant.adminapi.model.*;
import com.cognizant.adminapi.util.feign.*;
import net.bytebuddy.build.ToStringPlugin;
import org.junit.Before;
import org.junit.Test;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

public class ServiceLayerTest {

    private static final Integer CUSTOMER_ID = 1;
    private static final Customer CUSTOMER_INPUT1 = new Customer("Armin", "Van Buuren", "Trance", "Leiden", "2300-2334", "StateOfTrance@gmail.com", "123456789");
    private static final Customer CUSTOMER_OUTPUT1 = new Customer(1, "Armin", "Van Buuren", "Trance", "Leiden", "2300-2334", "StateOfTrance@gmail.com", "123456789");
    private static final Customer CUSTOMER_OUTPUT2 = new Customer(2, "Jai", "Wolf", "Broadway", "New York", "11001", "jaiwolf@gmail.com", "456789123");
    private static final Customer CUSTOMER_OUTPUT3 = new Customer(3, "Nick", "Miller", "10th St", "Denver", "54321", "illenium@gmail.com", "987654321");
    private static final CustomerViewModel CVM_INPUT1 = new CustomerViewModel("Armin", "Van Buuren", "Trance", "Leiden", "2300-2334", "StateOfTrance@gmail.com", "123456789");

    private static final Integer PRODUCT_ID = 1;
    private static final Product PRODUCT_INPUT1 = new Product("Macbook Pro", "Greatest laptop ever", new BigDecimal("2900.00"), new BigDecimal("2400.00"));
    private static final Product PRODUCT_OUTPUT1 = new Product(1, "Macbook Pro", "Greatest laptop ever", new BigDecimal("2900.00"), new BigDecimal("2400.00"));
    private static final Product PRODUCT_OUTPUT2 = new Product(2, "iPhone", "X Max Model", new BigDecimal("1200.00"), new BigDecimal("900.00"));
    private static final Product PRODUCT_OUTPUT3 = new Product(3, "Cardigan", "Cotton Sweater", new BigDecimal("55.00"), new BigDecimal("25.00"));
    private static final ProductViewModel PVM_INPUT1 = new ProductViewModel("Macbook Pro", "Greatest laptop ever", new BigDecimal("2900.00"), new BigDecimal("2400.00"));

    private static final Integer LEVELUP_ID = 1;
    private static final LevelUp LEVELUP_INPUT1 = new LevelUp(1, 100, LocalDate.of(2012, 1, 1));
    private static final LevelUp LEVELUP_OUTPUT1 = new LevelUp(1, 1, 100, LocalDate.of(2012, 1, 1));
    private static final LevelUp LEVELUP_OUTPUT2 = new LevelUp(2, 2, 100, LocalDate.of(2019, 12, 12));
    private static final LevelUp LEVELUP_OUTPUT3 = new LevelUp(3, 3, 100, LocalDate.of(2017, 5, 10));
    private static final LevelUpViewModel LVM_INPUT1 = new LevelUpViewModel(1, 100, LocalDate.of(2012, 1, 1));
    private static final LevelUpViewModel LVM_OUTPUT3 = new LevelUpViewModel(3, 3, 100, LocalDate.of(2017, 5, 10));

    private static final Integer INVENTORY_ID = 1;
    private static final Inventory INVENTORY_INPUT1 = new Inventory(1, 20);
    private static final Inventory INVENTORY_OUTPUT1 = new Inventory(1, 1, 20);
    private static final Inventory INVENTORY_OUTPUT2 = new Inventory(2, 2, 40);
    private static final Inventory INVENTORY_OUTPUT3 = new Inventory(3, 3, 60);
    private static final InventoryViewModel INVENTORYVM_INPUT1 = new InventoryViewModel(1, 20);
    private static final InventoryViewModel INVENTORYVM_OUTPUT3 = new InventoryViewModel(3, 3, 60);

    private static final Integer INVOICE_ID = 1;
    private static final Invoice INVOICE_INPUT1 = new Invoice(1, LocalDate.of(2018, 1, 1));
    private static final Invoice INVOICE_OUTPUT1 = new Invoice(1, 1, LocalDate.of(2018, 1, 1));
    private static final Invoice INVOICE_OUTPUT2 = new Invoice(2, 2, LocalDate.of(2018, 2, 2));
    private static final Invoice INVOICE_OUTPUT3 = new Invoice(3, 3, LocalDate.of(2018, 3, 3));
    private static final InvoiceViewModel INVOICEVM_INPUT1 = new InvoiceViewModel(1, LocalDate.of(2018, 1, 1));
    private static final InvoiceViewModel INVOICEVM_OUTPUT3 = new InvoiceViewModel(3, 3, LocalDate.of(2018, 3, 3));

    private CustomerClient customerClient;
    private ProductClient productClient;
    private LevelUpClient levelUpClient;
    private InventoryClient inventoryClient;
    private InvoiceClient invoiceClient;
    private ServiceLayer sl;

    @Before
    public void setUp() throws Exception {
        setUpCustomerClientMocks();
        setUpProductClientMocks();
        setUpLevelUpClientMocks();
        setUpInventoryClientMocks();
        setUpInvoiceClientMocks();

        sl = new ServiceLayer(customerClient, productClient, levelUpClient, inventoryClient, invoiceClient);
    }

    private void setUpCustomerClientMocks() {
        customerClient = mock(CustomerClient.class);

        //add
        doReturn(CUSTOMER_OUTPUT1).when(customerClient).createCustomer(CUSTOMER_INPUT1);

        //get by id
        doReturn(CUSTOMER_OUTPUT1).when(customerClient).getCustomer(CUSTOMER_ID);

        //get all
        List<Customer> cList = new ArrayList<>();
        cList.add(CUSTOMER_OUTPUT1);
        cList.add(CUSTOMER_OUTPUT2);
        doReturn(cList).when(customerClient).getAllCustomers();

        //update
        doNothing().when(customerClient).updateCustomer(CUSTOMER_OUTPUT3, 3);
        doReturn(CUSTOMER_OUTPUT3).when(customerClient).getCustomer(3);

        //delete
        doNothing().when(customerClient).deleteCustomer(10);
        doReturn(null).when(customerClient).getCustomer(10);
    }

    private void setUpProductClientMocks() {
        productClient = mock(ProductClient.class);

        //add
        doReturn(PRODUCT_OUTPUT1).when(productClient).createProduct(PRODUCT_INPUT1);

        //get by id
        doReturn(PRODUCT_OUTPUT1).when(productClient).getProduct(PRODUCT_ID);

        //get all
        List<Product> pList = new ArrayList<>();
        pList.add(PRODUCT_OUTPUT1);
        pList.add(PRODUCT_OUTPUT2);
        doReturn(pList).when(productClient).getAllProducts();

        //update
        doNothing().when(productClient).updateProduct(PRODUCT_OUTPUT3, 3);
        doReturn(PRODUCT_OUTPUT3).when(productClient).getProduct(3);

        //delete
        doNothing().when(productClient).deleteProduct(10);
        doReturn(null).when(productClient).getProduct(10);
    }

    private void setUpLevelUpClientMocks() {
        levelUpClient = mock(LevelUpClient.class);

        //add
        doReturn(LEVELUP_OUTPUT1).when(levelUpClient).createLevelUp(LEVELUP_INPUT1);

        //get by id
        doReturn(LEVELUP_OUTPUT1).when(levelUpClient).getLevelUp(LEVELUP_ID);

        //get all
        List<LevelUp> lList = new ArrayList<>();
        lList.add(LEVELUP_OUTPUT1);
        lList.add(LEVELUP_OUTPUT2);
        doReturn(lList).when(levelUpClient).getAllLevelUps();

        //update
        doNothing().when(levelUpClient).updateLevelUp(LEVELUP_OUTPUT3, 3);
        doReturn(LEVELUP_OUTPUT3).when(levelUpClient).getLevelUp(3);

        //delete
        doNothing().when(levelUpClient).deleteLevelUp(10);
        doReturn(null).when(levelUpClient).getLevelUp(10);

        //get by customer id
        doReturn(LEVELUP_OUTPUT3).when(levelUpClient).getLevelUpByCustomerId(3);
    }

    private void setUpInventoryClientMocks() {
        inventoryClient = mock(InventoryClient.class);

        //add
        doReturn(INVENTORY_OUTPUT1).when(inventoryClient).createInventory(INVENTORY_INPUT1);

        //get by id
        doReturn(INVENTORY_OUTPUT1).when(inventoryClient).getInventory(INVENTORY_ID);

        //get all
        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(INVENTORY_OUTPUT1);
        inventoryList.add(INVENTORY_OUTPUT2);
        doReturn(inventoryList).when(inventoryClient).getAllInventory();

        //update
        doNothing().when(inventoryClient).updateInventory(INVENTORY_OUTPUT3, 3);
        doReturn(INVENTORY_OUTPUT3).when(inventoryClient).getInventory(3);

        //delete
        doNothing().when(inventoryClient).deleteInventory(10);
        doReturn(null).when(inventoryClient).getInventory(10);

        //get by product id
        List<Inventory> findByProductList = new ArrayList<>();
        findByProductList.add(INVENTORY_OUTPUT3);
        doReturn(findByProductList).when(inventoryClient).getAllInventoryByProductId(3);
    }

    private void setUpInvoiceClientMocks() {
        invoiceClient = mock(InvoiceClient.class);

        //get by id
        doReturn(INVOICE_OUTPUT1).when(invoiceClient).getInvoice(INVOICE_ID);

        //get all
        List<Invoice> iList = new ArrayList<>();
        iList.add(INVOICE_OUTPUT1);
        iList.add(INVOICE_OUTPUT2);
        doReturn(iList).when(invoiceClient).getAllInvoices();

        //get all by customer id
        List<Invoice> customerList = new ArrayList<>();
        customerList.add(INVOICE_OUTPUT3);
        doReturn(customerList).when(invoiceClient).getInvoicesByCustomerId(3);
    }


    //CUSTOMER SERVICE
    @Test
    public void createGetUpdateCustomer() {
        CustomerViewModel cvm = sl.createCustomer(CVM_INPUT1);

        CustomerViewModel fromService = sl.getCustomer(cvm.getCustomerId());

        assertEquals(cvm, fromService);

        Customer customer = CUSTOMER_OUTPUT3;

        cvm = new CustomerViewModel();
        cvm.setCustomerId(CUSTOMER_OUTPUT3.getCustomerId());
        cvm.setFirstName(CUSTOMER_OUTPUT3.getFirstName());
        cvm.setLastName(CUSTOMER_OUTPUT3.getLastName());
        cvm.setStreet(CUSTOMER_OUTPUT3.getStreet());
        cvm.setCity(CUSTOMER_OUTPUT3.getCity());
        cvm.setZip(CUSTOMER_OUTPUT3.getZip());
        cvm.setEmail(CUSTOMER_OUTPUT3.getEmail());
        cvm.setPhone(CUSTOMER_OUTPUT3.getPhone());

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


    //PRODUCT SERVICE
    @Test
    public void createGetUpdateProduct() {
        ProductViewModel pvm = sl.createProduct(PVM_INPUT1);

        ProductViewModel fromService = sl.getProduct(pvm.getProductId());

        assertEquals(pvm, fromService);

        Product product = PRODUCT_OUTPUT3;

        pvm = new ProductViewModel();
        pvm.setProductId(PRODUCT_OUTPUT3.getProductId());
        pvm.setProductName(PRODUCT_OUTPUT3.getProductName());
        pvm.setProductDescription(PRODUCT_OUTPUT3.getProductDescription());
        pvm.setListPrice(PRODUCT_OUTPUT3.getListPrice());
        pvm.setUnitCost(PRODUCT_OUTPUT3.getUnitCost());

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


    //LEVEL UP SERVICE
    @Test
    public void createGetUpdateLevelUp() {
        LevelUpViewModel lvm = sl.createLevelUp(LVM_INPUT1);

        LevelUpViewModel fromService = sl.getLevelUp(lvm.getLevelUpId());

        assertEquals(lvm, fromService);

        LevelUp levelUp = LEVELUP_OUTPUT3;

        lvm = new LevelUpViewModel();
        lvm.setLevelUpId(LEVELUP_OUTPUT3.getLevelUpId());
        lvm.setCustomerId(LEVELUP_OUTPUT3.getCustomerId());
        lvm.setPoints(LEVELUP_OUTPUT3.getPoints());
        lvm.setMemberDate(LEVELUP_OUTPUT3.getMemberDate());

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


    //INVENTORY SERVICE
    @Test
    public void createGetUpdateInventory() {
        InventoryViewModel ivm = sl.createInventory(INVENTORYVM_INPUT1);

        InventoryViewModel fromService = sl.getInventory(ivm.getInventoryId());

        assertEquals(ivm, fromService);

        Inventory inventory = INVENTORY_OUTPUT3;

        ivm = new InventoryViewModel();
        ivm.setInventoryId(inventory.getInventoryId());
        ivm.setProductId(inventory.getProductId());
        ivm.setQuantity(inventory.getQuantity());

        sl.updateInventory(ivm);

        fromService = sl.getInventory(ivm.getInventoryId());

        assertEquals(ivm, fromService);
    }


    @Test(expected = NullPointerException.class)
    public void deleteInventory() {
        sl.deleteInventory(10);

        InventoryViewModel ivm = sl.getInventory(10);
    }

    @Test
    public void getAllInventory() {
        List<InventoryViewModel> ivmList = sl.getAllInventory();
        assertEquals(ivmList.size(), 2);
    }

    @Test
    public void getAllInventoryByProductId() {
        List<InventoryViewModel> ivmList = sl.getAllInventoryByProductId(3);
        assertEquals(ivmList.size(), 1);
    }


    //INVOICE SERVICE
    @Test
    public void getInvoice() {

    }

    @Test
    public void getAllInvoices() {

    }

    @Test
    public void getAllInvoicesByCustomerId() {

    }


}