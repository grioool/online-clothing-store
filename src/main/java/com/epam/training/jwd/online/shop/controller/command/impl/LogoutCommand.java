package com.epam.training.jwd.online.shop.controller.command.impl;

import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.UserCommand;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * The class provides log out {@link com.epam.training.jwd.online.shop.dao.entity.User} and removes him from {@link javax.servlet.http.HttpSession}
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class LogoutCommand implements Command, UserCommand {

    @Override
    public ResponseContext execute(RequestContext request) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put(RequestConstant.LOGOUT, "logout");
        return new ResponseContext(new RedirectResponseType(CommandManager.TO_MAIN.getCommandName()),
                requestMap, new HashMap<>());
    }
}
