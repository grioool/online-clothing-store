package com.epam.training.jwd.online.shop.dao.field;

public enum BasketField {
    ID("id"),
    USER("user_id"),
    PRODUCT("product_id"),
    AMOUNT("amount");

    private String query;

    BasketField(String query) {
        this.query = query;
    }

    BasketField() {}

    public String getQuery() {
        return query;
    }
}
