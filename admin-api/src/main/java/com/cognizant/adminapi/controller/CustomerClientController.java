package com.cognizant.adminapi.controller;

import com.cognizant.adminapi.exception.NoSuchCustomerException;
import com.cognizant.adminapi.exception.NotFoundException;
import com.cognizant.adminapi.model.CustomerViewModel;
import com.cognizant.adminapi.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value="/customers")
public class CustomerClientController {

    @Autowired
    ServiceLayer sl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerViewModel createCustomer(@RequestBody CustomerViewModel cvm) {
        return sl.createCustomer(cvm);
    }

    @GetMapping(value = "/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerViewModel getCustomer(@PathVariable(name="customerId") Integer customerId) {
        CustomerViewModel cvm = sl.getCustomer(customerId);

        if (cvm == null)
            throw new NotFoundException("Customer with ID " + customerId + "does not exist.");

        return cvm;
    }

    @PutMapping(value = "/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@RequestBody CustomerViewModel cvm, @PathVariable(name="customerId") Integer customerId) {
        CustomerViewModel customer = sl.getCustomer(cvm.getCustomerId());

        if (customer == null)
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist. Cannot be updated.");

        sl.updateCustomer(cvm);
    }

    @DeleteMapping(value = "/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable(name="customerId") Integer customerId) {
        CustomerViewModel customer = sl.getCustomer(customerId);

        if(customer == null)
            throw new NoSuchCustomerException(customerId);

        sl.deleteCustomer(customerId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerViewModel> getAllCustomers() {
        List<CustomerViewModel> customerList = sl.getAllCustomers();

        if (customerList != null && customerList.size() == 0) {
            throw new NotFoundException("There are no customers available.");
        }

        return customerList;
    }

}
