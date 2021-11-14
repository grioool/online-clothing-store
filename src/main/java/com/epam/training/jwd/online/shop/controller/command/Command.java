package com.epam.training.jwd.online.shop.controller.command;

public interface Command {

    CommandResponse execute(CommandRequest request);

    static Command withName(String name) {
        return ApplicationCommand.of(name)
                .getCommand();
    }

}
