package com.epam.training.jwd.online.shop.controller.command;

/**
 * The class is represented in {@link RequestContext} in order to {@link com.epam.training.jwd.online.shop.controller.ApplicationController} do forward
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class ForwardResponseType extends ResponseType {
    private final String page;

    public ForwardResponseType(String page) {
        super(Type.FORWARD);
        this.page = page;
    }

    public String getPage() {
        return page;
    }
}
