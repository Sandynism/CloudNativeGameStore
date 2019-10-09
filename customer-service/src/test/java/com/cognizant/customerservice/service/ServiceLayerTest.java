package com.cognizant.customerservice.service;

import com.cognizant.customerservice.dao.CustomerDao;
import com.cognizant.customerservice.model.Customer;
import com.cognizant.customerservice.model.CustomerViewModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;

public class ServiceLayerTest {

    private CustomerDao cd;
    private ServiceLayer sl;

    private static final Integer C_ID = 1;
    private static final Customer C_INPUT = new Customer("Armin", "Van Buuren", "Trance", "Leiden", "2300-2334", "StateOfTrance@gmail.com", "123456789");
    private static final Customer C_OUTPUT = new Customer(1, "Armin", "Van Buuren", "Trance", "Leiden", "2300-2334", "StateOfTrance@gmail.com", "123456789");
    private static final CustomerViewModel CVM_INPUT = new CustomerViewModel("Armin", "Van Buuren", "Trance", "Leiden", "2300-2334", "StateOfTrance@gmail.com", "123456789");
    private static final CustomerViewModel CVM_OUTPUT = new CustomerViewModel(1, "Armin", "Van Buuren", "Trance", "Leiden", "2300-2334", "StateOfTrance@gmail.com", "123456789");


    @Before
    public void setUp() throws Exception {
        setUpCustomerDaoMock();
        sl = new ServiceLayer(cd);
    }

    private void setUpCustomerDaoMock() {
        cd = mock(CustomerDao.class);

        doReturn(CVM_OUTPUT).when(sl).createCustomer(CVM_INPUT);
        doReturn(CVM_OUTPUT).when(sl).getCustomer(C_ID);
        List<CustomerViewModel> cvmList = new ArrayList<>();
        cvmList.add(CVM_OUTPUT);
        doReturn(cvmList).when(sl).getAllCustomers();

        //delete and update
    }

    @Test
    public void createCustomer() {
        CustomerViewModel cvm = sl.createCustomer(CVM_INPUT);
        assertEquals(cvm, CVM_OUTPUT);
    }

    @Test
    public void getCustomer() {
    }

    @Test
    public void updateCustomer() {
    }

    @Test
    public void deleteCustomer() {
    }

    @Test
    public void getAllCustomers() {
    }
}