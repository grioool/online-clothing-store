package com.epam.training.jwd.online.shop.dao.exception;

/**
 * The exception can be thrown when entity in database is not represented
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
