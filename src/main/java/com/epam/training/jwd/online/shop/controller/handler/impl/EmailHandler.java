package com.epam.training.jwd.online.shop.controller.handler.impl;

import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.controller.handler.AbstractHandler;
import com.epam.training.jwd.online.shop.controller.handler.Handler;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;
import java.util.HashSet;
import java.util.Set;

public class EmailHandler extends AbstractHandler {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+";

    public EmailHandler(Handler nextHandler) {
        super(nextHandler);
    }

    public EmailHandler() {
    }

    @Override
    public Set<String> handleRequest(RequestContext requestContext) {
        Set<String> errorMessages = new HashSet<>();
        String email = requestContext.getRequestParameters().get(RequestConstant.EMAIL);

        if (email == null || email.isEmpty() || !email.matches(EMAIL_PATTERN)) {
            errorMessages.add(LocalizationMessage.localize(requestContext.getLocale(), "error.mail"));
        }

        if (nextHandler != null) {
            errorMessages.addAll(nextHandler.handleRequest(requestContext));
        }

        return errorMessages;
    }
}
