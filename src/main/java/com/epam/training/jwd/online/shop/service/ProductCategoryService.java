package com.epam.training.jwd.online.shop.service;

import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;
import com.epam.training.jwd.online.shop.dao.field.ProductCategoryField;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryService {

    List<ProductCategory> findAllProductCategories();

    Optional<ProductCategory> findProductCategoryById(Integer id);

    Optional<ProductCategory> findProductCategoryByName(String productTypeName);

    Optional<String> saveProductCategory(ProductCategory productCategory);

    Optional<String> editProductCategory(Integer id, String newFileName, String newProductName);

    void deleteProductCategory(ProductCategory productCategory);

    Optional<ProductCategory> findProductCategoryByUniqueField(String searchableField, ProductCategoryField nameOfField);

    void updateProductCategory(ProductCategory productType);
}
