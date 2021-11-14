package com.epam.training.jwd.online.shop.dao.connectionpool;

import java.sql.Connection;

public interface ConnectionPool {
    boolean init();

    boolean shutDown();

    Connection takeConnection() throws InterruptedException;

    void returnConnection(Connection connection);

}
