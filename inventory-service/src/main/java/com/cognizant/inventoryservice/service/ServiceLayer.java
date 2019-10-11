package com.cognizant.inventoryservice.service;

import com.cognizant.inventoryservice.dao.InventoryDao;
import com.cognizant.inventoryservice.model.Inventory;
import com.cognizant.inventoryservice.model.InventoryViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class ServiceLayer {

    InventoryDao id;

    @Autowired
    public ServiceLayer(InventoryDao id) {
        this.id = id;
    }

    @Transactional
    public InventoryViewModel createInventory(InventoryViewModel ivm) {
        Inventory inventory = new Inventory();
        inventory.setProductId(ivm.getProductId());
        inventory.setQuantity(ivm.getQuantity());

        inventory = id.createInventory(inventory);

        return buildInventoryViewModel(inventory);
    }

    public InventoryViewModel getInventory(Integer inventoryId) {
        Inventory inventory = id.getInventory(inventoryId);

        if (inventory == null) {
            return null;
        } else {
            return buildInventoryViewModel(inventory);
        }
    }

    public void updateInventory(InventoryViewModel ivm) {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(ivm.getInventoryId());
        inventory.setProductId(ivm.getProductId());
        inventory.setQuantity(ivm.getQuantity());

        id.updateInventory(inventory);
    }

    public void deleteInventory(Integer inventoryId) {
        Inventory inventory = id.getInventory(inventoryId);

        if (inventory == null)
            throw new NoSuchElementException("Inventory with ID " + inventoryId + " does not exist.");

        id.deleteInventory(inventoryId);
    }

    public List<InventoryViewModel> getAllInventory() {
        List<Inventory> inventoryList = id.getAllInventory();
        List<InventoryViewModel> ivmList = new ArrayList<>();

        for (Inventory i : inventoryList) {
            ivmList.add(buildInventoryViewModel(i));
        }
        return ivmList;
    }

    public List<InventoryViewModel> getAllInventoryByProductId(Integer productId) {
        List<Inventory> inventoryList = id.getAllInventoryByProductId(productId);
        List<InventoryViewModel> ivmList = new ArrayList<>();

        for (Inventory i : inventoryList) {
            ivmList.add(buildInventoryViewModel(i));
        }

        return ivmList;
    }

    private InventoryViewModel buildInventoryViewModel(Inventory inventory) {
        InventoryViewModel ivm = new InventoryViewModel();

        ivm.setInventoryId(inventory.getInventoryId());
        ivm.setProductId(inventory.getProductId());
        ivm.setQuantity(inventory.getQuantity());

        return ivm;
    }
}



