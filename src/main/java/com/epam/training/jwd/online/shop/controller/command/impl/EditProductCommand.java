package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.AdminCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.controller.handler.impl.DescriptionHandler;
import com.epam.training.jwd.online.shop.controller.handler.impl.NumberHandler;
import com.epam.training.jwd.online.shop.controller.handler.impl.PriceHandler;
import com.epam.training.jwd.online.shop.controller.handler.impl.ProductNameHandler;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.dto.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Handler;

public class EditProductCommand implements Command, AdminCommand {
    private static final Logger logger = LogManager.getLogger(EditProductCommand.class);
    private static final ProductServiceImpl productService = ProductServiceImpl.getInstance();
    private static final Handler editProductPrice = new PriceHandler(new ProductNameHandler(new DescriptionHandler()));

    @Override
    public ResponseContext execute(RequestContext request) {
        ResponseContext responseContext;
        Map<String, Object> requestMap = new HashMap<>();

        String id = request.getRequestParameters().get(RequestConstant.ID);
        Set<String> errorMessage = new NumberHandler(editProductPrice, id).handleRequest(request);

        if (errorMessage.isEmpty()) {
            int productId = Integer.parseInt(id);
            String price = request.getRequestParameters().get(RequestConstant.PRODUCT_PRICE);
            String productName = request.getRequestParameters().get(RequestConstant.PRODUCT_NAME);
            BigDecimal productPrice = new BigDecimal(price);
            String productDescription = request.getRequestParameters().get(RequestConstant.PRODUCT_DESCRIPTION);

            try {
           //     Optional<String> serverMessage = productService.editProduct(productId, productName, productPrice, productDescription);

                if (!serverMessage.isPresent()) {
                    requestMap.put(RequestConstant.REDIRECT_COMMAND, CommandManager.TO_MENU_ITEM.getCommandName());
                } else {
                    requestMap.put(RequestConstant.SERVER_MESSAGE, LocalizationMessage.localize(request.getLocale(), serverMessage.get()));
                }
                responseContext = new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());

            } catch (ServiceException e) {
                logger.error("Failed to edit product", e);
                responseContext = new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE), new HashMap<>(), new HashMap<>());
            }
        } else {
            requestMap.put(RequestConstant.ERROR_MESSAGE, errorMessage);
            responseContext = new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());
        }
        return responseContext;
    }
}
