package com.cognizant.invoiceservice.exception;

public class QueueRequestTimeoutException extends Throwable {

    public QueueRequestTimeoutException() {
    }

    public QueueRequestTimeoutException(String message) {
        super(message);
    }

}
