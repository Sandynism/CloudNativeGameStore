package com.cognizant.invoiceservice.service;

import com.cognizant.invoiceservice.dao.InvoiceItemDao;
import com.cognizant.invoiceservice.dao.InvoiceItemDaoJdbcTemplateImpl;
import com.cognizant.invoiceservice.model.InvoiceItem;
import com.cognizant.invoiceservice.viewModel.InvoiceItemViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class InvoiceItemServiceLayerTest {

    InvoiceItemDao invoiceItemDao;
    InvoiceItemServiceLayer invoiceItemServiceLayer;


    @Before
    public void setUp() throws Exception {

        setUpInvoiceItemDaoMock();

        invoiceItemServiceLayer = new InvoiceItemServiceLayer(invoiceItemDao);
    }


    private void setUpInvoiceItemDaoMock() {

        invoiceItemDao = mock(InvoiceItemDaoJdbcTemplateImpl.class);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(9);
        invoiceItem.setInvoiceId(7);
        invoiceItem.setInventoryId(12);
        invoiceItem.setQuantity(20);
        invoiceItem.setUnitPrice(new BigDecimal("11.01"));

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(7);
        invoiceItem1.setInventoryId(12);
        invoiceItem1.setQuantity(20);
        invoiceItem1.setUnitPrice(new BigDecimal("11.01"));

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceItemId(2);
        invoiceItem2.setInvoiceId(5);
        invoiceItem2.setInventoryId(10);
        invoiceItem2.setQuantity(50);
        invoiceItem2.setUnitPrice(new BigDecimal("9.50"));

        InvoiceItem invoiceItem3 = new InvoiceItem();
        invoiceItem3.setInvoiceId(5);
        invoiceItem3.setInventoryId(10);
        invoiceItem3.setQuantity(50);
        invoiceItem3.setUnitPrice(new BigDecimal("9.50"));

        List<InvoiceItem> invoiceItems = new ArrayList<>();
        invoiceItems.add(invoiceItem);
        invoiceItems.add(invoiceItem2);

        List<InvoiceItem> invoiceItems1 = new ArrayList<>();
        invoiceItems1.add(invoiceItem2);

        doReturn(invoiceItem).when(invoiceItemDao).addInvoiceItem(invoiceItem1);
        doReturn(invoiceItem).when(invoiceItemDao).getInvoiceItem(9);

        doReturn(invoiceItem2).when(invoiceItemDao).addInvoiceItem(invoiceItem3);
        doReturn(invoiceItem2).when(invoiceItemDao).getInvoiceItem(2);

        doNothing().when(invoiceItemDao).updateInvoiceItem(invoiceItem, 2);
        doReturn(invoiceItem2).when(invoiceItemDao).getInvoiceItem(2);


        doNothing().when(invoiceItemDao).deleteInvoiceItem(11);
        doReturn(null).when(invoiceItemDao).getInvoiceItem(11);

        doReturn(invoiceItems).when(invoiceItemDao).getAllInvoiceItems();

        doReturn(invoiceItems1).when(invoiceItemDao).getInvoiceItemsByInvoiceId(5);

        doReturn(invoiceItem).when(invoiceItemDao).getInvoiceItemByInventoryId(12);

    }

    @Test
    public void saveFindInvoiceItem() {

        InvoiceItemViewModel invoiceItemViewModel = new InvoiceItemViewModel();
        invoiceItemViewModel.setInvoiceId(7);
        invoiceItemViewModel.setInventoryId(12);
        invoiceItemViewModel.setQuantity(20);
        invoiceItemViewModel.setUnitPrice(new BigDecimal("11.01"));

        invoiceItemViewModel = invoiceItemServiceLayer.saveInvoiceItem(invoiceItemViewModel);

        InvoiceItemViewModel fromService = invoiceItemServiceLayer.findInvoiceItem(invoiceItemViewModel.getInvoiceItemId());

        assertEquals(invoiceItemViewModel, fromService);

    }


    @Test
    public void updateInvoiceItem() {

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(9);
        invoiceItem.setInvoiceId(7);
        invoiceItem.setInventoryId(12);
        invoiceItem.setQuantity(20);
        invoiceItem.setUnitPrice(new BigDecimal("11.01"));

        InvoiceItemViewModel invoiceItemViewModel = new InvoiceItemViewModel();
        invoiceItemViewModel.setInvoiceItemId(invoiceItem.getInvoiceItemId());
        invoiceItemViewModel.setInvoiceId(invoiceItem.getInvoiceId());
        invoiceItemViewModel.setInventoryId(invoiceItem.getInventoryId());
        invoiceItemViewModel.setQuantity(invoiceItem.getQuantity());
        invoiceItemViewModel.setUnitPrice(invoiceItem.getUnitPrice());

        invoiceItemServiceLayer.updateInvoiceItem(invoiceItemViewModel);

        InvoiceItemViewModel fromService = invoiceItemServiceLayer.findInvoiceItem(invoiceItemViewModel.getInvoiceItemId());

        assertEquals(invoiceItemViewModel, fromService);
    }

    @Test(expected = NoSuchElementException.class)
    public void removeInvoiceItem() {

        invoiceItemServiceLayer.removeInvoiceItem(11);

        InvoiceItemViewModel invoiceItemViewModel = invoiceItemServiceLayer.findInvoiceItem(11);

    }

    @Test
    public void findAllInvoiceItems() {

        InvoiceItemViewModel invoiceItemViewModel = new InvoiceItemViewModel();
        invoiceItemViewModel.setInvoiceId(7);
        invoiceItemViewModel.setInventoryId(12);
        invoiceItemViewModel.setQuantity(20);
        invoiceItemViewModel.setUnitPrice(new BigDecimal("11.01"));

        invoiceItemViewModel = invoiceItemServiceLayer.saveInvoiceItem(invoiceItemViewModel);

        InvoiceItemViewModel invoiceItemViewModel1 = new InvoiceItemViewModel();
        invoiceItemViewModel1.setInvoiceId(5);
        invoiceItemViewModel1.setInventoryId(10);
        invoiceItemViewModel1.setQuantity(50);
        invoiceItemViewModel1.setUnitPrice(new BigDecimal("9.50"));

        invoiceItemViewModel1 = invoiceItemServiceLayer.saveInvoiceItem(invoiceItemViewModel1);

        List<InvoiceItemViewModel> invoiceItemViewModels = invoiceItemServiceLayer.findAllInvoiceItems();

        assertEquals(2, invoiceItemViewModels.size());

    }

    @Test
    public void findAllInvoiceItemByInvoiceId() {

        InvoiceItemViewModel invoiceItemViewModel = new InvoiceItemViewModel();
        invoiceItemViewModel.setInvoiceId(5);
        invoiceItemViewModel.setInventoryId(10);
        invoiceItemViewModel.setQuantity(50);
        invoiceItemViewModel.setUnitPrice(new BigDecimal("9.50"));

        invoiceItemViewModel = invoiceItemServiceLayer.saveInvoiceItem(invoiceItemViewModel);


        List<InvoiceItemViewModel> invoiceItemViewModels = invoiceItemServiceLayer.findAllInvoiceItemByInvoiceId(invoiceItemViewModel.getInvoiceId());

        assertEquals(1, invoiceItemViewModels.size());
    }

    @Test
    public void findInvoiceItemByInventoryId() {

        InvoiceItemViewModel invoiceItemViewModel = new InvoiceItemViewModel();
        invoiceItemViewModel.setInvoiceId(7);
        invoiceItemViewModel.setInventoryId(12);
        invoiceItemViewModel.setQuantity(20);
        invoiceItemViewModel.setUnitPrice(new BigDecimal("11.01"));

        invoiceItemViewModel = invoiceItemServiceLayer.saveInvoiceItem(invoiceItemViewModel);

        InvoiceItemViewModel fromService = invoiceItemServiceLayer.findInvoiceItemByInventoryId(invoiceItemViewModel.getInventoryId());

        assertEquals(invoiceItemViewModel, fromService);
    }
}