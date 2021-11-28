package com.epam.training.jwd.online.shop.controller.handler;


import com.epam.training.jwd.online.shop.controller.command.RequestContext;

import java.util.Set;

public interface Handler {
    Set<String> handleRequest(RequestContext requestContext);
}
