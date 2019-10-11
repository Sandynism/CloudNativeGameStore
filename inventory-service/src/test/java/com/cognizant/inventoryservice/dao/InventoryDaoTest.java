package com.cognizant.inventoryservice.dao;

import com.cognizant.inventoryservice.model.Inventory;
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
public class InventoryDaoTest {

    @Autowired
    InventoryDao id;

    private static final Integer I_ID = 1;
    private static final Inventory I_INPUT1 = new Inventory(1, 5);
    private static final Inventory I_OUTPUT1 = new Inventory(1,1,5);
    private static final Inventory I_INPUT2 = new Inventory(2, 8);

    @Before
    public void setUp() throws Exception {
        id.getAllInventory().stream().forEach(i -> id.deleteInventory(i.getInventoryId()));
    }

    @Test
    public void createGetDeleteInventory() {
        Inventory inventory = id.createInventory(I_INPUT1);

        Inventory inventory2 = id.getInventory(inventory.getInventoryId());

        assertEquals(inventory, inventory2);

        id.deleteInventory(inventory.getInventoryId());

        inventory2 = id.getInventory(inventory.getInventoryId());

        assertNull(inventory2);
    }


    @Test
    public void updateInventory() {
        Inventory inventory = id.createInventory(I_INPUT1);

        inventory.setQuantity(10);

        id.updateInventory(inventory);

        Inventory updatedInventory = id.getInventory(inventory.getInventoryId());
        assertEquals(inventory, updatedInventory);
    }


    @Test
    public void getAllInventory() {
        Inventory inventory1 = id.createInventory(I_INPUT1);
        Inventory inventory2 = id.createInventory(I_INPUT2);

        List<Inventory> inventoryList = id.getAllInventory();
        assertEquals(inventoryList.size(), 2);
    }

    @Test
    public void getAllInventoryByProductId() {
        Inventory inventory1 = id.createInventory(I_INPUT1);

        List<Inventory> inventoryList = id.getAllInventoryByProductId(1);
        assertEquals(inventoryList.size(), 1);

        List<Inventory> otherList = id.getAllInventoryByProductId(5);
        assertEquals(otherList.size(), 0);
    }
}