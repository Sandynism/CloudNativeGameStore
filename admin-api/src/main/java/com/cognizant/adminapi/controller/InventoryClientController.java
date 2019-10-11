package com.cognizant.adminapi.controller;

import com.cognizant.adminapi.exception.NoSuchInventoryException;
import com.cognizant.adminapi.exception.NotFoundException;
import com.cognizant.adminapi.model.InventoryViewModel;
import com.cognizant.adminapi.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/inventory")
public class InventoryClientController {

    @Autowired
    ServiceLayer sl;

    @PostMapping(value = "/inventory")
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryViewModel createInventory(@RequestBody InventoryViewModel ivm) {
        return sl.createInventory(ivm);
    }

    @GetMapping(value = "/inventory/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryViewModel getInventory(@PathVariable Integer inventoryId) {
        InventoryViewModel ivm = sl.getInventory(inventoryId);

        if (ivm == null)
            throw new NotFoundException("Inventory with ID " + inventoryId + " does not exist.");

        return ivm;
    }

    @PutMapping(value = "/inventory/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateInventory(@RequestBody InventoryViewModel ivm, @PathVariable Integer inventoryId) {
        InventoryViewModel inventory = sl.getInventory(ivm.getInventoryId());

        if (inventory == null)
            throw new IllegalArgumentException("Inventory with ID " + inventoryId + " does not exist.");

        sl.updateInventory(ivm);
    }

    @DeleteMapping(value = "/inventory/{inventoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable Integer inventoryId) {
        InventoryViewModel inventory = sl.getInventory(inventoryId);

        if (inventory == null)
            throw new NoSuchInventoryException(inventoryId);

        sl.deleteInventory(inventoryId);
    }

    @GetMapping(value = "/inventory")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryViewModel> getAllInventory() {
        List<InventoryViewModel> inventoryList = sl.getAllInventory();

        if (inventoryList != null && inventoryList.size() == 0) {
            throw new NotFoundException("There are no entries for inventory.");
        }
        return inventoryList;
    }

    @GetMapping(value = "/inventory/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryViewModel> getAllInventoryByProductId(@PathVariable Integer productId) {
        List<InventoryViewModel> inventoryList = sl.getAllInventoryByProductId(productId);

        if (inventoryList != null && inventoryList.size() == 0) {
            throw new NotFoundException("There are no entries with matching product ID " + productId);
        }

        return inventoryList;
    }
}
