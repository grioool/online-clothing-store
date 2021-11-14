package com.epam.jwd.controller.listener;

import com.epam.jwd.dao.model.Role;
import com.epam.jwd.dao.model.User;
import com.epam.jwd.service.UserService;

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
        service.create(new User("Alice", "Alice"));
        service.create(new User("Bob", "Bob"));
        service.create(new User("Martin", "Martin"));
        service.create(new User("Kate", "Kate"));
        service.create(new User("Lynn", "Lynn"));
        service.create(new User("Robert", "Robert"));
        service.create(new User("admin", "password", Role.ADMIN));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        service.clear();
    }
}
