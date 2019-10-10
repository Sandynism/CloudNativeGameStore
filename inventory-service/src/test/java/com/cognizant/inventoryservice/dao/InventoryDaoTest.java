package com.cognizant.inventoryservice.dao;

import com.cognizant.inventoryservice.model.Inventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InventoryDaoTest {

    @Autowired
    InventoryDao id;

    private static final Integer I_ID = 1;
    private static final Inventory I_INPUT1 = new Inventory();
    private static final Inventory I_OUTPUT1 = new Inventory();
    private static final Inventory I_INPUT2 = new Inventory();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void createInventory() {
    }

    @Test
    public void getInventory() {
    }

    @Test
    public void updateInventory() {
    }

    @Test
    public void deleteInventory() {
    }

    @Test
    public void getAllInventory() {
    }
}