package com.cognizant.invoiceservice.service;

import com.cognizant.invoiceservice.dao.InvoiceDao;
import com.cognizant.invoiceservice.dao.InvoiceDaoJdbcTemplateImpl;
import com.cognizant.invoiceservice.dao.InvoiceItemDao;
import com.cognizant.invoiceservice.dao.InvoiceItemDaoJdbcTemplateImpl;
import com.cognizant.invoiceservice.exception.QueueRequestTimeoutException;
import com.cognizant.invoiceservice.model.Invoice;
import com.cognizant.invoiceservice.model.InvoiceItem;
import com.cognizant.invoiceservice.viewModel.InvoiceItemViewModel;
import com.cognizant.invoiceservice.viewModel.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

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

        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);

        doReturn(invoice).when(invoiceDao).addInvoice(invoice1);
        doReturn(invoice).when(invoiceDao).getInvoice(4);

        doReturn(invoiceList).when(invoiceDao).getAllInvoices();


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
        invoiceItemViewModel2.setInvoiceId(5);
        invoiceItemViewModel2.setInventoryId(10);
        invoiceItemViewModel2.setQuantity(50);
        invoiceItemViewModel2.setUnitPrice(new BigDecimal("9.50"));

        InvoiceItemViewModel invoiceItemViewModel3 = new InvoiceItemViewModel();
        invoiceItemViewModel3.setInvoiceId(5);
        invoiceItemViewModel3.setInventoryId(10);
        invoiceItemViewModel3.setQuantity(50);
        invoiceItemViewModel3.setUnitPrice(new BigDecimal("9.50"));


        List<InvoiceItemViewModel> invoiceItemViewModels = new ArrayList<>();
        invoiceItemViewModels.add(invoiceItemViewModel);


        doReturn(invoiceItemViewModel).when(invoiceItemServiceLayer).saveInvoiceItem(invoiceItemViewModel1);
        doReturn(invoiceItemViewModel).when(invoiceItemServiceLayer).findInvoiceItem(9);

        doReturn(invoiceItemViewModel2).when(invoiceItemServiceLayer).saveInvoiceItem(invoiceItemViewModel3);
        doReturn(invoiceItemViewModel2).when(invoiceItemServiceLayer).findInvoiceItem(7);

        doReturn(invoiceItemViewModels).when(invoiceItemServiceLayer).findAllInvoiceItemByInvoiceId(4);


    }


    @Test
    public void saveFindInvoice() throws QueueRequestTimeoutException {

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(3);
        invoice1.setPurchaseDate(LocalDate.of(2012, 12, 12));

        InvoiceItemViewModel invoiceItemViewModel = new InvoiceItemViewModel();
        invoiceItemViewModel.setInvoiceItemId(2);
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
}