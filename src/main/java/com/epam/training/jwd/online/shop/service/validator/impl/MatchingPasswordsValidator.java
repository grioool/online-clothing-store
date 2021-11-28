package com.epam.training.jwd.online.shop.service.validator.impl;

import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.AbstractValidator;
import com.epam.training.jwd.online.shop.service.validator.Validator;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;


import java.util.HashSet;
import java.util.Set;

public class MatchingPasswordsValidator extends AbstractValidator {

    public MatchingPasswordsValidator(Validator nextValidator) {
        super(nextValidator);
    }

    public MatchingPasswordsValidator() {
    }

    @Override
    public Set<String> validateRequest(RequestContext requestContext) {
        Set<String> errorMessages = new HashSet<>();
        String password = requestContext.getRequestParameters().get(RequestConstant.PASSWORD);
        String repeatPassword = requestContext.getRequestParameters().get(RequestConstant.REPEAT_PASSWORD);

        if (password.isEmpty() || password == null) {
            errorMessages.add(LocalizationMessage.localize(requestContext.getLocale(), "error.password"));
        } else if (!password.equals(repeatPassword)) {
            errorMessages.add(LocalizationMessage.localize(requestContext.getLocale(), "error.repeatPassword"));
        }

        if (nextValidator != null) {
            errorMessages.addAll(nextValidator.validateRequest(requestContext));
        }

        return errorMessages;

    }
}
