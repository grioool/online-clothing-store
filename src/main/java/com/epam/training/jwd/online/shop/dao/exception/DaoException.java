package com.epam.training.jwd.online.shop.dao.exception;

/**
 * The exception can be thrown when execution sql query was failed
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class DaoException extends RuntimeException {
    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
