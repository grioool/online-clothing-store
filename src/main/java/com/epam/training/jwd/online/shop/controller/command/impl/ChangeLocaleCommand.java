package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.RedirectResponseType;
import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.command.ResponseContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;

import java.util.HashMap;
import java.util.Map;

public class ChangeLocaleCommand implements Command {

    @Override
    public ResponseContext execute(RequestContext request) {
        String locale = request.getRequestParameters().get(RequestConstant.LOCALE);
        String currUrl = request.getRequestParameters().get(RequestConstant.CURRENT_URL);
        Map<String, Object> map = new HashMap<>();
        map.put(RequestConstant.LOCALE, locale);
        return new ResponseContext(new RedirectResponseType(currUrl), new HashMap<>(), map);
    }
}