package com.epam.training.jwd.online.shop.controller.handler.impl;


import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.controller.handler.AbstractHandler;
import com.epam.training.jwd.online.shop.controller.handler.Handler;

import java.util.HashSet;
import java.util.Set;

public class PhoneNumberHandler extends AbstractHandler {
    private static final String PHONE_NUMBER_PATTERN = "^\\+375((44)|(33)|(29)|(25))[0-9]{7}";

    public PhoneNumberHandler(Handler nextHandler) {
        super(nextHandler);
    }

    public PhoneNumberHandler() {
    }

    @Override
    public Set<String> handleRequest(RequestContext requestContext) {
        Set<String> error_messages = new HashSet<>();
        String phoneNumber = requestContext.getRequestParameters().get(RequestConstant.PHONE_NUMBER);

        if (StringUtils.isNullOrEmpty(phoneNumber) || !phoneNumber.matches(PHONE_NUMBER_PATTERN)) {
            error_messages.add(LocalizationMessage.localize(requestContext.getLocale(), "serverMessage.phoneNumber"));
        }

        if (nextHandler != null) {
            error_messages.addAll(nextHandler.handleRequest(requestContext));
        }

        return error_messages;
    }
}
