package com.cognizant.invoiceservice.dao;

import com.cognizant.invoiceservice.model.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceItemDaoJdbcTemplateImpl implements InvoiceItemDao{

    private JdbcTemplate jdbcTemplate;

    // Prepared statement strings
    private static final String INSERT_INVOICE_ITEM_SQL =
            "insert into invoice_item (invoice_id, inventory_id, quantity,unit_price) values (?, ?, ?, ?)";

    private static final String SELECT_INVOICE_ITEM_SQL =
            "select * from invoice_item where invoice_item_id = ?";

    private static final String SELECT_ALL_INVOICE_ITEMS_SQL =
            "select * from invoice_item";

    private static final String DELETE_INVOICE_ITEM_SQL =
            "delete from invoice_item where invoice_item_id = ?";

    private static final String UPDATE_INVOICE_ITEM_SQL =
            "update invoice_item set invoice_id = ?, inventory_id = ?, quantity = ?, unit_price = ? where invoice_item_id = ?";

    private static final String SELECT_INVOICE_ITEM_BY_INVENTORY_ID_SQL =
            "select * from invoice_item where inventory_id = ?";

    private static final String SELECT_INVOICE_ITEM_BY_INVOICE_ID_SQL =
            "select * from invoice_item where invoice_id = ?";


    @Autowired
    public InvoiceItemDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public InvoiceItem getInvoiceItem(int invoiceItemId) {

        try {

            return jdbcTemplate.queryForObject(SELECT_INVOICE_ITEM_SQL, this::mapRowToInvoiceItem, invoiceItemId);

        } catch (EmptyResultDataAccessException e) {
            // if nothing is returned just catch the exception and return null
            return null;
        }
    }

    @Override
    @Transactional
    public InvoiceItem addInvoiceItem(InvoiceItem invoiceItem) {

       jdbcTemplate.update(INSERT_INVOICE_ITEM_SQL,
               invoiceItem.getInvoiceId(),
               invoiceItem.getInventoryId(),
               invoiceItem.getQuantity(),
               invoiceItem.getUnitPrice());

       int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);

       invoiceItem.setInvoiceItemId(id);

       return invoiceItem;
    }

    @Override
    public List<InvoiceItem> getAllInvoiceItems() {

        return jdbcTemplate.query(SELECT_ALL_INVOICE_ITEMS_SQL, this::mapRowToInvoiceItem);
    }

    @Override
    public List<InvoiceItem> getInvoiceItemsByInvoiceId(int invoiceId) {

        return jdbcTemplate.query(SELECT_INVOICE_ITEM_BY_INVOICE_ID_SQL, this::mapRowToInvoiceItem, invoiceId);
    }

    @Override
    public void updateInvoiceItem(InvoiceItem invoiceItem, int invoiceItemId) {

        jdbcTemplate.update(UPDATE_INVOICE_ITEM_SQL,
                invoiceItem.getInvoiceId(),
                invoiceItem.getInventoryId(),
                invoiceItem.getQuantity(),
                invoiceItem.getUnitPrice(),
                invoiceItem.getInvoiceItemId());

    }


    @Override
    public void deleteInvoiceItem(int invoiceItemId) {

        jdbcTemplate.update(DELETE_INVOICE_ITEM_SQL, invoiceItemId);

    }

    @Override
    public InvoiceItem getInvoiceItemByInventoryId(int inventoryId) {

        return jdbcTemplate.queryForObject(SELECT_INVOICE_ITEM_BY_INVENTORY_ID_SQL, this::mapRowToInvoiceItem, inventoryId);
    }


    private InvoiceItem mapRowToInvoiceItem(ResultSet rs, int rowNum) throws SQLException {

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(rs.getInt("invoice_item_id"));
        invoiceItem.setInvoiceId(rs.getInt("invoice_id"));
        invoiceItem.setInventoryId(rs.getInt("inventory_id"));
        invoiceItem.setQuantity(rs.getInt("quantity"));
        invoiceItem.setUnitPrice(rs.getBigDecimal("unit_price"));

        return invoiceItem;
    }
}
