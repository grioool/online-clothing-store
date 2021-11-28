package com.epam.training.jwd.online.shop.service.validator.impl;


import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.AbstractValidator;
import com.epam.training.jwd.online.shop.service.validator.Validator;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;

import java.util.HashSet;
import java.util.Set;

public class PhoneNumberValidator extends AbstractValidator {
    private static final String PHONE_NUMBER_PATTERN = "^\\+375((44)|(33)|(29)|(25))[0-9]{7}";

    public PhoneNumberValidator(Validator nextValidator) {
        super(nextValidator);
    }

    public PhoneNumberValidator() {
    }

    @Override
    public Set<String> validateRequest(RequestContext requestContext) {
        Set<String> errorMessages = new HashSet<>();
        String phoneNumber = requestContext.getRequestParameters().get(RequestConstant.PHONE_NUMBER);

        if (phoneNumber == null || phoneNumber.isEmpty() || !phoneNumber.matches(PHONE_NUMBER_PATTERN)) {
            errorMessages.add(LocalizationMessage.localize(requestContext.getLocale(), "serverMessage.phoneNumber"));
        }

        if (nextValidator != null) {
            errorMessages.addAll(nextValidator.validateRequest(requestContext));
        }

        return errorMessages;
    }
}
