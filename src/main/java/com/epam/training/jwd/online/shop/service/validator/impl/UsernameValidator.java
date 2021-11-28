package com.epam.training.jwd.online.shop.service.validator.impl;


import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.AbstractValidator;
import com.epam.training.jwd.online.shop.service.validator.Validator;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;

import java.util.HashSet;
import java.util.Set;


public class UsernameValidator extends AbstractValidator {
    private static final String USERNAME_PATTERN = "^[(\\w)-]{4,20}";

    public UsernameValidator(Validator nextValidator) {
        super(nextValidator);
    }

    public UsernameValidator() {
    }

    @Override
    public Set<String> validateRequest(RequestContext requestContext) {
        Set<String> errorMessages = new HashSet<>();
        String username = requestContext.getRequestParameters().get(RequestConstant.USERNAME);

        if (username == null || username.isEmpty() || !username.matches(USERNAME_PATTERN)) {
            errorMessages.add(LocalizationMessage.localize(requestContext.getLocale(), "error.username"));
        }

        if (nextValidator != null) {
            errorMessages.addAll(nextValidator.validateRequest(requestContext));
        }

        return errorMessages;
    }
}
