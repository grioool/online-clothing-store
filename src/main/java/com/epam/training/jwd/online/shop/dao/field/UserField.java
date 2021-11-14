package com.epam.training.jwd.online.shop.dao.field;

import com.epam.training.jwd.online.shop.dao.entity.User;

public enum UserField implements EntityField<User> {
    ID("id"),
    USERNAME("username"),
    PASSWORD("password"),
    FIRST_NAME("first_name"),
    LAST_NAME("last_name"),
    EMAIL("email"),
    IS_BLOCK("is_blocked"),
    PHONE_NUMBER("phone_number"),
    ROLE("role_id");

    private String query;

    UserField(String query) {
        this.query = query;
    }

    UserField() {}

    public String getQuery() {
        return query;
    }

    @Override
    public String getField() {
        return query;
    }
}
