package com.epam.training.jwd.online.shop.controller.command;

import com.epam.training.jwd.online.shop.dao.exception.EntityNotFoundException;

public interface Command {


    ResponseContext execute(RequestContext request);

    static Command withName(String name){
        return CommandManager.of(name);
    }

}
