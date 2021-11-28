package com.epam.training.jwd.online.shop.service.validator.impl;

import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.service.validator.AbstractValidator;
import com.epam.training.jwd.online.shop.service.validator.Validator;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;

import java.util.HashSet;
import java.util.Set;

public class NumberValidator extends AbstractValidator {
    private final String number;

    public NumberValidator(String number) {
        this.number = number;
    }

    public NumberValidator(Validator nextValidator, String number) {
        super(nextValidator);
        this.number = number;
    }

    @Override
    public Set<String> validateRequest(RequestContext requestContext) {
        Set<String> errorMessages = new HashSet<>();
        try {
            Integer.parseInt(number);
            if (Integer.parseInt(number) < 0) {
                errorMessages.add(
                        LocalizationMessage.localize(requestContext.getLocale(), "error.incorrectNumber"));
            }
        } catch (NumberFormatException e) {
            errorMessages.add(
                    LocalizationMessage.localize(requestContext.getLocale(), "error.incorrectNumber"));
        }

        if (nextValidator != null) {
            errorMessages.addAll(nextValidator.validateRequest(requestContext));
        }

        return errorMessages;
    }

}
