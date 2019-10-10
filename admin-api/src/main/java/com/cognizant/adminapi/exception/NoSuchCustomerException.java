package com.cognizant.adminapi.exception;

public class NoSuchCustomerException extends RuntimeException{
    public NoSuchCustomerException(Integer customerId) {
        super("There is no such customer with customer id " + customerId + ".");
    }
}

