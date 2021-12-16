package com.epam.training.jwd.online.shop.service.validator;

import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.impl.PhoneNumberValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PhoneNumberHandlerTest {
    private PhoneNumberValidator phoneNumberValidator;
    private Map<String, String> requestMap;

    @BeforeEach
    void setUp() {
        phoneNumberValidator = new PhoneNumberValidator();
        requestMap = new HashMap<>();
    }

    @Test
    public void handleRequest_ShouldReturnEmptySet_WhenPhoneNumberMatchPattern(){
        requestMap.put(RequestConstant.PHONE_NUMBER, "+375333132549");
        RequestContext requestContext = new RequestContext(requestMap, "ru_RU");

        assertEquals(Collections.EMPTY_SET, phoneNumberValidator.validateRequest(requestContext));
    }

    @Test
    public void handleRequest_ShouldReturnServerMessage_WhenPhoneNumberDoesntMatchPattern() {
        requestMap.put(RequestConstant.PHONE_NUMBER, "+375259999999456");
        RequestContext requestContext = new RequestContext(requestMap, "ru_RU");

        assertNotEquals(Collections.emptySet(), phoneNumberValidator.validateRequest(requestContext));
    }

    @AfterAll
    void clearRequestMap() {
        requestMap.clear();
    }

    @AfterEach
    void tearDown(){
        phoneNumberValidator = null;
        requestMap = null;
    }
}