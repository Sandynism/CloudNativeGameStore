package com.cognizant.invoiceservice.dao;

import com.cognizant.invoiceservice.model.InvoiceItem;

import java.util.List;

public interface InvoiceItemDao {

    InvoiceItem getInvoiceItem (int invoiceItemId);

    InvoiceItem addInvoiceItem (InvoiceItem invoiceItem);

    List<InvoiceItem> getAllInvoiceItems();

    List<InvoiceItem> getInvoiceItemsByInvoiceId (int invoiceId);

    void updateInvoiceItem (InvoiceItem invoiceItem, int invoiceItemId);

    void deleteInvoiceItem (int invoiceItemId);

    InvoiceItem getInvoiceItemByInventoryId (int inventoryId);

    void deleteByInvoiceId(int invoiceId);
}
