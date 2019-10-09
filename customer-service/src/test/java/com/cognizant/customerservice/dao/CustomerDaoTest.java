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

    @Before
    public void setUp() throws Exception {
        List<Customer> customerList = cd.getAllCustomers();
        customerList.stream().forEach(c -> cd.deleteCustomer(c.getCustomerId()));
    }

    @Test
    public void createGetDeleteCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Armin");
        customer.setLastName("Van Buuren");
        customer.setStreet("Trance");
        customer.setCity("Leiden");
        customer.setZip("2300-2334");
        customer.setEmail("StateOfTrance@gmail.com");
        customer.setPhone("123456789");

        customer = cd.createCustomer(customer);

        Customer customer2 = cd.getCustomer(customer.getCustomerId());

        assertEquals(customer, customer2);

        cd.deleteCustomer(customer.getCustomerId());

        customer2 = cd.getCustomer(customer.getCustomerId());

        assertNull(customer2);
    }

    @Test
    public void updateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Armin");
        customer.setLastName("Van Buuren");
        customer.setStreet("Trance");
        customer.setCity("Leiden");
        customer.setZip("2300-2334");
        customer.setEmail("StateOfTrance@gmail.com");
        customer.setPhone("123456789");

        cd.createCustomer(customer);

        customer.setStreet("Trance Street");

        cd.updateCustomer(customer);

        Customer updatedCustomer = cd.getCustomer(customer.getCustomerId());
        assertEquals(customer, updatedCustomer);
    }



    @Test
    public void getAllCustomers() {
        Customer customer = new Customer();
        customer.setFirstName("Armin");
        customer.setLastName("Van Buuren");
        customer.setStreet("Trance");
        customer.setCity("Leiden");
        customer.setZip("2300-2334");
        customer.setEmail("StateOfTrance@gmail.com");
        customer.setPhone("123456789");

        cd.createCustomer(customer);

        Customer customer2 = new Customer();
        customer2.setFirstName("Nick");
        customer2.setLastName("Miller");
        customer2.setStreet("10th St");
        customer2.setCity("Denver");
        customer2.setZip("11900");
        customer2.setEmail("illenium@gmail.com");
        customer2.setPhone("987654321");

        cd.createCustomer(customer2);

        List<Customer> customerList = cd.getAllCustomers();
        assertEquals(customerList.size(), 2);
    }
}