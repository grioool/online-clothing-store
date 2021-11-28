package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.UserCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.dto.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AddProductToCartCommand implements Command, UserCommand {
    private static final Logger logger = LogManager.getLogger(AddProductToCartCommand.class);
    private static final ProductServiceImpl productService = ProductServiceImpl.getInstance();

    @Override
    public ResponseContext execute(RequestContext request) {
        String id = request.getRequestParameters().get(RequestConstant.ID);
        Set<String> errorMessage = new NumberHandler(request.getRequestParameters().get(RequestConstant.ID)).handleRequest(request);

        if (errorMessage.isEmpty()) {
            int productId = Integer.parseInt(id);
            try {
                if (productService.findProductById(productId).isPresent()) {
                    Map<String, Object> sessionResponse = new HashMap<>();
                    Map<Integer, Integer> cart = (Map<Integer, Integer>) request.getSessionAttributes().get(RequestConstant.CART);
                    if (cart == null) {
                        cart = new HashMap<>();
                        sessionResponse.put(RequestConstant.CART, cart);
                    }
                    if (cart.containsKey(productId)) {
                        cart.put(productId, cart.get(productId) + 1);
                    } else {
                        cart.put(productId, 1);
                    }
                    return new ResponseContext(new RestResponseType(), new HashMap<>(), sessionResponse);
                }

            } catch (ServiceException e) {
                logger.error("Failed to add product to cart", e);
            }
        }
        return new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE), new HashMap<>(), new HashMap<>());
    }
}
