package com.epam.training.jwd.online.shop.controller.command.impl;

import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.ForwardResponseType;
import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.command.ResponseContext;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;

import java.util.HashMap;

/**
 * The class provides moving to 'not found' page, when page is not found
 *
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class ToNotFoundPageCommand implements Command {
    public final static ToNotFoundPageCommand INSTANCE = new ToNotFoundPageCommand();

    private ToNotFoundPageCommand() {
    }

    @Override
    public ResponseContext execute(RequestContext request) {
        return new ResponseContext(new ForwardResponseType(PageConstant.ERROR_404), new HashMap<>(), new HashMap<>());
    }
}
