package com.epam.training.jwd.online.shop.controller.command;

import java.util.HashMap;
import java.util.Map;

public class ResponseContext {
    private ResponseType responseType;
    private final Map<String, Object> requestAttributes;
    private final Map<String, Object> sessionAttributes;

    public ResponseContext(Map<String, Object> requestAttributes, Map<String, Object> sessionAttributes) {
        this.requestAttributes = requestAttributes;
        this.sessionAttributes = sessionAttributes;
    }

    public ResponseContext(ResponseType responseType, Map<String, Object> requestAttributes,
                           Map<String, Object> sessionAttributes) {
        this.responseType = responseType;
        this.requestAttributes = requestAttributes;
        this.sessionAttributes = sessionAttributes;
    }

    public ResponseContext(ResponseType responseType, Map<String, Object> requestAttributes) {
        this.responseType = responseType;
        this.requestAttributes = requestAttributes;
        this.sessionAttributes = new HashMap<>();
    }

    public ResponseContext(ResponseType responseType) {
        this.responseType = responseType;
        this.requestAttributes = new HashMap<>();
        this.sessionAttributes = new HashMap<>();
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public Map<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }
}
