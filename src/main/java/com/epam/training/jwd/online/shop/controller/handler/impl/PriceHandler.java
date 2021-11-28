package com.epam.training.jwd.online.shop.controller.handler.impl;


import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.controller.handler.AbstractHandler;
import com.epam.training.jwd.online.shop.controller.handler.Handler;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;

import java.util.HashSet;
import java.util.Set;

public class PriceHandler extends AbstractHandler {
    private static final String PRICE_REGEX = "^([0-9]{1,3}\\.[0-9]{1,2})$";


    public PriceHandler(Handler nextHandler) {
        super(nextHandler);
    }

    public PriceHandler() {
    }

    @Override
    public Set<String> handleRequest(RequestContext requestContext) {
        Set<String> errorMessages = new HashSet<>();
        String price = requestContext.getRequestParameters().get(RequestConstant.PRODUCT_PRICE);

        if (StringUtils.isNullOrEmpty(price) || !price.matches(PRICE_REGEX)) {
            errorMessages.add(LocalizationMessage.localize(requestContext.getLocale(), "serverMessage.price"));
        }

        if (nextHandler != null) {
            errorMessages.addAll(nextHandler.handleRequest(requestContext));
        }
        return errorMessages;
    }
}
