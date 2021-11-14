package com.epam.training.jwd.online.shop.dao.entity;

import com.epam.training.jwd.online.shop.dao.exception.EntityNotFoundException;

import java.util.Arrays;
import java.util.Objects;

public enum Brand {
    GRIOOOL(1),
    GRIOOOLANDCO(2);

    private final Integer id;

    Brand(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }


    public static Brand getById(Integer id) {
        return Arrays.stream(Brand.values())
                .filter(brand -> brand.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
