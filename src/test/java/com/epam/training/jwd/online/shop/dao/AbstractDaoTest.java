package com.epam.training.jwd.online.shop.dao;

import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPool;
import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPoolImpl;
import com.epam.training.jwd.online.shop.dao.entity.Brand;
import com.epam.training.jwd.online.shop.dao.entity.Product;
import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.field.ProductField;
import com.epam.training.jwd.online.shop.dao.impl.ProductDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.peer.ListPeer;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AbstractDaoTest {

    private static ProductCategory productCategory;
    private static ProductDao productDao;
    private static Product.Builder product;
    private static List<Product> products;

    @BeforeAll
    public static void beforeAll() {
        ConnectionPoolImpl.getInstance().init();
        productCategory = ProductCategory.builder()
                .withId(1)
                .withCategoryName("Shoes")
                .withImgFileName("shoes.png")
                .build();
        productDao = ProductDao.INSTANCE;
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
        productDao.save(product.build());
        products = productDao.findByField("product", ProductField.NAME);
        product.withId(products.get(0).getId());
        assertTrue(products.contains(product.build()));
    }

    @Test
    void update() throws DaoException {
        product.withProductName("updateName");
        productDao.update(product.build());
        products = productDao.findByField("updateName", ProductField.NAME);
        assertTrue(products.contains(product.build()));
    }

    @Test
    void delete() throws DaoException {
        productDao.delete(products.get(0).getId());
        products = productDao.findByField("updateName", ProductField.NAME);
        assertFalse(products.contains(product.build()));
    }

    @Test
    void findAll() {
    }

    @Test
    void findByField() {
    }

    @AfterAll
    public static void afterAll() {
        ConnectionPoolImpl.getInstance().shutDown();
    }
}