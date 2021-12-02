package com.epam.training.jwd.online.shop.controller.command;

/**
 * The class is represented in {@link RequestContext} in order to {@link com.epam.training.jwd.online.shop.controller.ApplicationController} do redirect
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class RedirectResponseType extends ResponseType {
    private final String command;

    public RedirectResponseType(String command) {
        super(Type.REDIRECT);
        this.command = command;
    }

    public String getCommand(){
        return command.startsWith("?") ? command : "?command=" + command;
    }
}
