package com.epam.training.jwd.online.shop.service.validator;

import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.impl.ProductNameValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ProductNameHandlerTest {
    private ProductNameValidator productNameValidator;
    private Map<String, String> requestMap;

    @BeforeEach
    void setUp() {
        productNameValidator = new ProductNameValidator();
        requestMap = new HashMap<>();
    }

    @Test
    public void handleRequest_ShouldReturnEmptySet_WhenNameMatchPattern(){
        requestMap.put(RequestConstant.PRODUCT_NAME, "steak medium-rare");
        RequestContext requestContext = new RequestContext(requestMap, "ru_RU");

        assertEquals(Collections.EMPTY_SET, productNameValidator.validateRequest(requestContext));
    }

    @Test
    public void handleRequest_ShouldReturnServerMessage_WhenNameIsNull() {
        requestMap.put(RequestConstant.PRODUCT_NAME, "he");
        RequestContext requestContext = new RequestContext(requestMap, "ru_RU");

        assertNotEquals(Collections.emptySet(), productNameValidator.validateRequest(requestContext));
    }

    @AfterAll
    void clearRequestMap() {
        requestMap.clear();
    }

    @AfterEach
    void tearDown(){
        productNameValidator = null;
        requestMap = null;
    }
}