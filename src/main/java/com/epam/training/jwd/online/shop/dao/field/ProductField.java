package com.epam.training.jwd.online.shop.dao.field;

public enum ProductField {
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
