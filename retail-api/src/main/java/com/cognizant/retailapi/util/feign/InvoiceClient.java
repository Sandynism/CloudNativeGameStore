package com.cognizant.retailapi.util.feign;

import com.cognizant.retailapi.model.InvoiceViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "invoice-service")
public interface InvoiceClient {

    @RequestMapping(value = "/invoices",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel createInvoice(@RequestBody @Valid InvoiceViewModel invoiceViewModel);

    @RequestMapping(value = "/invoices",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getAllInvoices();

    @RequestMapping(value = "/invoices/{invoiceId}",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel getInvoice(@PathVariable("invoiceId") int invoiceId);

    @RequestMapping(value = "/invoices/{invoiceId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable("invoiceId") int invoiceId);

    @RequestMapping(value = "/invoices/{invoiceId}",method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoice(@PathVariable("invoiceId") int invoiceId, @RequestBody @Valid InvoiceViewModel invoiceViewModel);

    @RequestMapping(value = "/invoices/customer/{customerId}",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getInvoiceByCustomerId(@PathVariable int customerId);

}
