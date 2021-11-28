package com.epam.training.jwd.online.shop.service.validator.impl;


import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.AbstractValidator;
import com.epam.training.jwd.online.shop.service.validator.Validator;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;

import java.util.HashSet;
import java.util.Set;

public class ProductNameValidator extends AbstractValidator {
    private static final String PRODUCT_NAME_PATTERN = "^[A-Za-zа-я-А-я\\s'-]{4,20}?$";

    public ProductNameValidator(Validator nextValidator) {
        super(nextValidator);
    }

    public ProductNameValidator() {
    }

    @Override
    public Set<String> validateRequest(RequestContext requestContext) {
        Set<String> errorMessages = new HashSet<>();
        String name = requestContext.getRequestParameters().get(RequestConstant.PRODUCT_NAME);

        if (name == null || name.isEmpty() || !name.matches(PRODUCT_NAME_PATTERN)) {
            errorMessages.add(LocalizationMessage.localize(requestContext.getLocale(), "serverMessage.productName"));
        }

        if (nextValidator != null) {
            errorMessages.addAll(nextValidator.validateRequest(requestContext));
        }

        return errorMessages;
    }
}
