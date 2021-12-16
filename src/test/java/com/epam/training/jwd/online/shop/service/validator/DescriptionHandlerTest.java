package com.epam.training.jwd.online.shop.service.validator;

import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.impl.DescriptionValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class DescriptionHandlerTest {
    private DescriptionValidator descriptionValidator;
    private Map<String, String> requestMap;

    @BeforeEach
    void setUp() {
        descriptionValidator = new DescriptionValidator();
        requestMap = new HashMap<>();
    }

    @Test
    public void handleRequest_ShouldReturnEmptySet_WhenDescriptionMatchPattern(){
        requestMap.put(RequestConstant.PRODUCT_DESCRIPTION, "valid description");
        RequestContext requestContext = new RequestContext(requestMap, "ru_RU");

        assertEquals(Collections.EMPTY_SET, descriptionValidator.validateRequest(requestContext));
    }

    @Test
    public void handleRequest_ShouldReturnServerMessage_WhenDescriptionDoesntMatchPattern() {
        requestMap.put(RequestConstant.PRODUCT_DESCRIPTION, "he");
        RequestContext requestContext = new RequestContext(requestMap, "ru_RU");

        assertNotEquals(Collections.emptySet(), descriptionValidator.validateRequest(requestContext));
    }

    @AfterAll
    void clearRequestMap() {
        requestMap.clear();
    }

    @AfterEach
    void tearDown(){
        descriptionValidator = null;
        requestMap = null;
    }
}