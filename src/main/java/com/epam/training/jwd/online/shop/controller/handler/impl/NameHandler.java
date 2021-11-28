package com.epam.training.jwd.online.shop.controller.handler.impl;


import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.controller.handler.AbstractHandler;
import com.epam.training.jwd.online.shop.controller.handler.Handler;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;

import java.util.HashSet;
import java.util.Set;

public class NameHandler extends AbstractHandler {
    private static final String NAME_PATTERN = "^[A-Za-zА-Яа-яЁё']{2,20}";

    public NameHandler(Handler nextHandler) {
        super(nextHandler);
    }

    public NameHandler() {
    }

    @Override
    public Set<String> handleRequest(RequestContext requestContext) {
        Set<String> errorMessages = new HashSet<>();
        String firstName = requestContext.getRequestParameters().get(RequestConstant.FIRST_NAME);
        String lastName = requestContext.getRequestParameters().get(RequestConstant.LAST_NAME);

        if (firstName == null || firstName.isEmpty()
                || !firstName.matches(NAME_PATTERN) || !lastName.matches(NAME_PATTERN)) {
            errorMessages.add(LocalizationMessage.localize(requestContext.getLocale(), "error.name"));
        }

        if (nextHandler != null) {
            errorMessages.addAll(nextHandler.handleRequest(requestContext));
        }

        return errorMessages;
    }
}
