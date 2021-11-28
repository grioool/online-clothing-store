package com.epam.training.jwd.online.shop.service.validator;


import com.epam.training.jwd.online.shop.controller.command.RequestContext;

import java.util.Set;

public interface Validator {
    Set<String> validateRequest(RequestContext requestContext);
}
