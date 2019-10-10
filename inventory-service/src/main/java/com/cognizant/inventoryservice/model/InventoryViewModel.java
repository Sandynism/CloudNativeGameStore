package com.cognizant.inventoryservice.model;

import java.util.Objects;

public class InventoryViewModel {
    private Integer inventoryId;
    private Integer productId;
    private Integer quantity;

    public InventoryViewModel() {}

    public InventoryViewModel(Integer productId, Integer quantity){
        this.productId = productId;
        this.quantity = quantity;
    }

    public InventoryViewModel(Integer inventoryId, Integer productId, Integer quantity){
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
        if (!(o instanceof InventoryViewModel)) return false;
        InventoryViewModel that = (InventoryViewModel) o;
        return Objects.equals(getInventoryId(), that.getInventoryId()) &&
                Objects.equals(getProductId(), that.getProductId()) &&
                Objects.equals(getQuantity(), that.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInventoryId(), getProductId(), getQuantity());
    }
}
