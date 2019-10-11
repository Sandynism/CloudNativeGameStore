package com.cognizant.invoiceservice.service;

import com.cognizant.invoiceservice.dao.InvoiceItemDao;
import com.cognizant.invoiceservice.model.InvoiceItem;
import com.cognizant.invoiceservice.viewModel.InvoiceItemViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class InvoiceItemServiceLayer {

    private InvoiceItemDao invoiceItemDao;

    @Autowired
    public InvoiceItemServiceLayer(InvoiceItemDao invoiceItemDao) {
        this.invoiceItemDao = invoiceItemDao;
    }

    @Transactional
     InvoiceItemViewModel saveInvoiceItem(InvoiceItemViewModel invoiceItemViewModel){

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoiceItemViewModel.getInvoiceId());
        invoiceItem.setInventoryId(invoiceItemViewModel.getInventoryId());
        invoiceItem.setQuantity(invoiceItemViewModel.getQuantity());
        invoiceItem.setUnitPrice(invoiceItemViewModel.getUnitPrice());

        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        invoiceItemViewModel.setInvoiceItemId(invoiceItem.getInvoiceItemId());

        return invoiceItemViewModel;
    }

    InvoiceItemViewModel findInvoiceItem(int invoiceItemId){

        InvoiceItem invoiceItem = invoiceItemDao.getInvoiceItem(invoiceItemId);

        if(invoiceItem == null) throw new NoSuchElementException(String.format("no invoice item with id %s found", invoiceItemId));

        else
            return buildInvoiceItemViewModel(invoiceItem);


    }

    public void updateInvoiceItem (InvoiceItemViewModel invoiceItemViewModel){

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(invoiceItemViewModel.getInvoiceItemId());
        invoiceItem.setInvoiceId(invoiceItemViewModel.getInvoiceId());
        invoiceItem.setInventoryId(invoiceItemViewModel.getInventoryId());
        invoiceItem.setQuantity(invoiceItemViewModel.getQuantity());
        invoiceItem.setUnitPrice(invoiceItemViewModel.getUnitPrice());

        invoiceItemDao.updateInvoiceItem(invoiceItem, invoiceItem.getInvoiceItemId());

    }

    public void removeInvoiceItem (int invoiceItemId){

        InvoiceItem invoiceItem = invoiceItemDao.getInvoiceItem(invoiceItemId);

        if(invoiceItem == null) throw new NoSuchElementException(String.format("no Level up with id %s found", invoiceItemId));

        invoiceItemDao.deleteInvoiceItem(invoiceItemId);

    }

    public List<InvoiceItemViewModel> findAllInvoiceItems(){

        List<InvoiceItem> invoiceItems = invoiceItemDao.getAllInvoiceItems();

        List<InvoiceItemViewModel> invoiceItemViewModels = new ArrayList<>();

        for(InvoiceItem i: invoiceItems){

            invoiceItemViewModels.add(buildInvoiceItemViewModel(i));

        }

        return invoiceItemViewModels;

    }

    public List<InvoiceItemViewModel> findAllInvoiceItemByInvoiceId(int invoiceId){

        List<InvoiceItem> invoiceItems = invoiceItemDao.getInvoiceItemsByInvoiceId(invoiceId);

        List<InvoiceItemViewModel> invoiceItemViewModels = new ArrayList<>();

        for(InvoiceItem i: invoiceItems){

            invoiceItemViewModels.add(buildInvoiceItemViewModel(i));

        }

        return invoiceItemViewModels;

    }

    InvoiceItemViewModel findInvoiceItemByInventoryId(int inventoryId){

        InvoiceItem invoiceItem = invoiceItemDao.getInvoiceItemByInventoryId(inventoryId);

        if(invoiceItem == null) throw new NoSuchElementException(String.format("no invoice item with this inventory id %s found", inventoryId));

        else
            return buildInvoiceItemViewModel(invoiceItem);


    }

    @Transactional
    private InvoiceItemViewModel buildInvoiceItemViewModel (InvoiceItem invoiceItem){

        InvoiceItemViewModel invoiceItemViewModel = new InvoiceItemViewModel();
        invoiceItemViewModel.setInvoiceItemId(invoiceItem.getInvoiceItemId());
        invoiceItemViewModel.setInvoiceId(invoiceItem.getInvoiceId());
        invoiceItemViewModel.setInventoryId(invoiceItem.getInventoryId());
        invoiceItemViewModel.setQuantity(invoiceItem.getQuantity());
        invoiceItemViewModel.setUnitPrice(invoiceItem.getUnitPrice());

        return invoiceItemViewModel;

    }

}
