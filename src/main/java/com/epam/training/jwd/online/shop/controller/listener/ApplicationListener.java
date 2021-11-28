package com.epam.training.jwd.online.shop.controller.listener;


import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPool;
import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPoolImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ApplicationListener implements ServletContextListener {
    private final Logger LOGGER = LogManager.getLogger(ApplicationListener.class);
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
