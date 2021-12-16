package com.epam.training.jwd.online.shop.service.validator;

import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.impl.UsernameValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UsernameHandlerTest {
    private UsernameValidator usernameValidator;
    private Map<String, String> requestMap;

    @BeforeEach
    void setUp() {
        usernameValidator = new UsernameValidator();
        requestMap = new HashMap<>();
    }

    @Test
    public void handleRequest_ShouldReturnEmptySet_WhenUsernameMatchPattern(){
        requestMap.put(RequestConstant.USERNAME, "userName");
        RequestContext requestContext = new RequestContext(requestMap, "ru_RU");

        assertEquals(Collections.EMPTY_SET, usernameValidator.validateRequest(requestContext));
    }

    @Test
    public void handleRequest_ShouldReturnServerMessage_WhenUsernameDoesntMatchPattern() {
        requestMap.put(RequestConstant.PRODUCT_DESCRIPTION, "invalid username long 123");
        RequestContext requestContext = new RequestContext(requestMap, "ru_RU");

        assertNotEquals(Collections.emptySet(), usernameValidator.validateRequest(requestContext));
    }

    @AfterAll
    void clearRequestMap() {
        requestMap.clear();
    }

    @AfterEach
    void tearDown(){
        usernameValidator = null;
        requestMap = null;
    }
}