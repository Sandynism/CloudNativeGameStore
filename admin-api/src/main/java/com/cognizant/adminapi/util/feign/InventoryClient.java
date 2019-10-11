package com.cognizant.adminapi.util.feign;

import com.cognizant.adminapi.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name="inventory-service")
public interface InventoryClient {

    @PostMapping(value ="/inventory")
    Inventory createInventory(Inventory inventory);

    @GetMapping(value="/inventory/{inventoryId}")
    Inventory getInventory(Integer inventoryId);

    @PutMapping(value="/inventory/{inventoryId}")
    void updateInventory(Inventory inventory);

    @DeleteMapping(value="/inventory/{inventoryId}")
    void deleteInventory(Integer inventoryId);

    @GetMapping(value="/inventory")
    List<Inventory> getAllInventory();

    @GetMapping(value="/inventory/product/{productId}")
    List<Inventory> getAllInventoryByProductId(Integer productId);
}
