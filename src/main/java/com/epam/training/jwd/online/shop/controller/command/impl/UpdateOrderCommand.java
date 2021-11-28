package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.AdminCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.dao.entity.*;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UpdateOrderCommand implements Command, AdminCommand {
    private static final Logger logger = LogManager.getLogger(UpdateOrderCommand.class);
    private static final OrderServiceImpl orderService = OrderServiceImpl.getInstance();

    @Override
    public ResponseContext execute(RequestContext request) {
        Map<String, Object> requestMap = new HashMap<>();
        try {
            int orderId = Integer.parseInt(request.getRequestParameters().get(RequestConstant.ID));
            OrderStatus orderStatus = OrderStatus.valueOf(request.getRequestParameters().get(RequestConstant.SELECT));
            Optional<Order> orderOptional = orderService.findOrderById(orderId);
            if (orderOptional.isPresent()) {
                Order order = Order.builder()
                        .withId(orderOptional.get().getId())
                        .withPaymentMethod(orderOptional.get().getPaymentMethod())
                        .withOrderStatus(orderStatus)
                        .withProducts(orderOptional.get().getProducts())
                        .withDeliveryDate(orderOptional.get().getDeliveryDate())
                        .withOrderDate(orderOptional.get().getOrderDate())
                        .withDeliveryCountry(orderOptional.get().getDeliveryCountry())
                        .withUser(orderOptional.get().getUser())
                        .withDeliveryTown(orderOptional.get().getDeliveryTown())
                        .withOrderCost(orderOptional.get().getOrderCost())
                        .build();
                orderService.updateOrder(order);
                requestMap.put(RequestConstant.REDIRECT_COMMAND, CommandManager.TO_ORDERS.getCommandName());
                return new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());
            }
        } catch (ServiceException e) {
            logger.error("Failed update order", e);
        }
        return new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE), requestMap, new HashMap<>());
    }
}
