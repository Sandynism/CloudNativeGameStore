package com.cognizant.inventoryservice.dao;

import com.cognizant.inventoryservice.model.Inventory;

import java.util.List;

public interface InventoryDao {

    Inventory createInventory(Inventory inventory);

    Inventory getInventory(Integer inventoryId);

    void updateInventory(Inventory inventory);

    void deleteInventory(Integer inventoryId);

    List<Inventory> getAllInventory();
}
