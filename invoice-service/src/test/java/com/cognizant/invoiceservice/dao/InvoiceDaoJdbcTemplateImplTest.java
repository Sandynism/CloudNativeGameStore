package com.cognizant.invoiceservice.dao;

import com.cognizant.invoiceservice.model.Invoice;
import com.cognizant.invoiceservice.model.InvoiceItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceDaoJdbcTemplateImplTest {

    @Autowired
    InvoiceDao invoiceDao;
    @Autowired
    InvoiceItemDao invoiceItemDao;

    @Before
    public void setUp() throws Exception {
        List<InvoiceItem> invoiceItems = invoiceItemDao.getAllInvoiceItems();

        invoiceItems.forEach(invoiceItem -> invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId()));

        List<Invoice> invoices = invoiceDao.getAllInvoices();

        invoices.forEach(invoice -> invoiceDao.deleteInvoice(invoice.getInvoiceId()));

    }


    @Test
    public void addGetDeleteInvoice() {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(3);
        invoice.setPurchaseDate(LocalDate.of(2012, 12, 12));

        invoice = invoiceDao.addInvoice(invoice);

        Invoice fromDao = invoiceDao.getInvoice(invoice.getInvoiceId());

        assertEquals(invoice, fromDao);

        invoiceDao.deleteInvoice(invoice.getInvoiceId());

        fromDao = invoiceDao.getInvoice(invoice.getInvoiceId());

        assertNull(fromDao);

    }


    @Test
    public void updateInvoice() {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(3);
        invoice.setPurchaseDate(LocalDate.of(2012, 12, 12));

        invoice = invoiceDao.addInvoice(invoice);

        invoice.setPurchaseDate(LocalDate.of(2011, 11, 11));

        invoiceDao.updateInvoice(invoice, invoice.getInvoiceId());

        Invoice fromDao = invoiceDao.getInvoice(invoice.getInvoiceId());

        assertEquals(invoice, fromDao);
    }


    @Test
    public void getAllInvoices() {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(3);
        invoice.setPurchaseDate(LocalDate.of(2012, 12, 12));

        invoice = invoiceDao.addInvoice(invoice);

        invoice = new Invoice();
        invoice.setCustomerId(7);
        invoice.setPurchaseDate(LocalDate.of(2011, 11, 11));

        invoice = invoiceDao.addInvoice(invoice);

        List<Invoice> invoices = invoiceDao.getAllInvoices();

        assertEquals(2, invoices.size());
    }


    @Test
    public void getInvoiceByCustomerId() {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(3);
        invoice.setPurchaseDate(LocalDate.of(2012, 12, 12));

        invoice = invoiceDao.addInvoice(invoice);

        invoice = new Invoice();
        invoice.setCustomerId(3);
        invoice.setPurchaseDate(LocalDate.of(2011, 11, 11));

        invoice = invoiceDao.addInvoice(invoice);

        List<Invoice> invoices = invoiceDao.getInvoiceByCustomerId(3);

        assertEquals(2, invoices.size());
    }

    }