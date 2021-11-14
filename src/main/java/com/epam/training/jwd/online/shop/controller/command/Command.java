package com.epam.training.jwd.online.shop.controller.command;

import com.epam.training.jwd.online.shop.dao.exception.EntityNotFoundException;

public interface Command {

    CommandResponse execute(CommandRequest request) throws EntityNotFoundException;

    static Command withName(String name) {
        return ApplicationCommand.of(name)
                .getCommand();
    }

}
