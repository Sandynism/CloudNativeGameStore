package com.cognizant.invoiceservice.dao;

import com.cognizant.invoiceservice.model.Invoice;
import com.cognizant.invoiceservice.model.InvoiceItem;
import com.netflix.discovery.converters.Auto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceItemDaoJdbcTemplateImplTest {


    @Autowired
    InvoiceItemDao invoiceItemDao;

    @Autowired
    InvoiceDao invoiceDao;

    @Before
    public void setUp() throws Exception {

        List<InvoiceItem> invoiceItems = invoiceItemDao.getAllInvoiceItems();

        invoiceItems.forEach(invoiceItem -> invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId()));

        List<Invoice> invoices = invoiceDao.getAllInvoices();

        invoices.forEach(invoice -> invoiceDao.deleteInvoice(invoice.getInvoiceId()));

    }

    @Test
    public void addGetDeleteInvoiceItem() {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(3);
        invoice.setPurchaseDate(LocalDate.of(2012, 12, 12));

        invoice = invoiceDao.addInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(12);
        invoiceItem.setQuantity(20);
        invoiceItem.setUnitPrice(new BigDecimal("11.01"));

        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        InvoiceItem fromDao = invoiceItemDao.getInvoiceItem(invoiceItem.getInvoiceItemId());

        assertEquals(invoiceItem, fromDao);

        invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId());

        fromDao = invoiceItemDao.getInvoiceItem(invoiceItem.getInvoiceItemId());

        assertNull(fromDao);


    }


    @Test
    public void updateInvoiceItem() {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(3);
        invoice.setPurchaseDate(LocalDate.of(2012, 12, 12));

        invoice = invoiceDao.addInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(12);
        invoiceItem.setQuantity(20);
        invoiceItem.setUnitPrice(new BigDecimal("11.01"));

        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        invoiceItem.setInventoryId(12);
        invoiceItem.setQuantity(20);

        invoiceItemDao.updateInvoiceItem(invoiceItem, invoiceItem.getInvoiceItemId());

        InvoiceItem fromDao = invoiceItemDao.getInvoiceItem(invoiceItem.getInvoiceItemId());

        assertEquals(invoiceItem, fromDao);

    }


    @Test
    public void getAllInvoiceItems() {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(3);
        invoice.setPurchaseDate(LocalDate.of(2012, 12, 12));

        invoice = invoiceDao.addInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(12);
        invoiceItem.setQuantity(20);
        invoiceItem.setUnitPrice(new BigDecimal("11.01"));

        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(10);
        invoiceItem.setQuantity(50);
        invoiceItem.setUnitPrice(new BigDecimal("9.50"));

        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        List<InvoiceItem> invoiceItemList = invoiceItemDao.getAllInvoiceItems();

        assertEquals(2, invoiceItemList.size());

    }

    @Test
    public void getInvoiceItemByInventoryId() {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(3);
        invoice.setPurchaseDate(LocalDate.of(2012, 12, 12));

        invoice = invoiceDao.addInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(12);
        invoiceItem.setQuantity(20);
        invoiceItem.setUnitPrice(new BigDecimal("11.01"));

        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        InvoiceItem fromDao = invoiceItemDao.getInvoiceItemByInventoryId(invoiceItem.getInventoryId());

        assertEquals(invoiceItem, fromDao);

    }

    @Test
    public void getInvoiceItemsByInvoiceId() {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(3);
        invoice.setPurchaseDate(LocalDate.of(2012, 12, 12));

        invoice = invoiceDao.addInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(12);
        invoiceItem.setQuantity(20);
        invoiceItem.setUnitPrice(new BigDecimal("11.01"));

        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(10);
        invoiceItem.setQuantity(50);
        invoiceItem.setUnitPrice(new BigDecimal("9.50"));

        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        List<InvoiceItem> invoiceItemList = invoiceItemDao.getInvoiceItemsByInvoiceId(invoiceItem.getInvoiceId());

        assertEquals(2, invoiceItemList.size());


    }


    @Test
    public void deleteInvoiceItemByInvoiceId(){

        Invoice invoice = new Invoice();
        invoice.setCustomerId(3);
        invoice.setPurchaseDate(LocalDate.of(2012, 12, 12));

        invoice = invoiceDao.addInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(12);
        invoiceItem.setQuantity(20);
        invoiceItem.setUnitPrice(new BigDecimal("11.01"));

        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        invoiceItemDao.deleteByInvoiceId(invoiceItem.getInvoiceId());

        List<InvoiceItem> fromDao = invoiceItemDao.getInvoiceItemsByInvoiceId(invoiceItem.getInvoiceItemId());

        assertEquals(0, fromDao.size());

    }




}