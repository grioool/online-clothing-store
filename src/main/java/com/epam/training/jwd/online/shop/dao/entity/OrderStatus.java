package com.epam.training.jwd.online.shop.dao.entity;

import com.epam.training.jwd.online.shop.dao.exception.EntityNotFoundException;

import java.util.Arrays;

public enum OrderStatus {
    ACTIVE(1),
    CANCELLED(2),
    COMPLETED(3),
    UNACCEPTED(4);

    private final Integer id;

    OrderStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static OrderStatus getById(Integer id) {
        return Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> orderStatus.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
