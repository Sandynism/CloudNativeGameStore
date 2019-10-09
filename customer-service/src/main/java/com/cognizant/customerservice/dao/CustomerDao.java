package com.cognizant.customerservice.dao;

import com.cognizant.customerservice.model.Customer;

import java.util.List;

public interface CustomerDao {
    Customer createCustomer(Customer customer);

    Customer getCustomer(Integer customerId);

    void updateCustomer(Customer customer);

    void deleteCustomer(Integer customerId);

    List<Customer> getAllCustomers();
}
