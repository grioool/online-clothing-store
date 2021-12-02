package com.epam.training.jwd.online.shop.controller.command.impl;

import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.ForwardResponseType;
import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.command.ResponseContext;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;

import java.util.HashMap;

/**
 * The class provides moving user to access blocked page when he has not enough access
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class ToAccessBlockedCommand implements Command {

    @Override
    public ResponseContext execute(RequestContext request) {
        return new ResponseContext(new ForwardResponseType(PageConstant.ACCESS_BLOCKED_PAGE), new HashMap<>(), new HashMap<>());
    }
}
