package com.cognizant.invoiceservice.dao;

import com.cognizant.invoiceservice.model.Invoice;

import java.util.List;

public interface InvoiceDao {

    Invoice getInvoice(int invoiceId);

    Invoice addInvoice(Invoice invoice);

    List<Invoice> getAllInvoices ();

    void updateInvoice (Invoice invoice, int invoiceId);

    void deleteInvoice (int invoiceId);

    List<Invoice> getInvoiceByCustomerId(int customerId);

}
