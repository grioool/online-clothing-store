package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.UserCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.dao.entity.*;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.dto.OrderServiceImpl;
import com.epam.training.jwd.online.shop.service.dto.ProductServiceImpl;
import com.epam.training.jwd.online.shop.service.dto.UserDto;
import com.epam.training.jwd.online.shop.service.dto.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CreateOrderCommand implements Command, UserCommand {
    private static final Logger logger = LogManager.getLogger(CreateOrderCommand.class);
    private static final OrderServiceImpl orderService = OrderServiceImpl.getInstance();
    private static final ProductServiceImpl productService = ProductServiceImpl.getInstance();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();


    @Override
    public ResponseContext execute(RequestContext request) {
        Map<String, Object> requestMap = new HashMap<>();
//        Map<String, Object> sessionMap = new HashMap<>();
//        String deliveryDateStr = request.getRequestParameters().get(RequestConstant.DELIVERY_DATE);
//        String deliveryAddress = request.getRequestParameters().get(RequestConstant.DELIVERY_ADDRESS);
//        String paymentMethod = request.getRequestParameters().get(RequestConstant.PAYMENT_METHOD);
        UserDto userDto = (UserDto) request.getSessionAttributes().get(RequestConstant.USER);
        Map<Integer, Integer> cart = (Map<Integer, Integer>) request.getSessionAttributes().get(RequestConstant.CART);

        if (cart == null || cart.isEmpty()) {
            requestMap.put(RequestConstant.CART, new HashMap<>());
            return new ResponseContext(new ForwardResponseType(PageConstant.CART_PAGE), requestMap, new HashMap<>());
        }

        try {
            Map<Product, Integer> products = productService.receiveProducts(cart);
            Optional<User> userOptional = userService.findById(userDto.getId());

            if (products.size() > 0 && userOptional.isPresent()) {
                LocalDateTime deliveryDate = LocalDateTime.parse(deliveryDateStr,
                        DateTimeFormatter.ofPattern(RequestConstant.DELIVERY_DATE_PATTERN));

//                Order order = Order.builder()
//                        .withId(userDto.getId())
//                        .withOrderStatus(OrderStatus.ACTIVE)
//                        .withCreateDate(LocalDateTime.now())
//                        .withPaymentMethod(PaymentMethod.valueOf(paymentMethod))
//                        .withDeliveryDate(deliveryDate)
//                        .withDeliveryAddress(deliveryAddress)
//                        .withProducts(products)
//                        .withUser(userOptional.get())
//                        .build();

                Optional<String> serverMessage = orderService.createOrder(order);

                if (!serverMessage.isPresent()) {
                    sessionMap.put(RequestConstant.CART, new HashMap<>());
                    requestMap.put(RequestConstant.REDIRECT_COMMAND, CommandManager.TO_MAIN.getCommandName());
                } else {
                    requestMap.put(RequestConstant.SERVER_MESSAGE,
                            LocalizationMessage.localize(request.getLocale(), serverMessage.get()));
                }
                return new ResponseContext(new RestResponseType(), requestMap, sessionMap);
            }
        } catch (ServiceException e) {
            logger.error("Failed to create order", e);
        }
        return new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE), requestMap, sessionMap);
    }
}
