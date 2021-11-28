package com.epam.training.jwd.online.shop.service.validator.impl;

import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.AbstractValidator;
import com.epam.training.jwd.online.shop.service.validator.Validator;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;
import java.util.HashSet;
import java.util.Set;

public class EmailValidator extends AbstractValidator {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+";

    public EmailValidator(Validator nextValidator) {
        super(nextValidator);
    }

    public EmailValidator() {
    }

    @Override
    public Set<String> validateRequest(RequestContext requestContext) {
        Set<String> errorMessages = new HashSet<>();
        String email = requestContext.getRequestParameters().get(RequestConstant.EMAIL);

        if (email == null || email.isEmpty() || !email.matches(EMAIL_PATTERN)) {
            errorMessages.add(LocalizationMessage.localize(requestContext.getLocale(), "error.mail"));
        }

        if (nextValidator != null) {
            errorMessages.addAll(nextValidator.validateRequest(requestContext));
        }

        return errorMessages;
    }
}
