package com.cognizant.adminapi.util.feign;

import com.cognizant.adminapi.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="inventory-service")
public interface InventoryClient {

    @PostMapping(value ="/inventory")
    Inventory createInventory(@RequestBody Inventory inventory);

    @GetMapping(value="/inventory/{inventoryId}")
    Inventory getInventory(@PathVariable Integer inventoryId);

    @PutMapping(value="/inventory/{inventoryId}")
    void updateInventory(@RequestBody Inventory inventory, @PathVariable Integer inventoryId);

    @DeleteMapping(value="/inventory/{inventoryId}")
    void deleteInventory(@PathVariable Integer inventoryId);

    @GetMapping(value="/inventory")
    List<Inventory> getAllInventory();

    @GetMapping(value="/inventory/product/{productId}")
    List<Inventory> getAllInventoryByProductId(@PathVariable Integer productId);
}
