package com.cognizant.invoiceservice.controller;

import com.cognizant.invoiceservice.exception.NotFoundException;
import com.cognizant.invoiceservice.service.InvoiceItemServiceLayer;
import com.cognizant.invoiceservice.viewModel.InvoiceItemViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/invoiceItem")
public class InvoiceItemsController {

    @Autowired
    InvoiceItemServiceLayer serviceLayer;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceItemViewModel createInvoiceItem(@RequestBody @Valid InvoiceItemViewModel invoiceItemViewModel) {

        return serviceLayer.saveInvoiceItem(invoiceItemViewModel);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceItemViewModel> getInvoiceItem() {

        List<InvoiceItemViewModel> invoiceItemViewModels = serviceLayer.findAllInvoiceItems();

        if (invoiceItemViewModels != null && invoiceItemViewModels.size() == 0)
            throw new NotFoundException("we don't have any invoice items");
        return invoiceItemViewModels;
    }

    @GetMapping("/{invoiceItemId}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceItemViewModel getInvoiceItem(@PathVariable("invoiceItemId") int invoiceItemId) {

        InvoiceItemViewModel invoiceItemViewModel = serviceLayer.findInvoiceItem(invoiceItemId);

        if (invoiceItemViewModel == null)

            throw new NotFoundException("Sorry! we don't have this Level up id no. " + invoiceItemId);
        return invoiceItemViewModel;
    }


    @DeleteMapping("/{invoiceItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoiceItem(@PathVariable("invoiceItemId") int invoiceItemId) {

        serviceLayer.removeInvoiceItem(invoiceItemId);
    }


    @PutMapping("/{invoiceItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoiceItem(@PathVariable("invoiceItemId") int invoiceItemId, @RequestBody @Valid InvoiceItemViewModel invoiceItemViewModel) {

        if (invoiceItemViewModel.getInvoiceItemId() == 0)
            invoiceItemViewModel.setInvoiceItemId(invoiceItemId);

        if (invoiceItemId != invoiceItemViewModel.getInvoiceItemId()) {

            throw new IllegalArgumentException("Sorry! we don't have invoice item id no. " + invoiceItemId);
        }

        serviceLayer.updateInvoiceItem(invoiceItemViewModel);
    }


    @GetMapping("/invoices/{invoiceId}")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceItemViewModel> getInvoiceItemsByInvoiceId(@PathVariable("invoiceId") int invoiceId) {

        List<InvoiceItemViewModel> invoiceItemViewModel = serviceLayer.findAllInvoiceItemByInvoiceId(invoiceId);

        if (invoiceItemViewModel == null)

            throw new NotFoundException("Sorry! we don't have this customer id no. " + invoiceId);

        return invoiceItemViewModel;
    }

    @GetMapping("/inventory/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceItemViewModel> getInvoiceItemsByInventoryId(@PathVariable("inventoryId") int inventoryId) {

        List<InvoiceItemViewModel> invoiceItemViewModel = serviceLayer.findAllInvoiceItemByInvoiceId(inventoryId);

        if (invoiceItemViewModel == null)

            throw new NotFoundException("Sorry! we don't have this inventory id no. " + inventoryId);

        return invoiceItemViewModel;
    }

}
