package com.epam.training.jwd.online.shop.controller.handler.impl;


import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.controller.handler.AbstractHandler;
import com.epam.training.jwd.online.shop.controller.handler.Handler;

import java.util.HashSet;
import java.util.Set;


public class UsernameHandler extends AbstractHandler {
    private static final String USERNAME_PATTERN = "^[(\\w)-]{4,20}";

    public UsernameHandler(Handler nextHandler) {
        super(nextHandler);
    }

    public UsernameHandler() {
    }

    @Override
    public Set<String> handleRequest(RequestContext requestContext) {
        Set<String> error_messages = new HashSet<>();
        String username = requestContext.getRequestParameters().get(RequestConstant.USERNAME);

        if (StringUtils.isNullOrEmpty(username) || !username.matches(USERNAME_PATTERN)) {
            error_messages.add(LocalizationMessage.localize(requestContext.getLocale(), "error.username"));
        }

        if (nextHandler != null) {
            error_messages.addAll(nextHandler.handleRequest(requestContext));
        }

        return error_messages;
    }
}
