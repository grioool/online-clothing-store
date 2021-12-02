package com.epam.training.jwd.online.shop.dao.connectionpool;

import com.epam.training.jwd.online.shop.dao.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * The class provide pool withName connections to database
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public final class ConnectionPoolImpl implements ConnectionPool {

    private final Logger logger = LogManager.getLogger(ConnectionPoolImpl.class);
    public static final ConnectionPool INSTANCE = new ConnectionPoolImpl();
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "890opklnm";
    private static final int INITIAL_POOL_SIZE = 32;


    private final BlockingQueue<ProxyConnection> availableConnections;
    private final Queue<ProxyConnection> givenAwayConnections;

    private boolean initialized = false;

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    public ConnectionPoolImpl() {
        availableConnections = new LinkedBlockingDeque<>(INITIAL_POOL_SIZE);
        givenAwayConnections = new ArrayDeque<>();
        init();
    }

    @Override
    public synchronized boolean init() {
        try {
            if(!initialized) {
                initializeConnections();
                initialized = true;
                return true;
            }
        } catch (ConnectionPoolException e) {
            logger.error("Can't initialize connections.");
        }
        return false;
    }

    private void initializeConnections() throws ConnectionPoolException {
        try {
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                final Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                final ProxyConnection proxyConnection = new ProxyConnection(connection, this);
                availableConnections.add(proxyConnection);
            }
        }catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }
    }

    @Override
    public synchronized boolean shutDown() {
        if(initialized) {
            closeConnections();
            initialized = false;
            return true;
        }
        return false;
    }

    @Override
    public synchronized Connection takeConnection() throws InterruptedException {
        while(availableConnections.isEmpty()) {
            this.wait();
        }

        final ProxyConnection connection = availableConnections.poll();
        givenAwayConnections.add(connection);
        return connection;
    }

    @Override
    public synchronized void returnConnection(Connection connection) {
        if(givenAwayConnections.remove((ProxyConnection) connection)) {
            availableConnections.add((ProxyConnection) connection);
            this.notifyAll();
        } else {
            logger.error("Can't return connections. ");
        }
    }

    private void closeConnections() {
        closeConnections(this.availableConnections);
        closeConnections(this.givenAwayConnections);
    }

    private void closeConnections(Collection<ProxyConnection> connections) {
        for (ProxyConnection connection : connections) {
            closeConnection(connection);
        }
    }

    private void closeConnection(ProxyConnection connection) {
        try {
            connection.realClose();
        } catch (SQLException throwables) {
            logger.error("Can't close connection. ");
        }
    }
}
