package com.cognizant.adminapi.exception;

public class NoSuchInvoiceException extends RuntimeException{
    public NoSuchInvoiceException(Integer invoiceId) {
        super("There is no such invoice with invoice id " + invoiceId + ".");
    }
}
