package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.ForwardResponseType;
import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.command.ResponseContext;
import com.epam.training.jwd.online.shop.controller.command.marker.UserCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.dao.entity.Order;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.dto.OrderServiceImpl;
import com.epam.training.jwd.online.shop.service.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToMyOrdersCommand implements Command, UserCommand {
    private static final Logger logger = LogManager.getLogger(ToMyOrdersCommand.class);
    private static final OrderServiceImpl orderService = OrderServiceImpl.getInstance();

    @Override
    public ResponseContext execute(RequestContext request) {
        ResponseContext responseContext;
        Map<String, Object> requestMap = new HashMap<>();

        UserDto userDto = (UserDto) request.getSessionAttributes().get(RequestConstant.USER);
        int userId = userDto.getId();
        try {
            int page = Integer.parseInt(request.getRequestParameters().get(RequestConstant.CURRENT_PAGE));
            List<Order> orders = orderService.findAllOrdersByUserId(userId);

            if (orders.size() > 0) {
                PaginationContext<Order> paginationContext = new PaginationContext<>(orders, page);
                requestMap.put(RequestConstant.PAGINATION_CONTEXT, paginationContext);
            }

            responseContext = new ResponseContext(
                    new ForwardResponseType(PageConstant.MY_ORDERS_PAGE), requestMap, new HashMap<>());

        } catch (ServiceException | NumberFormatException e) {
            logger.error("Failed move to 'my orders' page", e);
            responseContext = new ResponseContext(
                    new ForwardResponseType(PageConstant.ERROR_PAGE), requestMap, new HashMap<>());
        }
        return responseContext;
    }
}
