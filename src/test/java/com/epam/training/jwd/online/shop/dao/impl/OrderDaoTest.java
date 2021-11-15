package com.epam.training.jwd.online.shop.dao.impl;

import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPoolImpl;
import com.epam.training.jwd.online.shop.dao.entity.*;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.field.OrderField;
import com.epam.training.jwd.online.shop.dao.field.UserField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoTest {
    private static OrderDao orderDao;
    private static Order.Builder order;
    private static List<Order> orders;

    @BeforeAll
    public static void beforeAll() {
        ConnectionPoolImpl.getInstance().init();
        orderDao = OrderDao.INSTANCE;
        Product shoes = new Product();
        Map<Product, Integer> products = new HashMap<>();
        products.put(shoes, 32);
        User user = new User();
        user = User.builder()
                .withId(2)
                .withUsername("grioooddoool")
                .withPassword("888")
                .withFirstName("Olga")
                .withLastName("Grigorieva")
                .withEmail("kksk")
                .withIsBlocked(false)
                .withPhoneNumber("333")
                .withRole(UserRole.USER)
                .build();
        order = Order.builder()
                .withId(2)
                .withPaymentMethod(PaymentMethod.CARD)
                .withOrderStatus(OrderStatus.ACTIVE)
                .withProducts(products)
                .withDeliveryDate(LocalDateTime.now())
                .withOrderDate(LocalDateTime.now())
                .withDeliveryCountry(Country.BELARUS)
                .withUser(user)
                .withDeliveryTown(Town.MINSK);

    }

    @Test
    void save() throws DaoException {
        orderDao.save(order.build());
        orders = orderDao.findByField("2", OrderField.ID);
        order.withId(orders.get(0).getId());
        assertTrue(orders.contains(order.build()));
    }

    @Test
    void update() throws DaoException {
        order.withId(2);
        orderDao.update(order.build());
        orders = orderDao.findByField("2", OrderField.ID);
        order.withId(orders.get(0).getId());
        assertTrue(orders.contains(order.build()));
    }

    @Test
    void delete() throws DaoException {
        orders = orderDao.findByField("2", OrderField.ID);
        orderDao.delete(orders.get(0).getId());
        assertTrue(orders.contains(order.build()));
    }

}