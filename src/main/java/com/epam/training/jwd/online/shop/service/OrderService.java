package com.epam.training.jwd.online.shop.service;

import com.epam.training.jwd.online.shop.dao.entity.Order;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findAllOrders();

    Optional<String> saveOrder(Order order);

    Optional<Order> findOrderById(Integer orderId);

    List<Order> findAllOrdersByUserId(Integer userId);

    void updateOrder(Order order);

    void deleteProductFromOrders(Integer productId);

}