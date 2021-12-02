package com.epam.training.jwd.online.shop.controller.command.impl;

import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.ForwardResponseType;
import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.command.ResponseContext;
import com.epam.training.jwd.online.shop.controller.command.marker.AdminCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;

import java.util.HashMap;

/**
 * The class provides moving admin to page for add new product category
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class ToAddCategoryCommand implements Command, AdminCommand {

    @Override
    public ResponseContext execute(RequestContext request) {
        return new ResponseContext(new ForwardResponseType(PageConstant.ADD_CATEGORY_PRODUCT), new HashMap<>(), new HashMap<>());
    }
}
