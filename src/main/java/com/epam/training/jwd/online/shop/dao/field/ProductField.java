package com.epam.training.jwd.online.shop.dao.field;

import com.epam.training.jwd.online.shop.dao.entity.Product;

/**
 * The class representation withName {@link Product} fields
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public enum ProductField implements EntityField<Product> {
    ID("id"),
    NAME("product_name"),
    PRICE("price"),
    BRAND("brand_id"),
    CATEGORY("category_id"),
    NAME_OF_IMAGE("name_of_image"),
    DESCRIPTION("product_description"),
    ARTICLE( "article");

    private String query;

    ProductField(String query) {
        this.query = query;
    }

    ProductField() {}

    public String getField() {
        return query;
    }
}
