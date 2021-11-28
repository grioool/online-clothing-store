package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.UserCommand;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;

import java.util.HashMap;
import java.util.Map;

public class LogoutCommand implements Command, UserCommand {

    @Override
    public ResponseContext execute(RequestContext request) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put(RequestConstant.LOGOUT, "logout");
        return new ResponseContext(new RedirectResponseType(CommandManager.TO_MAIN.getCommandName()),
                requestMap, new HashMap<>());
    }
}
