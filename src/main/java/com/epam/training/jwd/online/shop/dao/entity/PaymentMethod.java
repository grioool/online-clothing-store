package com.epam.training.jwd.online.shop.dao.entity;

import com.epam.training.jwd.online.shop.dao.exception.EntityNotFoundException;

import java.util.Arrays;

public enum PaymentMethod {
    CARD(1),
    CASH(2);

    private final Integer id;

    PaymentMethod(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }


    public static PaymentMethod getById(Integer id) {
        return Arrays.stream(PaymentMethod.values())
                .filter(paymentMethod -> paymentMethod.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
