package com.cognizant.customerservice.controller;

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
        return sl.getCustomer(customerId);
    }

    @PutMapping(value="/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@RequestBody CustomerViewModel cvm, @PathVariable Integer customerId) {
        sl.updateCustomer(cvm);
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
