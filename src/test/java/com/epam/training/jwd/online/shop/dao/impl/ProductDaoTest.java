package com.epam.training.jwd.online.shop.dao.impl;

import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPoolImpl;
import com.epam.training.jwd.online.shop.dao.entity.*;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.field.ProductField;
import com.epam.training.jwd.online.shop.dao.field.UserField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {

    private static ProductCategory productCategory;
    private static ProductCategoryDao productCategoryDao;
    private static ProductDao productDao;
    private static Product.Builder product;
    private static List<Product> products;

    @BeforeAll
    public static void beforeAll() throws DaoException {
        ConnectionPoolImpl.getInstance().init();
        productCategory = ProductCategory.builder()
                .withId(1)
                .withCategoryName("Shoes")
                .withImgFileName("shoes.png")
                .build();
        productDao = ProductDao.INSTANCE;
        productCategoryDao = ProductCategoryDao.INSTANCE;
        product = Product.builder()
                .withId(1)
                .withProductName("black_shoes")
                .withPrice(33.3)
                .withBrand(Brand.GRIOOOL)
                .withProductCategory(productCategory)
                .withNameOfImage("blackShoesFile")
                .withProductDescription("Black shoes")
                .withArticle(299299);
    }

    @Test
    void save() throws DaoException {
        productCategoryDao.save(productCategory);
        productDao.save(product.build());
        products = productDao.findByField("black_shoes", ProductField.NAME);
        product.withId(products.get(0).getId());
        assertTrue(products.contains(product.build()));
    }

    @Test
    void update() throws DaoException {
        product.withProductName("black_shoes");
        productDao.update(product.build());
        products = productDao.findByField("black_shoes", ProductField.NAME);
        assertTrue(products.contains(product.build()));
    }

    @Test
    void delete() throws DaoException {
        productDao.delete(products.get(0).getId());
        products = productDao.findByField("black_shoes", ProductField.NAME);
        assertFalse(products.contains(product.build()));
    }


    @Test
    void findProductsInOrder() {
    }

    @Test
    void findProductCategoryById() {
    }

}