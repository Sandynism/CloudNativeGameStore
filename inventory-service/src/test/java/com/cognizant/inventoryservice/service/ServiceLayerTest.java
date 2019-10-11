package com.cognizant.inventoryservice.service;

import com.cognizant.inventoryservice.dao.InventoryDao;
import com.cognizant.inventoryservice.dao.InventoryDaoJdbcTemplateImpl;
import com.cognizant.inventoryservice.model.Inventory;
import com.cognizant.inventoryservice.model.InventoryViewModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

public class ServiceLayerTest {

    private InventoryDao id;
    private ServiceLayer sl;

    private static final Integer I_ID = 1;
    private static final Inventory I_INPUT1 = new Inventory(1, 10);
    private static final Inventory I_OUTPUT1 = new Inventory(1, 1, 10);
    private static final Inventory I_OUTPUT2 = new Inventory(2, 2, 100);
    private static final Inventory I_OUTPUT3 = new Inventory(3, 3, 10);
    private static final InventoryViewModel IVM_INPUT1 = new InventoryViewModel(1, 10);
    private static final InventoryViewModel IVM_OUTPUT1 = new InventoryViewModel(1, 1, 10);


    @Before
    public void setUp() throws Exception {
        setUpInventoryDaoMock();

        sl = new ServiceLayer(id);
    }

    private void setUpInventoryDaoMock(){
        id = mock(InventoryDaoJdbcTemplateImpl.class);

        //add
        doReturn(I_OUTPUT1).when(id).createInventory(I_INPUT1);

        //get by id
        doReturn(I_OUTPUT1).when(id).getInventory(I_ID);

        //get all
        List<Inventory> iList = new ArrayList<>();
        iList.add(I_OUTPUT1);
        iList.add(I_OUTPUT2);
        doReturn(iList).when(id).getAllInventory();

        //update
        doNothing().when(id).updateInventory(I_OUTPUT3);
        doReturn(I_OUTPUT3).when(id).getInventory(3);

        //delete
        doNothing().when(id).deleteInventory(10);
        doReturn(null).when(id).getInventory(10);

        //get all by product id
        List<Inventory> productList = new ArrayList<>();
        productList.add(I_OUTPUT2);
        doReturn(productList).when(id).getAllInventoryByProductId(2);
    }

    @Test
    public void createGetInventory() {
        InventoryViewModel ivm = sl.createInventory(IVM_INPUT1);

        InventoryViewModel fromService = sl.getInventory(ivm.getInventoryId());

        assertEquals(ivm, fromService);
    }


    @Test
    public void updateInventory() {
        Inventory inventory = I_OUTPUT3;

        InventoryViewModel ivm = new InventoryViewModel();
        ivm.setInventoryId(inventory.getInventoryId());
        ivm.setProductId(inventory.getProductId());
        ivm.setQuantity(inventory.getQuantity());

        sl.updateInventory(ivm);

        InventoryViewModel fromService = sl.getInventory(ivm.getInventoryId());

        assertEquals(ivm, fromService);
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteInventory() {
        sl.deleteInventory(10);

        InventoryViewModel ivm = sl.getInventory(10);
    }

    @Test
    public void getAllInventory() {
        List<InventoryViewModel> ivmList = sl.getAllInventory();
        assertEquals(ivmList.size(), 2);
    }

    @Test
    public void getAllInventoryByProductId() {
        List<InventoryViewModel> ivmList = sl.getAllInventoryByProductId(2);
        assertEquals(ivmList.size(), 1);
    }
}