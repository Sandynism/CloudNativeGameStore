package com.cognizant.inventoryservice.controller;

import com.cognizant.inventoryservice.model.Inventory;
import com.cognizant.inventoryservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RefreshScope
//@RequestMapping(value="/inventory")
//public class InventoryController {
//
//    @Autowired
//    ServiceLayer sl;
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    Inventory createInventory(@RequestBody Inventory inventory){
////        return sl;
//    }
//
//    @GetMapping(value="/{inventoryId}")
//    @ResponseStatus(HttpStatus.OK)
//    Inventory getInventory(@PathVariable(name="inventoryId") Integer inventoryId){}
//
//    @PutMapping(value="/{inventoryId}")
//    @ResponseStatus(HttpStatus.OK)
//    void updateInventory(@RequestBody Inventory inventory, @PathVariable(name="inventoryId") Integer inventoryId){}
//
//    @DeleteMapping(value="/{inventoryId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    void deleteInventory(@PathVariable(name="inventoryId") Integer inventoryId){}
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    List<Inventory> getAllInventory(){}
//
//    @GetMapping(value="/product/{productId}")
//    @ResponseStatus(HttpStatus.OK)
//    List<Inventory> getAllInventoryByProductId(@PathVariable(name="productId") Integer productId){
//
//    }
//}
