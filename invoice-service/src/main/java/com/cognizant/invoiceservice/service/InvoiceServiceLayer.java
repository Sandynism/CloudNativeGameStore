package com.cognizant.invoiceservice.service;

import com.cognizant.invoiceservice.dao.InvoiceDao;
import com.cognizant.invoiceservice.dao.InvoiceItemDao;
import com.cognizant.invoiceservice.exception.QueueRequestTimeoutException;
import com.cognizant.invoiceservice.model.Invoice;
import com.cognizant.invoiceservice.viewModel.InvoiceItemViewModel;
import com.cognizant.invoiceservice.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


@Component @Primary
public class InvoiceServiceLayer {

    private InvoiceDao invoiceDao;
    private InvoiceItemServiceLayer invoiceItemServiceLayer;

    public static final Long TIMEOUT = 1L;
    public static final TimeUnit TIMEOUT_UNIT = TimeUnit.SECONDS;

    @Autowired
    public InvoiceServiceLayer(InvoiceDao invoiceDao, InvoiceItemServiceLayer invoiceItemServiceLayer) {
        this.invoiceDao = invoiceDao;
        this.invoiceItemServiceLayer = invoiceItemServiceLayer;
    }



    @Transactional
    public InvoiceViewModel saveInvoice(InvoiceViewModel invoiceViewModel) throws QueueRequestTimeoutException {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(invoiceViewModel.getCustomerId());
        invoice.setPurchaseDate(invoiceViewModel.getPurchaseDate());

        invoice = invoiceDao.addInvoice(invoice);


        if (invoiceViewModel.getInvoiceItems() != null) {

            List<CompletableFuture<InvoiceItemViewModel>> listCf = new ArrayList<>();


            if (!invoiceViewModel.getInvoiceItems().isEmpty()) {

                Invoice finalInvoice = invoice;

                invoiceViewModel.getInvoiceItems().stream().forEach(invoiceItemViewModel -> {


                    invoiceItemViewModel.setInvoiceId(finalInvoice.getInvoiceId());

                    CompletableFuture<InvoiceItemViewModel> cf = CompletableFuture.supplyAsync(System::nanoTime)
                            .thenApply(start -> {
                                InvoiceItemViewModel invoiceItemViewModel1 = invoiceItemServiceLayer.saveInvoiceItem(invoiceItemViewModel);
                                return invoiceItemViewModel1;
                            }).thenApply(iivm -> {
                                invoiceItemViewModel.setInvoiceItemId(iivm.getInvoiceItemId());
                                return invoiceItemViewModel;
                            });
                    listCf.add(cf);
                });
                CompletableFuture<Void> allFutures =
                        CompletableFuture.allOf(listCf.toArray(new CompletableFuture[listCf.size()]));
//                try {
//                    allFutures.get(TIMEOUT, TIMEOUT_UNIT);
//                } catch (InterruptedException | ExecutionException e) {
//                    throw new RuntimeException(e.getCause() + " | " + e.getMessage());
//                } catch (TimeoutException e) {
//                    throw new QueueRequestTimeoutException("The request timed out while waiting for fulfillment. " +
//                            "Your new book has been placed in a queue and will be added shortly.");
//                }
            }
        } else {
            throw new IllegalArgumentException("you can not issue an invoice without invoice items");
        }

        invoiceViewModel.setInvoiceId(invoice.getInvoiceId());

        return invoiceViewModel;
    }


    InvoiceViewModel findInvoice (Integer invoiceId) {

        Invoice invoice = invoiceDao.getInvoice(invoiceId);

        if(invoice == null)
            throw new NoSuchElementException(String.format("No invoice with id %s found", invoiceId));
        else
            return buildInvoiceViewModel(invoice);
    }

    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {

        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setInvoiceId(invoice.getInvoiceId());
        invoiceViewModel.setCustomerId(invoice.getCustomerId());
        invoiceViewModel.setPurchaseDate(invoice.getPurchaseDate());
        invoiceViewModel.setInvoiceItems(invoiceItemServiceLayer.findAllInvoiceItemByInvoiceId(invoice.getInvoiceId()));

        return invoiceViewModel;
    }





    }


