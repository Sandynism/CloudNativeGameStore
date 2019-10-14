package com.cognizant.adminapi.util.feign;

import com.cognizant.adminapi.model.Invoice;
import com.cognizant.adminapi.model.InvoiceItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="invoice-service")
public interface InvoiceClient {

//    @PostMapping(value="/invoices")
//    Invoice createInvoice(@RequestBody Invoice invoice);

    @GetMapping(value="/invoices/{invoiceId}")
    Invoice getInvoice(@PathVariable(name="invoiceId") Integer invoiceId);

//    @PutMapping(value="/invoices/{invoiceId}")
//    void updateInvoice(@RequestBody Invoice invoice, @PathVariable(name="invoiceId") Integer invoiceId);
//
//    @DeleteMapping(value="/invoices/{invoiceId}")
//    void deleteInvoice(@PathVariable Integer invoiceId);

    @GetMapping(value="/invoices")
    List<Invoice> getAllInvoices();

//    has not been added to controller of invoice service
    @GetMapping(value="/invoices/customer/{customerId}")
    List<Invoice> getInvoicesByCustomerId(@PathVariable(name="customerId") Integer customerId);

//    @PostMapping(value="/invoiceItems")
//    InvoiceItem createInvoiceItem(@RequestBody InvoiceItem invoiceItem);

//    @GetMapping(value="/invoiceItems/{invoiceItemId}")
//    InvoiceItem getInvoiceItem(@PathVariable(name="invoiceItemId") Integer invoiceItemId);

//    @PutMapping(value="/invoiceItems/{invoiceItemId}")
//    void updateInvoiceItem(@RequestBody InvoiceItem invoiceItem, @PathVariable(name="invoiceItemId") Integer invoiceItemId);
//
//    @DeleteMapping(value="/invoiceItems/{invoiceItemId}")
//    void deleteInvoiceItem(@PathVariable(name="invoiceItemId") Integer invoiceItemId);

//    @GetMapping(value="/invoiceItems")
//    List<InvoiceItem> getAllInvoiceItems();

    @GetMapping(value="/invoiceItems/invoices/{invoiceId}")
    List<InvoiceItem> getInvoiceItemsByInvoiceId(@PathVariable(name="invoiceId") Integer invoiceId);

//    @GetMapping(value="/invoiceItems/invoices/{invoiceId}")
//    List<InvoiceItem> getInvoiceItemsByInventoryId(@PathVariable(name="inventoryId") Integer inventoryId);

}
