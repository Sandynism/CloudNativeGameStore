package com.cognizant.customerservice.service;

import com.cognizant.customerservice.dao.CustomerDao;
import com.cognizant.customerservice.dao.CustomerDaoJdbcTemplateImpl;
import com.cognizant.customerservice.model.Customer;
import com.cognizant.customerservice.model.CustomerViewModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

public class ServiceLayerTest {

    private CustomerDao cd;
    private ServiceLayer sl;

    private static final Integer C_ID = 1;
    private static final Customer C_INPUT1 = new Customer("Armin", "Van Buuren", "Trance", "Leiden", "2300-2334", "StateOfTrance@gmail.com", "123456789");
    private static final Customer C_OUTPUT1 = new Customer(1, "Armin", "Van Buuren", "Trance", "Leiden", "2300-2334", "StateOfTrance@gmail.com", "123456789");
    private static final Customer C_OUTPUT2 = new Customer(2, "Jai", "Wolf", "Broadway", "New York", "11001", "jaiwolf@gmail.com", "456789123");
    private static final Customer C_OUTPUT3 = new Customer(3, "Nick", "Miller", "10th St", "Denver", "54321", "illenium@gmail.com", "987654321");
    private static final CustomerViewModel CVM_INPUT1 = new CustomerViewModel("Armin", "Van Buuren", "Trance", "Leiden", "2300-2334", "StateOfTrance@gmail.com", "123456789");
    private static final CustomerViewModel CVM_OUTPUT1 = new CustomerViewModel(1, "Armin", "Van Buuren", "Trance", "Leiden", "2300-2334", "StateOfTrance@gmail.com", "123456789");


    @Before
    public void setUp() throws Exception {
        setUpCustomerDaoMock();

        sl = new ServiceLayer(cd);
    }

    private void setUpCustomerDaoMock() {
        cd = mock(CustomerDaoJdbcTemplateImpl.class);

        //add
        doReturn(C_OUTPUT1).when(cd).createCustomer(C_INPUT1);

        //get by id
        doReturn(C_OUTPUT1).when(cd).getCustomer(C_ID);

        //get all
        List<Customer> cList = new ArrayList<>();
        cList.add(C_OUTPUT1);
        cList.add(C_OUTPUT2);
        doReturn(cList).when(cd).getAllCustomers();

        //update
        doNothing().when(cd).updateCustomer(C_OUTPUT3);
        doReturn(C_OUTPUT3).when(cd).getCustomer(3);

        //delete
        doNothing().when(cd).deleteCustomer(10);
        doReturn(null).when(cd).getCustomer(10);
    }

    @Test
    public void createGetCustomer() {
        CustomerViewModel cvm = sl.createCustomer(CVM_INPUT1);

        CustomerViewModel fromService = sl.getCustomer(cvm.getCustomerId());

        assertEquals(cvm, fromService);
    }


    @Test
    public void updateCustomer() {
        Customer customer = C_OUTPUT3;

        CustomerViewModel cvm = new CustomerViewModel();
        cvm.setCustomerId(C_OUTPUT3.getCustomerId());
        cvm.setFirstName(C_OUTPUT3.getFirstName());
        cvm.setLastName(C_OUTPUT3.getLastName());
        cvm.setStreet(C_OUTPUT3.getStreet());
        cvm.setCity(C_OUTPUT3.getCity());
        cvm.setZip(C_OUTPUT3.getZip());
        cvm.setEmail(C_OUTPUT3.getEmail());
        cvm.setPhone(C_OUTPUT3.getPhone());

        sl.updateCustomer(cvm);

        CustomerViewModel fromService = sl.getCustomer(cvm.getCustomerId());

        assertEquals(cvm, fromService);
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteCustomer() {
        sl.deleteCustomer(10);

        CustomerViewModel cvm = sl.getCustomer(10);
    }

    @Test
    public void getAllCustomers() {
        List<CustomerViewModel> cvmList = sl.getAllCustomers();
        assertEquals(cvmList.size(), 2);
    }
}