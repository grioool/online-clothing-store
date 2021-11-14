package com.epam.training.jwd.online.shop.dao.entity;

import com.epam.training.jwd.online.shop.dao.exception.EntityNotFoundException;

import java.util.Arrays;

public enum Country {
    BELARUS(1),
    RUSSIA(2),
    UKRAINE(3);

    private final Integer id;

    Country(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }


    public static Country getById(Integer id) {
        return Arrays.stream(Country.values())
                .filter(country -> country.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
