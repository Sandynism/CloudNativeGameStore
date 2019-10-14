package com.cognizant.retailapi.util.feign;

import com.cognizant.retailapi.model.InventoryViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="inventory-service")
public interface InventoryClient {

    @PostMapping(value ="/inventory")
    InventoryViewModel createInventory(@RequestBody InventoryViewModel inventoryViewModel);

    @GetMapping(value="/inventory/{inventoryId}")
    InventoryViewModel getInventory(@PathVariable Integer inventoryId);

    @PutMapping(value="/inventory/{inventoryId}")
    void updateInventory(@RequestBody InventoryViewModel inventoryViewModel, @PathVariable Integer inventoryId);

    @DeleteMapping(value="/inventory/{inventoryId}")
    void deleteInventory(@PathVariable Integer inventoryId);

    @GetMapping(value="/inventory")
    List<InventoryViewModel> getAllInventory();

    @GetMapping(value="/inventory/product/{productId}")
    List<InventoryViewModel> getAllInventoryByProductId(@PathVariable Integer productId);
}
