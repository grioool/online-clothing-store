package com.epam.training.jwd.online.shop.service.validator.impl;


import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.AbstractValidator;
import com.epam.training.jwd.online.shop.service.validator.Validator;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;

import java.util.HashSet;
import java.util.Set;

public class PasswordValidator extends AbstractValidator {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}";

    public PasswordValidator(Validator nextValidator) {
        super(nextValidator);
    }

    public PasswordValidator() {
    }

    @Override
    public Set<String> validateRequest(RequestContext requestContext) {
        Set<String> errorMessages = new HashSet<>();
        String password = requestContext.getRequestParameters().get(RequestConstant.PASSWORD);

        if (password == null || password.isEmpty() || !password.matches(PASSWORD_PATTERN)) {
            errorMessages.add(LocalizationMessage.localize(requestContext.getLocale(), "error.password"));
        }

        if (nextValidator != null) {
            errorMessages.addAll(nextValidator.validateRequest(requestContext));
        }

        return errorMessages;
    }
}
