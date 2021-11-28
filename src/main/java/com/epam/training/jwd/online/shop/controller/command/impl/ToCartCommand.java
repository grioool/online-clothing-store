package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.ForwardResponseType;
import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.command.ResponseContext;
import com.epam.training.jwd.online.shop.controller.command.marker.UserCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.dao.entity.Product;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.dto.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ToCartCommand implements Command, UserCommand {
    private static final Logger LOGGER = LogManager.getLogger(ToCartCommand.class);
    private static final ProductServiceImpl PRODUCT_SERVICE = ProductServiceImpl.getInstance();

    @Override
    public ResponseContext execute(RequestContext request) {
        try {
            Map<Integer, Integer> cart = (Map<Integer, Integer>) request.getSessionAttributes().get(RequestConstant.CART);
            Map<Product, Integer> productsInCart;

            if (cart == null) {
                productsInCart = new HashMap<>();
            } else {
                productsInCart = PRODUCT_SERVICE.receiveProducts(cart);
            }

            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put(RequestConstant.CART, productsInCart.entrySet());
            return new ResponseContext(new ForwardResponseType(PageConstant.CART_PAGE), requestMap, new HashMap<>());
        } catch (ServiceException e) {
            LOGGER.error("Failed to receive products from cart", e);
        }
        return new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE));
    }
}
