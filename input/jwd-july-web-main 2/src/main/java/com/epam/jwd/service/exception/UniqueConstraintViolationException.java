package com.epam.jwd.service.exception;

public class UniqueConstraintViolationException extends RuntimeException {

    public UniqueConstraintViolationException(String message) {
        super(message);
    }
}
