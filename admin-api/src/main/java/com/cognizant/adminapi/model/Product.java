package com.cognizant.adminapi.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

public class Product {
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

    public Product() {
    }

    //constructor for input
    public Product(String productName, String productDescription, BigDecimal listPrice, BigDecimal unitCost) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.listPrice = listPrice;
        this.unitCost = unitCost;
    }

    //constructor for output
    public Product(Integer productId, String productName, String productDescription, BigDecimal listPrice, BigDecimal unitCost) {
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
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(getProductId(), product.getProductId()) &&
                Objects.equals(getProductName(), product.getProductName()) &&
                Objects.equals(getProductDescription(), product.getProductDescription()) &&
                Objects.equals(getListPrice(), product.getListPrice()) &&
                Objects.equals(getUnitCost(), product.getUnitCost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getProductName(), getProductDescription(), getListPrice(), getUnitCost());
    }
}
