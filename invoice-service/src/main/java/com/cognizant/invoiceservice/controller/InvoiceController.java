package com.cognizant.invoiceservice.controller;

import com.cognizant.invoiceservice.exception.NotFoundException;
import com.cognizant.invoiceservice.exception.QueueRequestTimeoutException;
import com.cognizant.invoiceservice.service.InvoiceItemServiceLayer;
import com.cognizant.invoiceservice.service.InvoiceServiceLayer;
import com.cognizant.invoiceservice.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    InvoiceServiceLayer serviceLayer;

    @Autowired
    InvoiceItemServiceLayer invoiceItemServiceLayer;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel createInvoice(@RequestBody @Valid InvoiceViewModel invoiceViewModel) throws QueueRequestTimeoutException {

        return serviceLayer.saveInvoice(invoiceViewModel);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getAllInvoices() {

        List<InvoiceViewModel> invoiceViewModels = serviceLayer.findAllInvoices();

        if (invoiceViewModels != null && invoiceViewModels.size() == 0)
            throw new NotFoundException("we don't have any invoices");
        return invoiceViewModels;
    }

    @GetMapping("/{invoiceId}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel getInvoice(@PathVariable("invoiceId") int invoiceId) {

        InvoiceViewModel invoiceViewModel = serviceLayer.findInvoice(invoiceId);

        if (invoiceViewModel == null)

            throw new NotFoundException("Sorry! we don't have this invoice id no. " + invoiceId);
        return invoiceViewModel;
    }


    @DeleteMapping("/{invoiceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable("invoiceId") int invoiceId) {

        serviceLayer.removeInvoice(invoiceId);
    }


    @PutMapping("/{invoiceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoice(@PathVariable("invoiceId") int invoiceId, @RequestBody @Valid InvoiceViewModel invoiceViewModel) {

        if (invoiceViewModel.getInvoiceId() == 0)
            invoiceViewModel.setInvoiceId(invoiceId);

        if (invoiceId != invoiceViewModel.getInvoiceId()) {

            throw new IllegalArgumentException("Sorry! the invoice ids don't match  ");

        } else invoiceViewModel = serviceLayer.findInvoice(invoiceId);

        if (invoiceViewModel == null)

            throw new NotFoundException("Sorry! we don't have this invoice id no. " + invoiceId);

        serviceLayer.updateInvoice(invoiceViewModel, invoiceId);
    }

    @GetMapping("/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getInvoiceByCustomerId(@PathVariable int customerId) {

        List<InvoiceViewModel> invoiceViewModels = serviceLayer.findInvoicesByCustomerId(customerId);

        if (invoiceViewModels != null && invoiceViewModels.size() == 0)
            throw new NotFoundException("we don't have any invoice for this customer");
        return invoiceViewModels;
    }

}
