package com.epam.training.jwd.online.shop.dao.field;

public enum BasketField {
    ID(" WHERE user.id = "),
    USER(" WHERE user_id = ?"),
    PRODUCT,
    AMOUNT;

    private String query;

    BasketField(String query) {
        this.query = query;
    }

    BasketField() {}

    public String getQuery() {
        return query;
    }
}
