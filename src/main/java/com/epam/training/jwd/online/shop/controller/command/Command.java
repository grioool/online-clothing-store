package com.epam.training.jwd.online.shop.controller.command;

/**
 * The interface represent Command pattern for {@link RequestContext}
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public interface Command {

    ResponseContext execute(RequestContext request);

    static Command withName(String name){
        return CommandManager.of(name);
    }

}
