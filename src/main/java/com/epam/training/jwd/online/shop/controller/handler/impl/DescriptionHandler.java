package com.epam.training.jwd.online.shop.controller.handler.impl;


import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.controller.handler.AbstractHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Handler;

public class DescriptionHandler extends AbstractHandler {

    public DescriptionHandler(Handler nextHandler) {
        super(nextHandler);
    }

    public DescriptionHandler() {
    }

    @Override
    public Set<String> handleRequest(RequestContext requestContext) {
        Set<String> errorMessages = new HashSet<>();
        String description = requestContext.getRequestParameters().get(RequestConstant.PRODUCT_DESCRIPTION);

        if (StringUtils.isNullOrEmpty(description) || description.length() < 4 || description.length() > 80) {
            errorMessages.add(LocalizationMessage.localize(requestContext.getLocale(), "serverMessage.description"));
        }

        if (nextHandler != null) {
            errorMessages.addAll(nextHandler.handleRequest(requestContext));
        }
        return errorMessages;
    }
}