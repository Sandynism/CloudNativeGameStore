package com.cognizant.retailapi.model;

import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceItemViewModel {

    private Integer InvoiceItemId;
    private Integer InvoiceId;
    private Integer InventoryId;
    private Integer quantity;
    private BigDecimal unitPrice;

    public Integer getInvoiceItemId() {
        return InvoiceItemId;
    }

    public void setInvoiceItemId(Integer invoiceItemId) {
        InvoiceItemId = invoiceItemId;
    }

    public Integer getInvoiceId() {
        return InvoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        InvoiceId = invoiceId;
    }

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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceItemViewModel)) return false;
        InvoiceItemViewModel that = (InvoiceItemViewModel) o;
        return Objects.equals(InvoiceItemId, that.InvoiceItemId) &&
                Objects.equals(InvoiceId, that.InvoiceId) &&
                Objects.equals(InventoryId, that.InventoryId) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(unitPrice, that.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(InvoiceItemId, InvoiceId, InventoryId, quantity, unitPrice);
    }
}
