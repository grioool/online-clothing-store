package com.epam.training.jwd.online.shop.service.validator.impl;

import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.AbstractValidator;
import com.epam.training.jwd.online.shop.service.validator.Validator;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;

import java.util.HashSet;
import java.util.Set;

public class DescriptionValidator extends AbstractValidator {

    public DescriptionValidator(Validator nextValidator) {
        super(nextValidator);
    }

    public DescriptionValidator() {
    }

    @Override
    public Set<String> validateRequest(RequestContext requestContext) {
        Set<String> errorMessages = new HashSet<>();
        String description = requestContext.getRequestParameters().get(RequestConstant.PRODUCT_DESCRIPTION);

        if (description == null || description.length() < 4 || description.length() > 80) {
            errorMessages.add(LocalizationMessage.localize(requestContext.getLocale(), "serverMessage.description"));
        }

        if (nextValidator != null) {
            errorMessages.addAll(nextValidator.validateRequest(requestContext));
        }
        return errorMessages;
    }
}
