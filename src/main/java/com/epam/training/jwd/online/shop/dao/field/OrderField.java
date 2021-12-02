package com.epam.training.jwd.online.shop.dao.field;

import com.epam.training.jwd.online.shop.dao.entity.Order;

/**
 * The class representation withName {@link Order} fields
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public enum OrderField implements EntityField<Order> {
    ID("id"),
    PAYMENT_METHOD("payment_method"),
    STATUS("status_id"),
    PRODUCTS("products"),
    DELIVERY_DATE("delivery_date"),
    ORDER_DATE("order_date"),
    COUNTRY("delivery_country_id"),
    USER("user_id"),
    TOWN("delivery_town_id"),
    ORDER_COST("order_cost");

    private final String query;

    OrderField(String query) {
        this.query = query;
    }

    public String getField() {
        return query;
    }
}
