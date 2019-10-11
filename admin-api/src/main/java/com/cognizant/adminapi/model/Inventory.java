package com.cognizant.adminapi.model;

import java.util.Objects;

public class Inventory {
    private Integer inventoryId;
    private Integer productId;
    private Integer quantity;

    public Inventory() {}

    public Inventory(Integer productId, Integer quantity){
        this.productId = productId;
        this.quantity = quantity;
    }

    public Inventory(Integer inventoryId, Integer productId, Integer quantity){
        this.inventoryId = inventoryId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inventory)) return false;
        Inventory inventory = (Inventory) o;
        return Objects.equals(getInventoryId(), inventory.getInventoryId()) &&
                Objects.equals(getProductId(), inventory.getProductId()) &&
                Objects.equals(getQuantity(), inventory.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInventoryId(), getProductId(), getQuantity());
    }
}
