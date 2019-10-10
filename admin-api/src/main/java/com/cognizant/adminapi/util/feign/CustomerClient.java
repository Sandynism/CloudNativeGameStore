package com.cognizant.adminapi.util.feign;

import com.cognizant.adminapi.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "customer-service")
public interface CustomerClient {

    @PostMapping(value = "/customers")
    Customer createCustomer(@RequestBody Customer customer);

    @GetMapping(value = "/customers/{customerId}")
    Customer getCustomer(@PathVariable Integer customerId);

    @PutMapping(value = "/customers/{customerId}")
    void updateCustomer(@RequestBody Customer customer, @PathVariable Integer customerId);

    @DeleteMapping(value = "/customers/{customerId}")
    void deleteCustomer(@PathVariable Integer customerId);

    @GetMapping(value = "/customers")
    List<Customer> getAllCustomers();

}
