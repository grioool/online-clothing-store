package com.epam.training.jwd.online.shop.controller.command;


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
