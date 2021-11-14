package com.epam.training.jwd.online.shop.dao.field;

import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;

public enum ProductCategoryField implements EntityField<ProductCategory> {
    ID("id"),
    NAME("type_name"),
    FILENAME("img_filename");

    private String query;

    ProductCategoryField(String query) {
        this.query = query;
    }

    ProductCategoryField() {}

    public String getQuery() {
        return query;
    }

    @Override
    public String getField() {
        return query;
    }
}
