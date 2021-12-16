package com.epam.training.jwd.online.shop.service.validator;

import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.impl.PriceValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PriceHandlerTest {
    private PriceValidator priceValidator;
    private Map<String, String> requestMap;

    @BeforeEach
    void setUp() {
        priceValidator = new PriceValidator();
        requestMap = new HashMap<>();
    }

    @Test
    public void handleRequest_ShouldReturnEmptySet_WhenPriceMatchPattern(){
        requestMap.put(RequestConstant.PRODUCT_PRICE, "15.99");
        RequestContext requestContext = new RequestContext(requestMap, "ru_RU");

        assertEquals(Collections.EMPTY_SET, priceValidator.validateRequest(requestContext));
    }

    @Test
    public void handleRequest_ShouldReturnServerMessage_WhenPriceDoesntMatchPattern() {
        requestMap.put(RequestConstant.PRODUCT_PRICE, "195.590");
        RequestContext requestContext = new RequestContext(requestMap, "ru_RU");

        assertNotEquals(Collections.emptySet(), priceValidator.validateRequest(requestContext));
    }

    @AfterAll
    void clearRequestMap() {
        requestMap.clear();
    }

    @AfterEach
    void tearDown(){
        priceValidator = null;
        requestMap = null;
    }
}