package com.epam.training.jwd.online.shop.service.impl;

import com.epam.training.jwd.online.shop.dao.entity.Order;
import com.epam.training.jwd.online.shop.dao.entity.User;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.dao.field.OrderField;
import com.epam.training.jwd.online.shop.dao.impl.OrderDao;
import com.epam.training.jwd.online.shop.service.OrderService;
import com.epam.training.jwd.online.shop.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
    private static volatile OrderServiceImpl instance;
    private final UserService userService = UserServiceImpl.getInstance();
    private final OrderDao orderDao;

    private OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public static OrderServiceImpl getInstance() {
        OrderServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (OrderServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new OrderServiceImpl(OrderDao.INSTANCE);
                }
            }
        }
        return localInstance;
    }

    public List<Order> findAllOrders() {
        try {
            return orderDao.findAll();
        } catch (DaoException e) {
            logger.error("Failed to find all orders");
            throw new ServiceException(e);
        }
    }

    public Optional<String> saveOrder(Order order) {
        User orderUser = order.getUser();

        if (orderUser.isBlocked()) {
            return Optional.of("serverMessage.blockedAccount");
        }

        try {
            orderDao.save(order);
        } catch (DaoException e) {
            logger.error("Failed to create order");
            throw new ServiceException(e);
        }

        return Optional.empty();
    }

    public Optional<Order> findOrderById(Integer orderId) {
        try {
            return Optional.ofNullable(orderDao.findById(orderId));
        } catch (DaoException e) {
            logger.error("Failed on a order search");
            throw new ServiceException("Failed search order by id", e);
        }
    }

    public List<Order> findAllOrdersByUserId(Integer userId) {
        try {
            return orderDao.findByField(userId, OrderField.USER);
        } catch (DaoException e) {
            logger.error("Failed to find all orders by user id = " + userId);
            throw new ServiceException(e);
        }
    }

    public void updateOrder(Order order) {
        try {
            orderDao.update(order);
        } catch (DaoException e) {
            logger.error("Failed to update order");
            throw new ServiceException(e);
        }
    }

    public void deleteProductFromOrders(Integer productId) throws ServiceException {
        try {
            orderDao.deleteOrderProductByProductId(productId);
        } catch (DaoException e) {
            logger.error("Failed to delete product from orders");
            throw new ServiceException(e);
        }
    }
}
