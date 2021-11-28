package com.epam.training.jwd.online.shop.dao.impl;

import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPoolImpl;
import com.epam.training.jwd.online.shop.dao.entity.*;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.accessibility.AccessibleStateSet;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoTest {
    private static final String TEST_ORDER_USERNAME = "griol";
    private static OrderDao orderDao;
    private static Order.Builder orderBuilder;
    private static User user;

    @BeforeAll
    public static void beforeAll() throws DaoException {
        ConnectionPoolImpl.getInstance().init();
        Map<Product, Integer> products = new HashMap<>();
        products.put(ProductDao.INSTANCE.findByProductName("black_oe"), 41);
        orderDao = OrderDao.INSTANCE;
        user = UserDao.INSTANCE.findByUsername(TEST_ORDER_USERNAME);
        orderBuilder = Order.builder()
                .withOrderDate(LocalDateTime.ofEpochSecond(new Date().getTime(), 100, ZoneOffset.MAX))
                .withOrderStatus(OrderStatus.ACTIVE)
                .withDeliveryCountry(Country.BELARUS)
                .withDeliveryDate(LocalDateTime.ofEpochSecond(new Date().getTime(), 100, ZoneOffset.MAX))
                .withPaymentMethod(PaymentMethod.CARD)
                .withDeliveryTown(Town.MINSK)
                .withUser(user)
                .withProducts(products);
    }

    @Test
    void crudTest() throws DaoException {
        Order order = orderBuilder.build();
        orderDao.save(order);
        assertNotNull(user.getId());
        assertNotNull(orderDao.findByUser(user));
        assertTrue(orderDao.findById(order.getId()).getProducts().size() > 0);
        order.setOrderStatus(OrderStatus.COMPLETED);
        orderDao.update(order);
        assertEquals(OrderStatus.COMPLETED, orderDao.findById(order.getId()).getOrderStatus());
        orderDao.delete(order);
        assertNull(orderDao.findById(order.getId()));
    }
}