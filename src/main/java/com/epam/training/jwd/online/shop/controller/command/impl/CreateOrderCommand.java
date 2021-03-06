package com.epam.training.jwd.online.shop.controller.command.impl;

import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.UserCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.dao.entity.*;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.impl.OrderServiceImpl;
import com.epam.training.jwd.online.shop.service.impl.ProductServiceImpl;
import com.epam.training.jwd.online.shop.service.dto.UserDto;
import com.epam.training.jwd.online.shop.service.impl.UserServiceImpl;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The class provides create new {@link Order}
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class CreateOrderCommand implements Command, UserCommand {
    private static final Logger logger = LogManager.getLogger(CreateOrderCommand.class);
    private static final OrderServiceImpl orderService = OrderServiceImpl.getInstance();
    private static final ProductServiceImpl productService = ProductServiceImpl.getInstance();
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();


    @Override
    public ResponseContext execute(RequestContext request) {
        Map<String, Object> requestMap = new HashMap<>();
        Map<String, Object> sessionMap = new HashMap<>();

        String deliveryDateStr = request.getRequestParameters().get(RequestConstant.DELIVERY_DATE);
        String deliveryCountry = request.getRequestParameters().get(RequestConstant.DELIVERY_COUNTRY);
        String deliveryTown = request.getRequestParameters().get(RequestConstant.DELIVERY_TOWN);
        String paymentMethod = request.getRequestParameters().get(RequestConstant.PAYMENT_METHOD);

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

                Order order = Order.builder()
                        .withId(userDto.getId())
                        .withPaymentMethod(PaymentMethod.valueOf(paymentMethod))
                        .withOrderStatus(OrderStatus.ACTIVE)
                        .withProducts(products)
                        .withDeliveryDate(deliveryDate)
                        .withOrderDate(LocalDateTime.now())
                        .withDeliveryCountry(Country.valueOf(deliveryCountry))
                        .withUser(userOptional.get())
                        .withDeliveryTown(Town.valueOf(deliveryTown))
                     //   .withOrderCost()
                        .build();

                Optional<String> serverMessage = orderService.saveOrder(order);

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
