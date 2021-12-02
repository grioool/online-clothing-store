package com.epam.training.jwd.online.shop.dao.connectionpool;

import java.sql.Connection;

/**
 * The interface provide pool withName connections to database
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public interface ConnectionPool {
    boolean init();

    boolean shutDown();

    Connection takeConnection() throws InterruptedException;

    void returnConnection(Connection connection);

}
