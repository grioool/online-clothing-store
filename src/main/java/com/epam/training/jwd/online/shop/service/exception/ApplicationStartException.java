package com.epam.training.jwd.online.shop.service.exception;

/**
 * The exception can be thrown when application start up is failed
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class ApplicationStartException extends RuntimeException {

    public ApplicationStartException(String message) {
        super(message);
    }
}
