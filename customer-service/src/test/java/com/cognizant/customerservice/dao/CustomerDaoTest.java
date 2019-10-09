package com.cognizant.customerservice.dao;

import com.cognizant.customerservice.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerDaoTest {

    @Autowired
    CustomerDao cd;

    private static final Integer C_ID = 1;
    private static final Customer C_INPUT1 = new Customer("Armin", "Van Buuren", "Trance", "Leiden", "2300-2334", "StateOfTrance@gmail.com", "123456789");
    private static final Customer C_OUTPUT1 = new Customer(1, "Armin", "Van Buuren", "Trance", "Leiden", "2300-2334", "StateOfTrance@gmail.com", "123456789");
    private static final Customer C_INPUT2 = new Customer( "Jai", "Wolf", "Broadway", "New York", "11001", "jaiwolf@gmail.com", "456789123");
    private static final Customer C_OUTPUT2 = new Customer(2, "Jai", "Wolf", "Broadway", "New York", "11001", "jaiwolf@gmail.com", "456789123");

    @Before
    public void setUp() throws Exception {
        List<Customer> customerList = cd.getAllCustomers();
        customerList.stream().forEach(c -> cd.deleteCustomer(c.getCustomerId()));
    }

    @Test
    public void createGetDeleteCustomer() {

        Customer customer = cd.createCustomer(C_INPUT1);

        Customer customer2 = cd.getCustomer(customer.getCustomerId());

        assertEquals(customer, customer2);

        cd.deleteCustomer(customer.getCustomerId());

        customer2 = cd.getCustomer(customer.getCustomerId());

        assertNull(customer2);
    }

    @Test
    public void updateCustomer() {
        Customer customer = cd.createCustomer(C_INPUT1);

        customer.setStreet("Trance Street");

        cd.updateCustomer(customer);

        Customer updatedCustomer = cd.getCustomer(customer.getCustomerId());
        assertEquals(customer, updatedCustomer);
    }



    @Test
    public void getAllCustomers() {
        Customer customer1 = cd.createCustomer(C_INPUT1);
        Customer customer2 = cd.createCustomer(C_INPUT2);

        List<Customer> customerList = cd.getAllCustomers();
        assertEquals(customerList.size(), 2);
    }
}