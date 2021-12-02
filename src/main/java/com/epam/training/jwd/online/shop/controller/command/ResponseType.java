package com.epam.training.jwd.online.shop.controller.command;

/**
 * Class represent types withName {@link ResponseContext}
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public abstract class ResponseType {
    public enum Type{
        FORWARD,
        REDIRECT,
        REST
    }

    private final Type type;

    public ResponseType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
