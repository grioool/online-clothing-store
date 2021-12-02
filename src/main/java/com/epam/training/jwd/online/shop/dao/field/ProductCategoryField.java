package com.epam.training.jwd.online.shop.dao.field;

import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;

/**
 * The class representation withName {@link ProductCategory} fields
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public enum ProductCategoryField implements EntityField<ProductCategory> {
    ID("id"),
    NAME("category_name"),
    FILENAME("filename_of_image");

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
