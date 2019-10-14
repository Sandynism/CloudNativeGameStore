package com.cognizant.retailapi.service;

import com.cognizant.retailapi.model.*;
import com.cognizant.retailapi.util.feign.*;
import com.cognizant.retailapi.util.feign.message.LevelUpEntry;
import org.apache.catalina.servlets.DefaultServlet;
import org.junit.Before;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    public static final String EXCHANGE = "levelUp-exchange";
    public static final String ROUTING_KEY_ADD = "levelUp.add.retail.service";
    public static final String ROUTING_KEY_UPDATE = "levelUp.update.retail.service";

    private CustomerClient customerClient;
    private ProductClient productClient;
    private LevelUpClient levelUpClient;
    private InventoryClient inventoryClient;
    private InvoiceClient invoiceClient;
    private RabbitTemplate rabbitTemplate;
    private ServiceLayer serviceLayer;

    @Before
    public void setUp() throws Exception {

        setUpCustomerClientMock();
        setUpProductClientMock();
        setUpLevelUpClientMock();
        setUpInventoryClientMock();
        setUpInvoiceClientMock();
        setUpRabbitTemplateMock();

        serviceLayer = new ServiceLayer(customerClient, productClient, levelUpClient, inventoryClient, invoiceClient, rabbitTemplate);
    }


    private void setUpCustomerClientMock(){

        customerClient = mock(CustomerClient.class);

        CustomerViewModel customerViewModel = new CustomerViewModel();
        customerViewModel.setCustomerId(2);
        customerViewModel.setFirstName("Marc");
        customerViewModel.setLastName("Cuban");
        customerViewModel.setStreet("5th Ave");
        customerViewModel.setCity("New York");
        customerViewModel.setZip("12345");
        customerViewModel.setEmail("marc@marc.com");
        customerViewModel.setPhone("123456789");

        doReturn(customerViewModel).when(customerClient).getCustomer(2);


    }

    private void setUpProductClientMock(){

        productClient = mock(ProductClient.class);

        ProductViewModel productViewModel = new ProductViewModel();
        productViewModel.setProductId(4);
        productViewModel.setProductName("Shirt");
        productViewModel.setProductDescription("White Polo Shirt");
        productViewModel.setListPrice(new BigDecimal("12.12"));
        productViewModel.setUnitCost(new BigDecimal("11.01"));

        ProductViewModel productViewModel1 = new ProductViewModel();
        productViewModel1.setProductId(6);
        productViewModel1.setProductName("T-shirt");
        productViewModel1.setProductDescription("Blue T-shirt");
        productViewModel1.setListPrice(new BigDecimal("15.01"));
        productViewModel1.setUnitCost(new BigDecimal("9.50"));

        List<ProductViewModel> productViewModels = new ArrayList<>();
        productViewModels.add(productViewModel);

        doReturn(productViewModel).when(productClient).getProduct(4);
        doReturn(productViewModel1).when(productClient).getProduct(6);

        doReturn(productViewModels).when(productClient).getAllProducts();

        ProductViewModel productViewModel2 = new ProductViewModel();
        productViewModel2.setProductId(10);
        productViewModel2.setProductName("T-shirt");
        productViewModel2.setProductDescription("Blue T-shirt");
        productViewModel2.setListPrice(new BigDecimal("12.01"));
        productViewModel2.setUnitCost(new BigDecimal("2.50"));

        doNothing().when(productClient).updateProduct(productViewModel2, productViewModel2.getProductId());
        doReturn(productViewModel2).when(productClient).getProduct(productViewModel2.getProductId());

        doNothing().when(productClient).deleteProduct(50);
        doReturn(null).when(productClient).getProduct(50);

        ProductViewModel productViewModel3 = new ProductViewModel();
        productViewModel3.setProductName("Shirt");
        productViewModel3.setProductDescription("White Polo Shirt");
        productViewModel3.setListPrice(new BigDecimal("12.12"));
        productViewModel3.setUnitCost(new BigDecimal("11.01"));

        doReturn(productViewModel).when(productClient).createProduct(productViewModel3);
    }


    private void setUpLevelUpClientMock(){

        levelUpClient = mock(LevelUpClient.class);

        LevelUpViewModel levelUpViewModel = new LevelUpViewModel();
        levelUpViewModel.setLevelUpId(10);
        levelUpViewModel.setCustomerId(2);
        levelUpViewModel.setMemberDate(LocalDate.of(2012, 12, 12));
        levelUpViewModel.setPoints(50);

        LevelUpViewModel levelUpViewModel1 = new LevelUpViewModel();
        levelUpViewModel1.setCustomerId(2);
        levelUpViewModel1.setMemberDate(LocalDate.of(2012, 12, 12));
        levelUpViewModel1.setPoints(50);

        doReturn(levelUpViewModel).when(levelUpClient).getLevelUpByCustomerId(2);

        doReturn(levelUpViewModel).when(levelUpClient).createLevelUp(levelUpViewModel1);


    }

    private void setUpInventoryClientMock(){

        inventoryClient = mock(InventoryClient.class);

        InventoryViewModel inventoryViewModel = new InventoryViewModel();
        inventoryViewModel.setInventoryId(8);
        inventoryViewModel.setProductId(4);
        inventoryViewModel.setQuantity(20);

        InventoryViewModel inventoryViewModel1 = new InventoryViewModel();
        inventoryViewModel1.setInventoryId(12);
        inventoryViewModel1.setProductId(6);
        inventoryViewModel1.setQuantity(20);

        List<InventoryViewModel> inventoryViewModels = new ArrayList<>();
        inventoryViewModels.add(inventoryViewModel);

        doReturn(inventoryViewModel).when(inventoryClient).getInventory(8);
        doReturn(inventoryViewModel1).when(inventoryClient).getInventory(12);

        doReturn(inventoryViewModels).when(inventoryClient).getAllInventory();
    }

    private void setUpInvoiceClientMock(){

        invoiceClient = mock(InvoiceClient.class);

        InvoiceItemViewModel invoiceItemViewModel = new InvoiceItemViewModel();
        invoiceItemViewModel.setInvoiceItemId(20);
        invoiceItemViewModel.setInvoiceId(1);
        invoiceItemViewModel.setInventoryId(8);
        invoiceItemViewModel.setQuantity(9);
        invoiceItemViewModel.setUnitPrice(new BigDecimal("11.01"));

        InvoiceItemViewModel invoiceItemViewModel1 = new InvoiceItemViewModel();
        invoiceItemViewModel1.setInvoiceItemId(21);
        invoiceItemViewModel1.setInvoiceId(1);
        invoiceItemViewModel1.setInventoryId(12);
        invoiceItemViewModel1.setQuantity(7);
        invoiceItemViewModel1.setUnitPrice(new BigDecimal("9.50"));

        List<InvoiceItemViewModel> invoiceItemViewModels = new ArrayList<>();
        invoiceItemViewModels.add(invoiceItemViewModel);
        invoiceItemViewModels.add(invoiceItemViewModel1);

        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setInvoiceId(1);
        invoiceViewModel.setCustomerId(2);
        invoiceViewModel.setPurchaseDate(LocalDate.of(2012, 12, 12));
        invoiceViewModel.setInvoiceItems(invoiceItemViewModels);


        InvoiceItemViewModel invoiceItemViewModel2 = new InvoiceItemViewModel();
        invoiceItemViewModel2.setInvoiceId(1);
        invoiceItemViewModel2.setInventoryId(8);
        invoiceItemViewModel2.setQuantity(9);
        invoiceItemViewModel2.setUnitPrice(new BigDecimal("11.01"));

        InvoiceItemViewModel invoiceItemViewModel3 = new InvoiceItemViewModel();
        invoiceItemViewModel3.setInvoiceId(1);
        invoiceItemViewModel3.setInventoryId(12);
        invoiceItemViewModel3.setQuantity(7);
        invoiceItemViewModel3.setUnitPrice(new BigDecimal("9.50"));

        List<InvoiceItemViewModel> invoiceItemViewModels1 = new ArrayList<>();
        invoiceItemViewModels1.add(invoiceItemViewModel2);
        invoiceItemViewModels1.add(invoiceItemViewModel3);

        InvoiceViewModel invoiceViewModel1 = new InvoiceViewModel();
        invoiceViewModel1.setCustomerId(2);
        invoiceViewModel1.setPurchaseDate(LocalDate.of(2012, 12, 12));
        invoiceViewModel1.setInvoiceItems(invoiceItemViewModels1);

        doReturn(invoiceViewModel).when(invoiceClient).createInvoice(invoiceViewModel1);
        doReturn(invoiceViewModel).when(invoiceClient).getInvoice(1);

        List<InvoiceViewModel> invoiceViewModelList = new ArrayList<>();
        invoiceViewModelList.add(invoiceViewModel);


        doReturn(invoiceViewModelList).when(invoiceClient).getInvoiceByCustomerId(invoiceViewModel.getCustomerId());


    }

    private void setUpRabbitTemplateMock(){

        rabbitTemplate = mock(RabbitTemplate.class);

        LevelUpEntry msg = new LevelUpEntry();
        msg.setPoints(20);

        doNothing().when(rabbitTemplate).convertAndSend(EXCHANGE,ROUTING_KEY_UPDATE,msg);


    }


    @Test
    public void saveOrder() {

        InvoiceItemViewModel invoiceItemViewModel = new InvoiceItemViewModel();
        invoiceItemViewModel.setInvoiceId(1);
        invoiceItemViewModel.setInventoryId(8);
        invoiceItemViewModel.setQuantity(9);
        invoiceItemViewModel.setUnitPrice(new BigDecimal("11.01"));

        InvoiceItemViewModel invoiceItemViewModel1 = new InvoiceItemViewModel();
        invoiceItemViewModel1.setInvoiceId(1);
        invoiceItemViewModel1.setInventoryId(12);
        invoiceItemViewModel1.setQuantity(7);
        invoiceItemViewModel1.setUnitPrice(new BigDecimal("9.50"));

        List<InvoiceItemViewModel> invoiceItemViewModels = new ArrayList<>();
        invoiceItemViewModels.add(invoiceItemViewModel);
        invoiceItemViewModels.add(invoiceItemViewModel1);

        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setCustomerId(2);
        invoiceViewModel.setPurchaseDate(LocalDate.of(2012, 12, 12));
        invoiceViewModel.setInvoiceItems(invoiceItemViewModels);

        OrderViewModel orderViewModel = serviceLayer.saveOrder(invoiceViewModel);

        OrderViewModel fromService = serviceLayer.findOrder(orderViewModel.getInvoiceId());

        assertEquals(orderViewModel, fromService);

    }


    @Test
    public void findAllOrder() {
    }

    @Test
    public void findOrdersByCustomerId() {

        InvoiceItemViewModel invoiceItemViewModel = new InvoiceItemViewModel();
        invoiceItemViewModel.setInvoiceId(1);
        invoiceItemViewModel.setInventoryId(8);
        invoiceItemViewModel.setQuantity(9);
        invoiceItemViewModel.setUnitPrice(new BigDecimal("11.01"));

        InvoiceItemViewModel invoiceItemViewModel1 = new InvoiceItemViewModel();
        invoiceItemViewModel1.setInvoiceId(1);
        invoiceItemViewModel1.setInventoryId(12);
        invoiceItemViewModel1.setQuantity(7);
        invoiceItemViewModel1.setUnitPrice(new BigDecimal("9.50"));

        List<InvoiceItemViewModel> invoiceItemViewModels = new ArrayList<>();
        invoiceItemViewModels.add(invoiceItemViewModel);
        invoiceItemViewModels.add(invoiceItemViewModel1);

        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setCustomerId(2);
        invoiceViewModel.setPurchaseDate(LocalDate.of(2012, 12, 12));
        invoiceViewModel.setInvoiceItems(invoiceItemViewModels);

        OrderViewModel orderViewModel = serviceLayer.saveOrder(invoiceViewModel);

        List<OrderViewModel> fromService = serviceLayer.findOrdersByCustomerId(invoiceViewModel.getCustomerId());

        assertEquals(1, fromService.size());

    }

    @Test
    public void getProductsInInventory() {

        InventoryViewModel inventoryViewModel = new InventoryViewModel();
        inventoryViewModel.setInventoryId(8);
        inventoryViewModel.setProductId(4);
        inventoryViewModel.setQuantity(20);

        inventoryViewModel = serviceLayer.saveInventory(inventoryViewModel);

        ProductViewModel productViewModel = new ProductViewModel();
        productViewModel.setProductId(4);
        productViewModel.setProductName("Shirt");
        productViewModel.setProductDescription("White Polo Shirt");
        productViewModel.setListPrice(new BigDecimal("12.12"));
        productViewModel.setUnitCost(new BigDecimal("11.01"));

        productViewModel = serviceLayer.saveProduct(productViewModel);

        List<ProductViewModel> productViewModels = serviceLayer.getProductsInInventory();

        assertEquals(1, productViewModels.size());
    }

    @Test
    public void addGetProduct() {

        ProductViewModel productViewModel = new ProductViewModel();
        productViewModel.setProductName("Shirt");
        productViewModel.setProductDescription("White Polo Shirt");
        productViewModel.setListPrice(new BigDecimal("12.12"));
        productViewModel.setUnitCost(new BigDecimal("11.01"));

        productViewModel = serviceLayer.saveProduct(productViewModel);

        ProductViewModel fromService = serviceLayer.getProduct(productViewModel.getProductId());

        assertEquals(productViewModel, fromService);

    }

    @Test
    public void productByInvoiceId() {

        InvoiceItemViewModel invoiceItemViewModel = new InvoiceItemViewModel();
        invoiceItemViewModel.setInvoiceId(1);
        invoiceItemViewModel.setInventoryId(8);
        invoiceItemViewModel.setQuantity(9);
        invoiceItemViewModel.setUnitPrice(new BigDecimal("11.01"));

        InvoiceItemViewModel invoiceItemViewModel1 = new InvoiceItemViewModel();
        invoiceItemViewModel1.setInvoiceId(1);
        invoiceItemViewModel1.setInventoryId(12);
        invoiceItemViewModel1.setQuantity(7);
        invoiceItemViewModel1.setUnitPrice(new BigDecimal("9.50"));

        List<InvoiceItemViewModel> invoiceItemViewModels = new ArrayList<>();
        invoiceItemViewModels.add(invoiceItemViewModel);
        invoiceItemViewModels.add(invoiceItemViewModel1);

        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setCustomerId(2);
        invoiceViewModel.setPurchaseDate(LocalDate.of(2012, 12, 12));
        invoiceViewModel.setInvoiceItems(invoiceItemViewModels);

        OrderViewModel orderViewModel = serviceLayer.saveOrder(invoiceViewModel);

//        InventoryViewModel inventoryViewModel = serviceLayer.getProductsInInventory(invoiceViewModel.get)
//
//        List<ProductViewModel> productViewModels = serviceLayer.getProductsInInventory(invoiceViewModel.get)





    }

    @Test
    public void getLevelUpByCustomerId() {

        LevelUpViewModel levelUpViewModel = new LevelUpViewModel();
        levelUpViewModel.setCustomerId(2);
        levelUpViewModel.setMemberDate(LocalDate.of(2012, 12, 12));
        levelUpViewModel.setPoints(50);

        levelUpViewModel = serviceLayer.saveLevelUp(levelUpViewModel);

        Integer fromService = serviceLayer.getLevelUpPointsByCustomerId(levelUpViewModel.getCustomerId());

        assertEquals(levelUpViewModel.getPoints(), fromService);

    }

    @Test
    public void getAllProducts() {

        ProductViewModel productViewModel = new ProductViewModel();
        productViewModel.setProductId(4);
        productViewModel.setProductName("Shirt");
        productViewModel.setProductDescription("White Polo Shirt");
        productViewModel.setListPrice(new BigDecimal("12.12"));
        productViewModel.setUnitCost(new BigDecimal("11.01"));

        productViewModel = serviceLayer.saveProduct(productViewModel);

        ProductViewModel productViewModel1 = new ProductViewModel();
        productViewModel1.setProductId(6);
        productViewModel1.setProductName("T-shirt");
        productViewModel1.setProductDescription("Blue T-shirt");
        productViewModel1.setListPrice(new BigDecimal("15.01"));
        productViewModel1.setUnitCost(new BigDecimal("9.50"));

        productViewModel1 = serviceLayer.saveProduct(productViewModel1);

        List<ProductViewModel> productViewModels = serviceLayer.getAllProducts();

        assertEquals(2, productViewModels.size());

    }

    @Test
    public void updateProduct() {

        ProductViewModel productViewModel2 = new ProductViewModel();
        productViewModel2.setProductId(10);
        productViewModel2.setProductName("T-shirt");
        productViewModel2.setProductDescription("Blue T-shirt");
        productViewModel2.setListPrice(new BigDecimal("12.01"));
        productViewModel2.setUnitCost(new BigDecimal("2.50"));

        serviceLayer.updateProduct(productViewModel2, productViewModel2.getProductId());

        ProductViewModel fromService = serviceLayer.getProduct(productViewModel2.getProductId());

        assertEquals(productViewModel2, fromService);
    }

    @Test
    public void deleteProduct() {

        serviceLayer.deleteProduct(50);

        assertNull(serviceLayer.getProduct(50));
    }
}