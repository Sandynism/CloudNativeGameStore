package com.cognizant.adminapi.util.feign;

import com.cognizant.adminapi.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "customer-service")
@RequestMapping(value = "/customers")
public interface CustomerClient {

    @PostMapping
    Customer createCustomer(@RequestBody Customer customer);

    @GetMapping(value = "/{customerId}")
    Customer getCustomer(@PathVariable Integer customerId);

    @PutMapping(value = "/{customerId}")
    void updateCustomer(@RequestBody Customer customer, @PathVariable Integer customerId);

    @DeleteMapping(value = "/{customerId}")
    void deleteCustomer(@PathVariable Integer customerId);

    @GetMapping
    List<Customer> getAllCustomers();

}
