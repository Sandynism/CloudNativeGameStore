package com.cognizant.adminapi.service;

import com.cognizant.adminapi.exception.NoSuchCustomerException;
import com.cognizant.adminapi.exception.NoSuchLevelUpException;
import com.cognizant.adminapi.exception.NoSuchProductException;
import com.cognizant.adminapi.exception.NotFoundException;
import com.cognizant.adminapi.model.*;
import com.cognizant.adminapi.util.feign.*;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
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
    public ServiceLayer(CustomerClient customerClient, ProductClient productClient, LevelUpClient levelUpClient) {
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.levelUpClient = levelUpClient;
//        this.inventoryClient = inventoryClient;
//        this.invoiceClient = invoiceClient;
    }

    //CUSTOMER SERVICE
    public CustomerViewModel createCustomer(CustomerViewModel cvm) {
        Customer customer = new Customer();
        customer.setFirstName(cvm.getFirstName());
        customer.setLastName(cvm.getLastName());
        customer.setStreet(cvm.getStreet());
        customer.setCity(cvm.getCity());
        customer.setZip(cvm.getZip());
        customer.setEmail(cvm.getEmail());
        customer.setPhone(cvm.getPhone());

        customer = customerClient.createCustomer(customer);

        return buildCustomerViewModel(customer);
    }

    public CustomerViewModel getCustomer(Integer customerId) throws NoSuchCustomerException {
        Customer customer;
        try {
            customer = customerClient.getCustomer(customerId);
        } catch (FeignException.NotFound fe) {
            System.out.println("Customer " + customerId + " could not be found. " + fe.getMessage());
            throw new NoSuchCustomerException(customerId);
        }

        return buildCustomerViewModel(customer);
    }

    public void updateCustomer(CustomerViewModel cvm) throws NoSuchCustomerException {
        try {
            int id = customerClient.getCustomer(cvm.getCustomerId()).getCustomerId();
        } catch (FeignException.NotFound fe) {
            System.out.println("Customer " + cvm.getCustomerId() + " could not be found. " + fe.getMessage());
            throw new NoSuchCustomerException(cvm.getCustomerId());
        }

        Customer customer = new Customer();
        customer.setCustomerId(cvm.getCustomerId());
        customer.setFirstName(cvm.getFirstName());
        customer.setLastName(cvm.getLastName());
        customer.setStreet(cvm.getStreet());
        customer.setCity(cvm.getCity());
        customer.setZip(cvm.getZip());
        customer.setEmail(cvm.getEmail());
        customer.setPhone(cvm.getPhone());

        customerClient.updateCustomer(customer, cvm.getCustomerId());
    }

    public void deleteCustomer(Integer customerId) throws NoSuchCustomerException {

        try {
            int id = customerClient.getCustomer(customerId).getCustomerId();
        } catch (FeignException.NotFound fe) {
            System.out.println("Customer " + customerId + " could not be found. " + fe.getMessage());
            throw new NoSuchCustomerException(customerId);
        }

        customerClient.deleteCustomer(customerId);
    }

    public List<CustomerViewModel> getAllCustomers() {
        List<Customer> customerList = customerClient.getAllCustomers();
        List<CustomerViewModel> cvmList = new ArrayList<>();

        for (Customer c : customerList) {
            cvmList.add(buildCustomerViewModel(c));
        }
        return cvmList;
    }

    private CustomerViewModel buildCustomerViewModel(Customer customer) {
        CustomerViewModel cvm = new CustomerViewModel();

        cvm.setCustomerId(customer.getCustomerId());
        cvm.setFirstName(customer.getFirstName());
        cvm.setLastName(customer.getLastName());
        cvm.setStreet(customer.getStreet());
        cvm.setCity(customer.getCity());
        cvm.setZip(customer.getZip());
        cvm.setEmail(customer.getEmail());
        cvm.setPhone(customer.getPhone());

        return cvm;
    }


    //PRODUCT SERVICE
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

        productClient.updateProduct(product, pvm.getProductId());
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



    //LEVEL UP SERVICE
    public LevelUpViewModel createLevelUp(LevelUpViewModel lvm) {

        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(lvm.getCustomerId());
        levelUp.setPoints(lvm.getPoints());
        levelUp.setMemberDate(lvm.getMemberDate());

        levelUp = levelUpClient.addLevelUp(levelUp);

        return buildLevelUpViewModel(levelUp);

    }

    public LevelUpViewModel getLevelUp(Integer levelUpId) throws NoSuchLevelUpException {
        LevelUp levelUp;
        try {
            levelUp = levelUpClient.getLevelUp(levelUpId);
        } catch (FeignException.NotFound fe) {
            System.out.println("Level up " + levelUpId + " could not be found. " + fe.getMessage());
            throw new NoSuchLevelUpException(levelUpId);
        }
        return buildLevelUpViewModel(levelUp);
    }

    public void updateLevelUp(LevelUpViewModel lvm) throws NoSuchLevelUpException {
        try {
            int id = levelUpClient.getLevelUp(lvm.getLevelUpId()).getLevelUpId();
        } catch (FeignException.NotFound fe) {
            System.out.println("Level up " + lvm.getLevelUpId() + " could not be found. " + fe.getMessage());
            throw new NoSuchLevelUpException(lvm.getLevelUpId());
        }

        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(lvm.getLevelUpId());
        levelUp.setCustomerId(lvm.getCustomerId());
        levelUp.setPoints(lvm.getPoints());
        levelUp.setMemberDate(lvm.getMemberDate());

        levelUpClient.updateLevelUp(levelUp, lvm.getLevelUpId());

    }

    public void deleteLevelUp(Integer levelUpId) throws NoSuchLevelUpException {
        try {
            int id = levelUpClient.getLevelUp(levelUpId).getLevelUpId();
        } catch (FeignException.NotFound fe) {
            System.out.println("Level up " + levelUpId + " could not be found. " + fe.getMessage());
            throw new NoSuchLevelUpException(levelUpId);
        }

        levelUpClient.deleteLevelUp(levelUpId);
    }


    public List<LevelUpViewModel> getAllLevelUps() {
        List<LevelUp> levelUpsList = levelUpClient.getAllLevelUps();
        List<LevelUpViewModel> lvmList = new ArrayList<>();

        for (LevelUp l : levelUpsList) {
            lvmList.add(buildLevelUpViewModel(l));
        }

        return lvmList;
    }

    public LevelUpViewModel getLevelUpByCustomerId(Integer customerId) {
        LevelUp levelUp = levelUpClient.getLevelUpByCustomerId(customerId);

        if (levelUp == null)
            throw new NotFoundException("There is no level up entry with customer id " + customerId);

        return buildLevelUpViewModel(levelUp);
    }


    private LevelUpViewModel buildLevelUpViewModel(LevelUp levelUp) {

        LevelUpViewModel levelUpViewModel = new LevelUpViewModel();
        levelUpViewModel.setLevelUpId(levelUp.getLevelUpId());
        levelUpViewModel.setCustomerId(levelUp.getCustomerId());
        levelUpViewModel.setPoints(levelUp.getPoints());
        levelUpViewModel.setMemberDate(levelUp.getMemberDate());

        return levelUpViewModel;
    }




}
