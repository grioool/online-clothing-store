package com.epam.training.jwd.online.shop.service.dto;

import com.epam.training.jwd.online.shop.dao.entity.Order;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface OrderServiceImpl {

    List<Order> findAllOrders() throws ServiceException;

    Optional<String> createOrder(Order order) throws ServiceException;

    Optional<Order> findOrderById(int orderId) throws ServiceException;

    List<Order> findAllOrdersByUserId(int userId) throws ServiceException;

    void updateOrder(Order order) throws ServiceException;

    void deleteProductFromOrders(int productId) throws ServiceException;

    void updateUserBalanceAndLoyaltyPoints(Order order) throws ServiceException;


}