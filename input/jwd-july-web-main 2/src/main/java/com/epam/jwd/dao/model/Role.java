package com.epam.jwd.dao.model;

import java.util.Arrays;
import java.util.List;

public enum Role {
    USER,
    ADMIN,
    UNAUTHORIZED;

    public static final List<Role> ALL_AVAILABLE_ROLES = Arrays.asList(values());

    public static List<Role> valuesAsList() {
        return ALL_AVAILABLE_ROLES;
    }
}
