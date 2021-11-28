package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.AdminCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.dao.entity.Order;
import com.epam.training.jwd.online.shop.dao.entity.OrderStatus;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.dto.OrderServiceImpl;
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
//                Order order = Order.builder()
//                        .withId(orderOptional.get().getId())
//                        .withUser(orderOptional.get().getUser())
//                        .withProducts(orderOptional.get().getProducts())
//                        .withDeliveryDate(orderOptional.get().getDeliveryDate())
//                        .withDeliveryAddress(orderOptional.get().getDeliveryAddress())
//                        .withCreateDate(orderOptional.get().getCreateDate())
//                        .withPaymentMethod(orderOptional.get().getPaymentMethod())
//                        .withOrderStatus(orderStatus)
//                        .withCost(orderOptional.get().getCost())
//                        .build();
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
