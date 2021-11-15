package com.epam.training.jwd.online.shop.dao.impl;

import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPoolImpl;
import com.epam.training.jwd.online.shop.dao.entity.*;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.field.ProductField;
import com.epam.training.jwd.online.shop.dao.field.UserField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private static UserDao userDao;
    private static User.Builder user;
    private static List<User> users;

    @BeforeAll
    public static void beforeAll() {
        ConnectionPoolImpl.getInstance().init();
        userDao = UserDao.INSTANCE;
        user = User.builder()
                .withId(2)
                .withUsername("grioooddoool")
                .withPassword("888")
                .withFirstName("Olga")
                .withLastName("Grigorieva")
                .withEmail("kksk")
                .withIsBlocked(false)
                .withPhoneNumber("333")
                .withRole(UserRole.USER);
    }

    @Test
    void save() throws DaoException {
        userDao.save(user.build());
        users = userDao.findByField("grioooddoool", UserField.USERNAME);
        user.withId(users.get(0).getId());
        assertTrue(users.contains(user.build()));
    }

    @Test
    void update() throws DaoException {
        user.withUsername("grioooddoool");
        userDao.update(user.build());
        users = userDao.findByField("grioooddoool", UserField.USERNAME);
        user.withId(users.get(0).getId());
        assertTrue(users.contains(user.build()));
    }

    @Test
    void delete() throws DaoException {
        users = userDao.findByField("grioooddoool", UserField.USERNAME);
        userDao.delete(users.get(0).getId());
        assertTrue(users.contains(user.build()));
    }

    @Test
    void findUserById() {
    }
}