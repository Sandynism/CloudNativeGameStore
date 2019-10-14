package com.cognizant.retailapi.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PreOrderViewModel {

    private Integer invoiceId;
    private Integer customerId;
    private LocalDate purchaseDate;

    private List<ItemsViewModel> Items;

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<ItemsViewModel> getItems() {
        return Items;
    }

    public void setItems(List<ItemsViewModel> items) {
        Items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreOrderViewModel)) return false;
        PreOrderViewModel that = (PreOrderViewModel) o;
        return Objects.equals(invoiceId, that.invoiceId) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(purchaseDate, that.purchaseDate) &&
                Objects.equals(Items, that.Items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, customerId, purchaseDate, Items);
    }
}
