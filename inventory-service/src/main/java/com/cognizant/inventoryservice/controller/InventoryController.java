package com.cognizant.inventoryservice.controller;

import com.cognizant.inventoryservice.exception.NotFoundException;
import com.cognizant.inventoryservice.model.Inventory;
import com.cognizant.inventoryservice.model.InventoryViewModel;
import com.cognizant.inventoryservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/inventory")
public class InventoryController {

    @Autowired
    ServiceLayer sl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    InventoryViewModel createInventory(@RequestBody InventoryViewModel ivm) {
        return sl.createInventory(ivm);
    }

    @GetMapping(value = "/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    InventoryViewModel getInventory(@PathVariable(name = "inventoryId") Integer inventoryId) {
        InventoryViewModel ivm = sl.getInventory(inventoryId);

        if (ivm == null)
            throw new NotFoundException("Inventory with ID " + inventoryId + " not found.");
        return ivm;
    }

    @PutMapping(value = "/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    void updateInventory(@RequestBody InventoryViewModel ivm, @PathVariable(name = "inventoryId") Integer inventoryId) {
        InventoryViewModel inventory = sl.getInventory(ivm.getInventoryId());

        if (inventory == null)
            throw new IllegalArgumentException("Inventory with ID " + inventoryId + " not found.");

        sl.updateInventory(ivm);
    }

    @DeleteMapping(value = "/{inventoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteInventory(@PathVariable(name = "inventoryId") Integer inventoryId) {
        sl.deleteInventory(inventoryId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<InventoryViewModel> getAllInventory() {
        List<InventoryViewModel> ivmList = sl.getAllInventory();

        if (ivmList != null && ivmList.size() == 0) {
            throw new NotFoundException("There are no available inventory entries.");
        }

        return ivmList;
    }

    @GetMapping(value = "/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    List<InventoryViewModel> getAllInventoryByProductId(@PathVariable(name = "productId") Integer productId) {
        List<InventoryViewModel> ivmList = sl.getAllInventoryByProductId(productId);

        if (ivmList != null && ivmList.size() == 0) {
            throw new NotFoundException("There are no available inventory entries with product Id " + productId);
        }
        return ivmList;
    }
}
