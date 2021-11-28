package com.epam.training.jwd.online.shop.service.validator.impl;


import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.AbstractValidator;
import com.epam.training.jwd.online.shop.service.validator.Validator;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;

import java.util.HashSet;
import java.util.Set;

public class NameValidator extends AbstractValidator {
    private static final String NAME_PATTERN = "^[A-Za-zА-Яа-яЁё']{2,20}";

    public NameValidator(Validator nextValidator) {
        super(nextValidator);
    }

    public NameValidator() {
    }

    @Override
    public Set<String> validateRequest(RequestContext requestContext) {
        Set<String> errorMessages = new HashSet<>();
        String firstName = requestContext.getRequestParameters().get(RequestConstant.FIRST_NAME);
        String lastName = requestContext.getRequestParameters().get(RequestConstant.LAST_NAME);

        if (firstName == null || firstName.isEmpty()
                || !firstName.matches(NAME_PATTERN) || !lastName.matches(NAME_PATTERN)) {
            errorMessages.add(LocalizationMessage.localize(requestContext.getLocale(), "error.name"));
        }

        if (nextValidator != null) {
            errorMessages.addAll(nextValidator.validateRequest(requestContext));
        }

        return errorMessages;
    }
}
