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
    private static Product.Builder productBuilder;
    private static final String TEST_PRODUCT_PRODUCT_NAME = "black_shoes";

    @BeforeAll
    public static void beforeAll() throws DaoException {
        ConnectionPoolImpl.getInstance().init();
        productDao = ProductDao.INSTANCE;
        productCategoryDao = ProductCategoryDao.INSTANCE;
        productBuilder = Product.builder()
                .withProductName("black_shoes")
                .withPrice(33.3)
                .withBrand(Brand.GRIOOOL)
                .withProductCategory(productCategoryDao.findByName("Shoes"))
                .withNameOfImage("blackShoesFile")
                .withProductDescription("Black shoes")
                .withArticle(299299);
    }

    @Test
    void crudTest() throws DaoException {
        Product product = productBuilder.build();
        productDao.save(product);
        assertNotNull(product.getId());
        assertNotNull(productDao.findByProductName(TEST_PRODUCT_PRODUCT_NAME));
        product.setBrand(Brand.GRIOOOL);
        productDao.update(product);
        assertEquals(Brand.GRIOOOL, productDao.findByProductName(TEST_PRODUCT_PRODUCT_NAME).getBrand());
        productDao.delete(product);
        assertNull(productDao.findByProductName(TEST_PRODUCT_PRODUCT_NAME));
    }


}