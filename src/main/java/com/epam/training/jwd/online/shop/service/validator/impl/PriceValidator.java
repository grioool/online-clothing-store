package com.epam.training.jwd.online.shop.service.validator.impl;


import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.AbstractValidator;
import com.epam.training.jwd.online.shop.service.validator.Validator;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;

import java.util.HashSet;
import java.util.Set;

public class PriceValidator extends AbstractValidator {
    private static final String PRICE_REGEX = "^([0-9]{1,3}\\.[0-9]{1,2})$";


    public PriceValidator(Validator nextValidator) {
        super(nextValidator);
    }

    public PriceValidator() {
    }

    @Override
    public Set<String> validateRequest(RequestContext requestContext) {
        Set<String> errorMessages = new HashSet<>();
        String price = requestContext.getRequestParameters().get(RequestConstant.PRODUCT_PRICE);

        if (price == null || price.isEmpty() || !price.matches(PRICE_REGEX)) {
            errorMessages.add(LocalizationMessage.localize(requestContext.getLocale(), "serverMessage.price"));
        }

        if (nextValidator != null) {
            errorMessages.addAll(nextValidator.validateRequest(requestContext));
        }
        return errorMessages;
    }
}
