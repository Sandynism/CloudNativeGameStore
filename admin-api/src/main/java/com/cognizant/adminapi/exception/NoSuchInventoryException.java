package com.cognizant.adminapi.exception;

public class NoSuchInventoryException extends RuntimeException{
    public NoSuchInventoryException(Integer inventoryId){
        super("There is no such inventory with inventory id " + inventoryId + ".");
    }
}

