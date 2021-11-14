package com.epam.training.jwd.online.shop.dao.entity;

import com.epam.training.jwd.online.shop.dao.exception.EntityNotFoundException;

import java.util.Arrays;

public enum Town {
    MINSK(1),
    MOSCOW(2),
    KIEV(3);

    private final Integer id;

    Town(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static Town getById(Integer id) {
        return Arrays.stream(Town.values())
                .filter(town -> town.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}

