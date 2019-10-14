package com.cognizant.adminapi.controller;

import com.cognizant.adminapi.exception.NotFoundException;
import com.cognizant.adminapi.model.InvoiceViewModel;
import com.cognizant.adminapi.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/invoices")
public class InvoiceClientController {

    @Autowired
    ServiceLayer sl;

    @GetMapping(value = "/{invoiceId}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel getInvoice(@PathVariable Integer invoiceId) {
        InvoiceViewModel ivm = sl.getInvoice(invoiceId);

        if (ivm == null)
            throw new NotFoundException("Invoice with ID " + invoiceId + " does not exist.");

        return ivm;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getAllInvoices() {
        List<InvoiceViewModel> invoiceList = sl.getAllInvoices();

        if (invoiceList != null && invoiceList.size() == 0) {
            throw new NotFoundException("There are no invoices available.");
        }

        return invoiceList;
    }

    @GetMapping(value = "/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getAllInvoicesByCustomerId(@PathVariable Integer customerId) {
        List<InvoiceViewModel> invoiceList = sl.getAllInvoicesByCustomerId(customerId);

        if (invoiceList != null && invoiceList.size() == 0) {
            throw new NotFoundException("There are no invoices with the customer ID " + customerId);
        }

        return invoiceList;
    }

}
