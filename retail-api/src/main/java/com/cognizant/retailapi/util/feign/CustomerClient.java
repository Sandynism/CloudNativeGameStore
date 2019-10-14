package com.cognizant.retailapi.util.feign;

import com.cognizant.retailapi.model.CustomerViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "customer-service")
public interface CustomerClient {

    @PostMapping(value = "/customers")
    CustomerViewModel createCustomer(@RequestBody CustomerViewModel customerViewModel);

    @GetMapping(value = "/customers/{customerId}")
    CustomerViewModel getCustomer(@PathVariable Integer customerId);

    @PutMapping(value = "/customers/{customerId}")
    void updateCustomer(@RequestBody CustomerViewModel customerViewModel, @PathVariable Integer customerId);

    @DeleteMapping(value = "/customers/{customerId}")
    void deleteCustomer(@PathVariable Integer customerId);

    @GetMapping(value = "/customers")
    List<CustomerViewModel> getAllCustomers();

}
