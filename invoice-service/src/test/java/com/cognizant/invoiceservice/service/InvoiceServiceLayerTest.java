package com.cognizant.invoiceservice.service;

import com.cognizant.invoiceservice.dao.InvoiceDao;
import com.cognizant.invoiceservice.dao.InvoiceDaoJdbcTemplateImpl;
import com.cognizant.invoiceservice.exception.QueueRequestTimeoutException;
import com.cognizant.invoiceservice.model.Invoice;

import com.cognizant.invoiceservice.viewModel.InvoiceItemViewModel;
import com.cognizant.invoiceservice.viewModel.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class InvoiceServiceLayerTest {

    InvoiceServiceLayer invoiceServiceLayer;
    InvoiceDao invoiceDao;
    InvoiceItemServiceLayer invoiceItemServiceLayer;

    @Before
    public void setUp() throws Exception {

        setUpInvoiceDaoMock();
        setUpInvoiceItemServiceLayerMock();

        invoiceServiceLayer = new InvoiceServiceLayer(invoiceDao, invoiceItemServiceLayer);

    }


    private void setUpInvoiceDaoMock() {

        invoiceDao = mock(InvoiceDaoJdbcTemplateImpl.class);

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(4);
        invoice.setCustomerId(3);
        invoice.setPurchaseDate(LocalDate.of(2012, 12, 12));

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(3);
        invoice1.setPurchaseDate(LocalDate.of(2012, 12, 12));

        Invoice invoice2 = new Invoice();
        invoice2.setInvoiceId(7);
        invoice2.setCustomerId(5);
        invoice2.setPurchaseDate(LocalDate.of(2011, 11, 11));


        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);

        doReturn(invoice).when(invoiceDao).addInvoice(invoice1);
        doReturn(invoice).when(invoiceDao).getInvoice(4);

        doNothing().when(invoiceDao).updateInvoice(invoice2, invoice2.getInvoiceId());
        doReturn(invoice2).when(invoiceDao).getInvoice(7);

        doReturn(invoiceList).when(invoiceDao).getAllInvoices();

        doNothing().when(invoiceDao).deleteInvoice(1);
        doReturn(null).when(invoiceDao).getInvoice(1);


    }


    private void setUpInvoiceItemServiceLayerMock(){

        invoiceItemServiceLayer = mock(InvoiceItemServiceLayer.class);

        InvoiceItemViewModel invoiceItemViewModel = new InvoiceItemViewModel();
        invoiceItemViewModel.setInvoiceItemId(2);
        invoiceItemViewModel.setInvoiceId(4);
        invoiceItemViewModel.setInventoryId(12);
        invoiceItemViewModel.setQuantity(20);
        invoiceItemViewModel.setUnitPrice(new BigDecimal("11.01"));

        InvoiceItemViewModel invoiceItemViewModel1 = new InvoiceItemViewModel();
        invoiceItemViewModel1.setInvoiceId(4);
        invoiceItemViewModel1.setInventoryId(12);
        invoiceItemViewModel1.setQuantity(20);
        invoiceItemViewModel1.setUnitPrice(new BigDecimal("11.01"));

        InvoiceItemViewModel invoiceItemViewModel2 = new InvoiceItemViewModel();
        invoiceItemViewModel2.setInvoiceItemId(7);
        invoiceItemViewModel2.setInvoiceId(7);
        invoiceItemViewModel2.setInventoryId(10);
        invoiceItemViewModel2.setQuantity(50);
        invoiceItemViewModel2.setUnitPrice(new BigDecimal("9.50"));

        InvoiceItemViewModel invoiceItemViewModel3 = new InvoiceItemViewModel();
        invoiceItemViewModel3.setInvoiceId(7);
        invoiceItemViewModel3.setInventoryId(10);
        invoiceItemViewModel3.setQuantity(50);
        invoiceItemViewModel3.setUnitPrice(new BigDecimal("9.50"));


        List<InvoiceItemViewModel> invoiceItemViewModels = new ArrayList<>();
        invoiceItemViewModels.add(invoiceItemViewModel);

        List<InvoiceItemViewModel> invoiceItemViewModelList = new ArrayList<>();
        invoiceItemViewModelList.add(invoiceItemViewModel2);

        doReturn(invoiceItemViewModel).when(invoiceItemServiceLayer).saveInvoiceItem(invoiceItemViewModel1);
        doReturn(invoiceItemViewModel).when(invoiceItemServiceLayer).findInvoiceItem(2);
        doReturn(invoiceItemViewModels).when(invoiceItemServiceLayer).findAllInvoiceItemByInvoiceId(4);

        doReturn(invoiceItemViewModel2).when(invoiceItemServiceLayer).saveInvoiceItem(invoiceItemViewModel3);
        doReturn(invoiceItemViewModel2).when(invoiceItemServiceLayer).findInvoiceItem(7);
        doReturn(invoiceItemViewModelList).when(invoiceItemServiceLayer).findAllInvoiceItemByInvoiceId(7);


        doNothing().when(invoiceItemServiceLayer).updateInvoiceItem(invoiceItemViewModel2);
        doReturn(invoiceItemViewModel2).when(invoiceItemServiceLayer).findInvoiceItem(7);

    }


    @Test
    public void saveFindInvoice() throws QueueRequestTimeoutException {

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(3);
        invoice1.setPurchaseDate(LocalDate.of(2012, 12, 12));

        InvoiceItemViewModel invoiceItemViewModel = new InvoiceItemViewModel();
        invoiceItemViewModel.setInvoiceId(4);
        invoiceItemViewModel.setInventoryId(12);
        invoiceItemViewModel.setQuantity(20);
        invoiceItemViewModel.setUnitPrice(new BigDecimal("11.01"));

        List<InvoiceItemViewModel> invoiceItemViewModels = new ArrayList<>();
        invoiceItemViewModels.add(invoiceItemViewModel);

        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setCustomerId(3);
        invoiceViewModel.setPurchaseDate(LocalDate.of(2012, 12, 12));
        invoiceViewModel.setInvoiceItems(invoiceItemViewModels);

        invoiceViewModel = invoiceServiceLayer.saveInvoice(invoiceViewModel);

        InvoiceViewModel fromService = invoiceServiceLayer.findInvoice(invoiceViewModel.getInvoiceId());

        assertEquals(invoiceViewModel, fromService);


    }

    @Test
    public void updateInvoice(){

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(7);
        invoice.setCustomerId(5);
        invoice.setPurchaseDate(LocalDate.of(2011, 11, 11));

        InvoiceItemViewModel invoiceItemViewModel = new InvoiceItemViewModel();
        invoiceItemViewModel.setInvoiceId(7);
        invoiceItemViewModel.setInventoryId(10);
        invoiceItemViewModel.setQuantity(50);
        invoiceItemViewModel.setUnitPrice(new BigDecimal("9.50"));

        List<InvoiceItemViewModel> invoiceItemViewModels = new ArrayList<>();
        invoiceItemViewModels.add(invoiceItemViewModel);

        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setInvoiceId(invoice.getInvoiceId());
        invoiceViewModel.setCustomerId(invoice.getCustomerId());
        invoiceViewModel.setPurchaseDate(invoice.getPurchaseDate());
        invoiceViewModel.setInvoiceItems(invoiceItemViewModels);

        invoiceServiceLayer.updateInvoice(invoiceViewModel,invoiceViewModel.getInvoiceId());

        InvoiceViewModel fromService = invoiceServiceLayer.findInvoice(invoiceViewModel.getInvoiceId());


        assertEquals(invoiceViewModel, fromService);

    }

    @Test(expected = NoSuchElementException.class)
    public void removeInvoice(){

        invoiceServiceLayer.removeInvoice(1);

        InvoiceViewModel invoiceViewModel = invoiceServiceLayer.findInvoice(1);

    }


    @Test
    public void findAllInvoices(){







    }

}