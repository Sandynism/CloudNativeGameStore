package com.cognizant.retailapi.controller;

import com.cognizant.retailapi.exception.NotFoundException;
import com.cognizant.retailapi.model.InvoiceViewModel;
import com.cognizant.retailapi.model.OrderViewModel;
import com.cognizant.retailapi.model.ProductViewModel;
import com.cognizant.retailapi.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CacheConfig
public class OrderController {

    @Autowired
    ServiceLayer serviceLayer;

    @CachePut(cacheNames = {"invoices"}, key = "#result.getInvoiceId()")
    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    public OrderViewModel submitInvoice(@RequestBody InvoiceViewModel invoiceViewModel) {

        return serviceLayer.saveOrder(invoiceViewModel);
    }

    @Cacheable(cacheNames = {"invoices"})
    @RequestMapping(value = "/invoices/{invoiceId}", method = RequestMethod.GET)
    public OrderViewModel getInvoiceById(@PathVariable int invoiceId) {

        OrderViewModel orderViewModel = serviceLayer.findOrder(invoiceId);

        if (orderViewModel == null)

            throw new NotFoundException("Sorry! we don't have this invoice id no. " + invoiceId);

        return orderViewModel;
    }

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<OrderViewModel> getAllInvoices() {


        List<OrderViewModel> orderViewModels = serviceLayer.findAllOrder();

        if (orderViewModels != null && orderViewModels.size() == 0)
            throw new NotFoundException("we don't have any invoices");

        return orderViewModels;
    }

    @RequestMapping(value = "/invoices/customer/{customerId}", method = RequestMethod.GET)
    public List<OrderViewModel> getInvoicesByCustomerId(@PathVariable int customerId) {

        List<OrderViewModel> orderViewModels = serviceLayer.findOrdersByCustomerId(customerId);

        if (orderViewModels != null && orderViewModels.size() == 0)
            throw new NotFoundException("we don't have any invoices for this customer id");

        return orderViewModels;
    }

    @RequestMapping(value = "/products/inventory", method = RequestMethod.GET)
    public List<ProductViewModel> getProductsInInventory() {

        List<ProductViewModel> productViewModels = serviceLayer.getProductsInInventory();

        if (productViewModels != null && productViewModels.size() == 0)
            throw new NotFoundException("we don't have any products in the inventory");

        return productViewModels;
    }

    @Cacheable(cacheNames = {"products"})
    @RequestMapping(value = "/products/{productId}", method = RequestMethod.GET)
    public ProductViewModel getProductById(@PathVariable int productId) {

        ProductViewModel productViewModel = serviceLayer.getProduct(productId);

        if (productViewModel == null)

            throw new NotFoundException("Sorry! we don't have this product id no. " + productId);

        return productViewModel;
    }

    @RequestMapping(value = "/products/invoice/{invoiceId}", method = RequestMethod.GET)
    public List<ProductViewModel> getProductByInvoiceId(@PathVariable int invoiceId) {

        List<ProductViewModel> productViewModels = serviceLayer.getProductByInvoiceId(invoiceId);

        if (productViewModels != null && productViewModels.size() == 0)
            throw new NotFoundException("we don't have any products for this invoice id" + invoiceId);

        return productViewModels;
    }

    @RequestMapping(value = "/levelup/customer/{customerId}", method = RequestMethod.GET)
    public int getLevelUpPointsByCustomerId(int customerId) {

        int points =  serviceLayer.getLevelUpPointsByCustomerId(customerId);

        return points;
    }


    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductViewModel> getAllProducts() {

        List<ProductViewModel> productViewModels = serviceLayer.getAllProducts();

        if (productViewModels != null && productViewModels.size() == 0)
            throw new NotFoundException("we don't have any products");

        return productViewModels;
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.PUT)
    public void updateProduct(@PathVariable int productId, @RequestBody ProductViewModel productViewModel) {

        if (productViewModel.getProductId() == 0)
            productViewModel.setProductId(productId);

        if (productId != productViewModel.getProductId()) {

            throw new IllegalArgumentException("Sorry! the product ids don't match");

        } else productViewModel = serviceLayer.getProduct(productId);

        if (productViewModel == null)

            throw new NotFoundException("Sorry! we don't have this product id no. " + productId);

        serviceLayer.updateProduct(productViewModel, productId);
    }

    @CacheEvict(cacheNames = {"products"})
    @RequestMapping(value = "/products/{productId}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable int productId) {

        serviceLayer.deleteProduct(productId);

    }

}
