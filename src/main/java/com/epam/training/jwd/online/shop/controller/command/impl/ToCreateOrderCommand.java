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
import com.epam.training.jwd.online.shop.service.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ToCreateOrderCommand implements Command, UserCommand {
    private static final Logger LOGGER = LogManager.getLogger(ToCreateOrderCommand.class);
    private static final ProductServiceImpl PRODUCT_SERVICE = ProductServiceImpl.getInstance();

    @Override
    public ResponseContext execute(RequestContext request) {
        Map<String, Object> requestMap = new HashMap<>();
        Map<Integer, Integer> cart = (Map<Integer, Integer>) request.getSessionAttributes().get(RequestConstant.CART);

        if (cart != null) {
            try {
                Map<Product, Integer> productsInCart = PRODUCT_SERVICE.receiveProducts(cart);
                if (!productsInCart.isEmpty()) {
                    BigDecimal totalCost = PRODUCT_SERVICE.calcOrderCost(productsInCart);
                    requestMap.put(RequestConstant.TOTAL_COST, totalCost);

                    return new ResponseContext(new ForwardResponseType(PageConstant.CREATE_ORDER), requestMap, new HashMap<>());
                }
            } catch (ServiceException e) {
                LOGGER.error("Failed to receive product from cart", e);
            }
        }
        return new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE), requestMap, new HashMap<>());
    }
}
