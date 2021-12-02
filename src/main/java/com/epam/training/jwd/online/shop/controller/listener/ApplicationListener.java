package com.epam.training.jwd.online.shop.controller.listener;


import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPool;
import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPoolImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

/**
 * The class provide initialization {@link ConnectionPool} on application start up
 * @author Olga Grigorieva
 * @version 1.0.0
 */

@WebListener
public class ApplicationListener implements ServletContextListener {
    private ConnectionPool connectionPool;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        connectionPool = ConnectionPoolImpl.getInstance();
        connectionPool.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        connectionPool.shutDown();
    }
}
