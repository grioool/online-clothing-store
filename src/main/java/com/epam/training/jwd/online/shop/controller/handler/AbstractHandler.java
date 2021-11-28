package com.epam.training.jwd.online.shop.controller.handler;


public abstract class AbstractHandler implements Handler {
    protected final Handler nextHandler;

    public AbstractHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public AbstractHandler() {
        nextHandler = null;
    }
}
