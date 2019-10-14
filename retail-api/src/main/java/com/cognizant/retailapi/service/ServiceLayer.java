package com.cognizant.retailapi.service;

import com.cognizant.retailapi.exception.NotFoundException;
import com.cognizant.retailapi.model.*;
import com.cognizant.retailapi.util.feign.*;
import com.cognizant.retailapi.util.feign.message.LevelUpEntry;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@Component
public class ServiceLayer {

    public static final String EXCHANGE = "levelUp-exchange";
    public static final String ROUTING_KEY = "levelUp.add.retail.service";

    private CustomerClient customerClient;
    private ProductClient productClient;
    private LevelUpClient levelUpClient;
    private InventoryClient inventoryClient;
    private InvoiceClient invoiceClient;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public ServiceLayer(CustomerClient customerClient, ProductClient productClient, LevelUpClient levelUpClient, InventoryClient inventoryClient, InvoiceClient invoiceClient, RabbitTemplate rabbitTemplate) {
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.levelUpClient = levelUpClient;
        this.inventoryClient = inventoryClient;
        this.invoiceClient = invoiceClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public OrderViewModel saveOrder(InvoiceViewModel invoiceViewModel) {

        BigDecimal total = BigDecimal.valueOf(0.00);

        OrderViewModel orderViewModel = new OrderViewModel();

        CustomerViewModel customerViewModel = customerClient.getCustomer(invoiceViewModel.getCustomerId());

        if (customerViewModel == null)
            throw new NotFoundException("Customer id does not exist, please add the customer first");

        else {


            for (InvoiceItemViewModel invoiceItemViewModel : invoiceViewModel.getInvoiceItems()) {


                InventoryViewModel inventoryViewModel = inventoryClient.getInventory(invoiceItemViewModel.getInventoryId());

                if (invoiceItemViewModel.getQuantity() <= inventoryViewModel.getQuantity()) {

                    ProductViewModel productViewModel = productClient.getProduct(inventoryViewModel.getProductId());

                    if (productViewModel == null) throw new NotFoundException("this product is not available");
                    else {

                        invoiceItemViewModel.setUnitPrice(productViewModel.getUnitCost());
                    }

                    inventoryViewModel.setQuantity(invoiceItemViewModel.getQuantity() - inventoryViewModel.getQuantity());


                } else
                    throw new NotFoundException(String.format("Sorry! we only have %s items of this product ", inventoryViewModel.getQuantity()));

                orderViewModel.setTotal((invoiceItemViewModel.getUnitPrice()).multiply(new BigDecimal(invoiceItemViewModel.getQuantity()).setScale(2, RoundingMode.HALF_UP)));

                total = total.add(orderViewModel.getTotal());

            }


            InvoiceViewModel invoiceViewModel1 = invoiceClient.createInvoice(invoiceViewModel);


            LevelUpViewModel levelUpViewModel = levelUpClient.getLevelUpByCustomerId(invoiceViewModel.getCustomerId());

            if (levelUpViewModel == null) {

                LevelUpEntry msg = new LevelUpEntry();
                msg.setCustomerId(invoiceViewModel.getCustomerId());
                msg.setMemberDate(invoiceViewModel.getPurchaseDate());
                msg.setPoints(0);

                rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, msg);

            }

            levelUpViewModel.setPoints((Integer.divideUnsigned(total.intValue(),50))*10);

            LevelUpEntry msg = new LevelUpEntry();
            msg.setLevelUpId(levelUpViewModel.getLevelUpId());
            msg.setPoints(levelUpViewModel.getPoints());

            rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, msg);

            orderViewModel.setInvoiceId(invoiceViewModel1.getInvoiceId());
            orderViewModel.setCustomerId(invoiceViewModel1.getCustomerId());
            orderViewModel.setPurchaseDate(invoiceViewModel1.getPurchaseDate());
            orderViewModel.setLevelUpPoints(levelUpViewModel.getPoints());
            orderViewModel.setTotal(total);

            orderViewModel.setInvoiceItems((invoiceViewModel1.getInvoiceItems()));


            return orderViewModel;


        }
    }


    public OrderViewModel findOrder(int invoiceId){

        final BigDecimal[] total = {BigDecimal.valueOf(0.00)};

        InvoiceViewModel invoiceViewModel = invoiceClient.getInvoice(invoiceId);

        OrderViewModel orderViewModel = new OrderViewModel();
        orderViewModel.setInvoiceId(invoiceViewModel.getInvoiceId());
        orderViewModel.setCustomerId(invoiceViewModel.getCustomerId());
        orderViewModel.setPurchaseDate(invoiceViewModel.getPurchaseDate());
        orderViewModel.setInvoiceItems(invoiceViewModel.getInvoiceItems());

        LevelUpViewModel levelUpViewModel = levelUpClient.getLevelUpByCustomerId(invoiceViewModel.getCustomerId());

        orderViewModel.setLevelUpPoints(levelUpViewModel.getPoints());
        orderViewModel.getInvoiceItems().stream().forEach(invoiceItemViewModel ->{

                orderViewModel.setTotal((invoiceItemViewModel.getUnitPrice()).multiply(new BigDecimal(invoiceItemViewModel.getQuantity()).setScale(2, RoundingMode.HALF_UP)));

                total[0] = total[0].add(orderViewModel.getTotal());
        });

        orderViewModel.setTotal(total[0]);

        return orderViewModel;

    }

    public List<OrderViewModel> findAllOrder(){

        List<InvoiceViewModel> invoiceViewModelList = invoiceClient.getAllInvoices();

        List<OrderViewModel> orderViewModels = new ArrayList<>();

        for(InvoiceViewModel i: invoiceViewModelList){

            orderViewModels.add(findOrder(i.getInvoiceId()));

        }

        return orderViewModels;
    }


    public List<OrderViewModel> findOrdersByCustomerId(int customerId){

        List<InvoiceViewModel> invoiceViewModelList = invoiceClient.getInvoiceByCustomerId(customerId);

        List<OrderViewModel> orderViewModels = new ArrayList<>();

        for(InvoiceViewModel i: invoiceViewModelList){

            orderViewModels.add(findOrder(i.getInvoiceId()));

        }

        return orderViewModels;

    }

    public InventoryViewModel saveInventory (InventoryViewModel inventoryViewModel){

        InventoryViewModel inventoryViewModel1 = inventoryClient.createInventory(inventoryViewModel);


        return inventoryViewModel1;
    }

    public List<ProductViewModel> getProductsInInventory (){

        List<ProductViewModel> productViewModels = new ArrayList<>();

        List<InventoryViewModel> inventoryViewModels = inventoryClient.getAllInventory();

        inventoryViewModels.forEach(inventoryViewModel -> {

            ProductViewModel productViewModel = getProduct(inventoryViewModel.getProductId());
            productViewModels.add(productViewModel);

        });

        return productViewModels;

    }

    public ProductViewModel saveProduct(ProductViewModel productViewModel){

        ProductViewModel productViewModel1 = productClient.createProduct(productViewModel);

        return productViewModel1;
    }


    public ProductViewModel getProduct (int productId){

     return productClient.getProduct(productId);

    }

    public List<ProductViewModel> getProductByInvoiceId (int invoiceId){


        List<ProductViewModel> productViewModels = new ArrayList<>();


        InvoiceViewModel invoiceViewModel = invoiceClient.getInvoice(invoiceId);

        invoiceViewModel.getInvoiceItems().forEach(invoiceItemViewModel -> {

            InventoryViewModel inventoryViewModel =  inventoryClient.getInventory(invoiceItemViewModel.getInventoryId());

            ProductViewModel productViewModel = productClient.getProduct(inventoryViewModel.getProductId());

            productViewModels.add(productViewModel);

        });


        return productViewModels;
    }

    public LevelUpViewModel saveLevelUp (LevelUpViewModel levelUpViewModel){

        LevelUpViewModel levelUpViewModel1 = levelUpClient.createLevelUp(levelUpViewModel);

        return levelUpViewModel1;
    }

    public int getLevelUpPointsByCustomerId(int customerId){

        LevelUpViewModel levelUpViewModel = levelUpClient.getLevelUpByCustomerId(customerId);

        return levelUpViewModel.getPoints();
    }

    public List<ProductViewModel> getAllProducts(){

        return productClient.getAllProducts();

    }

    public void updateProduct(ProductViewModel productViewModel, int productId){

        productClient.updateProduct(productViewModel, productId);


    }

    public void deleteProduct(int productId){

        productClient.deleteProduct(productId);

    }

}
