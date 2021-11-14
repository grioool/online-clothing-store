package com.epam.training.jwd.online.shop.service.validator;

import com.sun.xml.internal.ws.client.RequestContext;

import java.util.Set;

public interface Validator {
    Set<String> validate(RequestContext context);
}
