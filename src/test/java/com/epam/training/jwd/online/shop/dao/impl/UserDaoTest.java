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

    private static final String TEST_USER_USERNAME = "grioooddoool";
    private static UserDao userDao;
    private static User.Builder userBuilder;

    @BeforeAll
    public static void beforeAll() {
        ConnectionPoolImpl.getInstance().init();
        userDao = UserDao.INSTANCE;
        userBuilder = User.builder()
                .withUsername(TEST_USER_USERNAME)
                .withPassword("888")
                .withFirstName("Olga")
                .withLastName("Grigorieva")
                .withEmail("kksk")
                .withIsBlocked(false)
                .withPhoneNumber("333")
                .withRole(UserRole.USER);
    }

    @Test
    void crudTest() throws DaoException {
        User user = userBuilder.build();
        userDao.save(user);
        assertNotNull(user.getId());
        assertNotNull(userDao.findByUsername(TEST_USER_USERNAME));
        user.setFirstName("Vasya");
        userDao.update(user);
        assertEquals("Vasya", userDao.findByUsername(TEST_USER_USERNAME).getFirstName());
        userDao.delete(user);
        assertNull(userDao.findByUsername(TEST_USER_USERNAME));
    }
}