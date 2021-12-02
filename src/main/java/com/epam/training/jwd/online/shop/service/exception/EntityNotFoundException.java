package com.epam.training.jwd.online.shop.service.exception;

/**
 * The exception can be thrown when entity in database is not represented
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
