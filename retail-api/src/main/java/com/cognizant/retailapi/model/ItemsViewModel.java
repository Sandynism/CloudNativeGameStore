package com.cognizant.retailapi.model;

import java.util.Objects;

public class ItemsViewModel {

    private Integer InventoryId;
    private Integer quantity;

    public Integer getInventoryId() {
        return InventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        InventoryId = inventoryId;
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
        if (!(o instanceof ItemsViewModel)) return false;
        ItemsViewModel that = (ItemsViewModel) o;
        return Objects.equals(InventoryId, that.InventoryId) &&
                Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(InventoryId, quantity);
    }
}
