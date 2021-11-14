package com.epam.training.jwd.online.shop.controller.listener;


import com.epam.training.jwd.online.shop.dao.entity.User;
import com.epam.training.jwd.online.shop.dao.entity.UserRole;
import com.epam.training.jwd.online.shop.service.dto.UserService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationLifecycleListener implements ServletContextListener {

    private final UserService service;

    public ApplicationLifecycleListener() {
        service = UserService.simple();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        service.create(new User(1,"admin", "olga", "grigorieva", "olga.a.grigorieva@gmail.com", "password", UserRole.ADMIN, false, "+37529" ));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        service.clear();
    }
}
