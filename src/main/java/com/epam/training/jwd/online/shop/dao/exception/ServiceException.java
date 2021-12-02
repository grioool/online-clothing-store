package com.epam.training.jwd.online.shop.dao.exception;

/**
 * The exception can be thrown when services catch {@link DaoException}
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
