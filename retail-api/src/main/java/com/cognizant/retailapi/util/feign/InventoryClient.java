package com.cognizant.retailapi.util.feign;

import com.cognizant.retailapi.model.InventoryViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name="inventory-service")
public interface InventoryClient {

    @PostMapping(value ="/inventory")
    InventoryViewModel createInventory(InventoryViewModel inventoryViewModel);

    @GetMapping(value="/inventory/{inventoryId}")
    InventoryViewModel getInventory(Integer inventoryId);

    @PutMapping(value="/inventory/{inventoryId}")
    void updateInventory(InventoryViewModel inventoryViewModel, Integer inventoryId);

    @DeleteMapping(value="/inventory/{inventoryId}")
    void deleteInventory(Integer inventoryId);

    @GetMapping(value="/inventory")
    List<InventoryViewModel> getAllInventory();

    @GetMapping(value="/inventory/product/{productId}")
    List<InventoryViewModel> getAllInventoryByProductId(Integer productId);
}
