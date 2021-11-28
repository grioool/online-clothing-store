package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.UserCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.impl.NumberValidator;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DeleteProductFromCartCommand implements Command, UserCommand {
    private static final Logger logger = LogManager.getLogger(DeleteProductFromCartCommand.class);
    private static final ProductServiceImpl productService = ProductServiceImpl.getInstance();

    @Override
    public ResponseContext execute(RequestContext request) {
        Map<String, Object> requestMap = new HashMap<>();
        String id = request.getRequestParameters().get(RequestConstant.ID);
        Set<String> errorMessage = new NumberValidator(id).validateRequest(request);

        if (errorMessage.isEmpty()) {
            try {
                int productId = Integer.parseInt(id);
                Map<Integer, Integer> cart = (Map<Integer, Integer>) request.getSessionAttributes().get(RequestConstant.CART);

                if (cart != null && productService.findProductById(productId).isPresent()) {
                    if (cart.containsKey(productId)) {
                        cart.put(productId, cart.get(productId) - 1);
                        if (cart.get(productId) < 1) {
                            cart.remove(productId);
                            requestMap.put(RequestConstant.REDIRECT_COMMAND, CommandManager.TO_CART.getCommandName());
                        }
                    }
                    return new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());
                }
            } catch (ServiceException | NumberFormatException e) {
                logger.error("Failed to delete product from cart or incorrect product id", e);
            }
        }
        return new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE), requestMap, new HashMap<>());
    }
}
