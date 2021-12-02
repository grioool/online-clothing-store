package com.epam.training.jwd.online.shop.service.validator;

import com.epam.training.jwd.online.shop.controller.command.RequestContext;

import java.util.Set;

/**
 * The interface of application validator
 *
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public interface Validator {
    Set<String> validateRequest(RequestContext requestContext);
}
