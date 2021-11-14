package com.epam.training.jwd.online.shop.dao.entity;

import java.util.Arrays;
import java.util.List;

public enum UserRole {
    USER(1),
    ADMIN(2),
    UNAUTHORIZED(3);

    private Integer id;

    UserRole(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static UserRole getById(Integer id) {
        return Arrays.stream(UserRole.values())
                .filter(role -> role.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static final List<UserRole> ALL_AVAILABLE_ROLES = Arrays.asList(values());

    public static List<UserRole> valuesAsList() {
        return ALL_AVAILABLE_ROLES;
    }
}
