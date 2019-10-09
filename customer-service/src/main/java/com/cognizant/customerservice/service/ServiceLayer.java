package com.cognizant.customerservice.service;

import com.cognizant.customerservice.dao.CustomerDao;
import com.cognizant.customerservice.model.Customer;
import com.cognizant.customerservice.model.CustomerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class ServiceLayer {

    CustomerDao cd;

    @Autowired
    public ServiceLayer(CustomerDao cd) {
        this.cd = cd;
    }

    @Transactional
    public CustomerViewModel createCustomer(CustomerViewModel cvm) {
        Customer customer = new Customer();
        customer.setFirstName(cvm.getFirstName());
        customer.setLastName(cvm.getLastName());
        customer.setStreet(cvm.getStreet());
        customer.setCity(cvm.getCity());
        customer.setZip(cvm.getZip());
        customer.setEmail(cvm.getEmail());
        customer.setPhone(cvm.getPhone());

        customer = cd.createCustomer(customer);

        return buildCustomerViewModel(customer);
    }

    public CustomerViewModel getCustomer(Integer customerId) {
        Customer customer = cd.getCustomer(customerId);

        if (customer == null) {
            return null;
        } else {
            return buildCustomerViewModel(customer);
        }
    }

    public void updateCustomer(CustomerViewModel cvm) {
        Customer customer = new Customer();
        customer.setCustomerId(cvm.getCustomerId());
        customer.setFirstName(cvm.getFirstName());
        customer.setLastName(cvm.getLastName());
        customer.setStreet(cvm.getStreet());
        customer.setCity(cvm.getCity());
        customer.setZip(cvm.getZip());
        customer.setEmail(cvm.getEmail());
        customer.setPhone(cvm.getPhone());

        cd.updateCustomer(customer);
    }

    public void deleteCustomer(Integer customerId) {

        Customer customer = cd.getCustomer(customerId);

        if (customer == null)
            throw new NoSuchElementException("Customer with ID " + customerId + " does not exist.");

        cd.deleteCustomer(customerId);
    }

    public List<CustomerViewModel> getAllCustomers() {
        List<Customer> customerList = cd.getAllCustomers();
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

}
