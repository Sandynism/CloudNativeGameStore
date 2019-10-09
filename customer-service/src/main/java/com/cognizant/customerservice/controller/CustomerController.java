package com.cognizant.customerservice.controller;

import com.cognizant.customerservice.exception.NotFoundException;
import com.cognizant.customerservice.model.Customer;
import com.cognizant.customerservice.model.CustomerViewModel;
import com.cognizant.customerservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    ServiceLayer sl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerViewModel createCustomer(@RequestBody CustomerViewModel cvm) {
        return sl.createCustomer(cvm);
    }

    @GetMapping(value="/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerViewModel getCustomer(@PathVariable Integer customerId) {
        CustomerViewModel cvm = sl.getCustomer(customerId);
        if(cvm == null)
            throw new NotFoundException("Customer with ID " + customerId + "does not exist.");
        return cvm;
    }

    @PutMapping(value="/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@RequestBody CustomerViewModel cvm, @PathVariable Integer customerId) {
        CustomerViewModel customer = sl.getCustomer(cvm.getCustomerId());
        if(customer == null)
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist. Cannot be updated.");
        sl.updateCustomer(customer);
    }

    @DeleteMapping(value="/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Integer customerId) {
        sl.deleteCustomer(customerId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerViewModel> getAllCustomers() {
        return sl.getAllCustomers();
    }
}
