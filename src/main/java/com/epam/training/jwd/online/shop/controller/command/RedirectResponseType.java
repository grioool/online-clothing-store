package com.epam.training.jwd.online.shop.controller.command;

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
