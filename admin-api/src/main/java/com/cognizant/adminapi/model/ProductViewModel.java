package com.cognizant.adminapi.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

public class ProductViewModel {
    private Integer productId;
    @Size(min = 1, max = 50, message = "Product name cannot be empty.")
    private String productName;
    @Size(min = 1, max = 255, message = "Product description cannot be empty.")
    private String productDescription;
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "9999999.99")
    private BigDecimal listPrice;
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "9999999.99")
    private BigDecimal unitCost;

    public ProductViewModel() {
    }

    //constructor for input
    public ProductViewModel(String productName, String productDescription, BigDecimal listPrice, BigDecimal unitCost) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.listPrice = listPrice;
        this.unitCost = unitCost;
    }

    //constructor for output
    public ProductViewModel(Integer productId, String productName, String productDescription, BigDecimal listPrice, BigDecimal unitCost) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.listPrice = listPrice;
        this.unitCost = unitCost;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductViewModel)) return false;
        ProductViewModel that = (ProductViewModel) o;
        return Objects.equals(getProductId(), that.getProductId()) &&
                Objects.equals(getProductName(), that.getProductName()) &&
                Objects.equals(getProductDescription(), that.getProductDescription()) &&
                Objects.equals(getListPrice(), that.getListPrice()) &&
                Objects.equals(getUnitCost(), that.getUnitCost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getProductName(), getProductDescription(), getListPrice(), getUnitCost());
    }
}
