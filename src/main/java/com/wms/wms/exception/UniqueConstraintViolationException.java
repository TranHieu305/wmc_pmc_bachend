package com.wms.wms.exception;

public class UniqueConstraintViolationException extends RuntimeException{

    public UniqueConstraintViolationException(String message) {
        super(message);
    }
}
