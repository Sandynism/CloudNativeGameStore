package com.cognizant.retailapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class OrderViewModel {

    private Integer invoiceId;
    private Integer customerId;
    private Integer levelUpPoints;
    private LocalDate purchaseDate;
    private BigDecimal total;

    private List<InvoiceItemViewModel> invoiceItems;

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

    public Integer getLevelUpPoints() {
        return levelUpPoints;
    }

    public void setLevelUpPoints(Integer levelUpPoints) {
        this.levelUpPoints = levelUpPoints;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<InvoiceItemViewModel> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItemViewModel> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderViewModel)) return false;
        OrderViewModel that = (OrderViewModel) o;
        return Objects.equals(invoiceId, that.invoiceId) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(levelUpPoints, that.levelUpPoints) &&
                Objects.equals(purchaseDate, that.purchaseDate) &&
                Objects.equals(total, that.total) &&
                Objects.equals(invoiceItems, that.invoiceItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, customerId, levelUpPoints, purchaseDate, total, invoiceItems);
    }


}
