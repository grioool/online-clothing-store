package com.epam.training.jwd.online.shop.dao.field;

import com.epam.training.jwd.online.shop.dao.entity.Order;

public enum OrderField implements EntityField<Order> {
    ID("id"),
    PAYMENT_METHOD("payment_method"),
    STATUS("status_id"),
    PRODUCTS("products"),
    DELIVERY_DATE("delivery_date"),
    ORDER_DATE("order_date"),
    COUNTRY("delivery_country_id"),
    USER("user_id"),
    TOWN("delivery_town_id");

    private String query;

    OrderField(String query) {
        this.query = query;
    }

    OrderField() {}

    public String getField() {
        return query;
    }
}
