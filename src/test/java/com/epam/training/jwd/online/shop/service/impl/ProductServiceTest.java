package com.epam.training.jwd.online.shop.service.impl;

import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPoolImpl;
import com.epam.training.jwd.online.shop.dao.entity.Brand;
import com.epam.training.jwd.online.shop.dao.entity.Product;
import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.dao.impl.ProductCategoryDao;
import com.epam.training.jwd.online.shop.service.ProductService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceTest {
    private static ProductService productService;
    private static Product.Builder productBuilder;


    @BeforeAll
    static void setUp() throws ClassNotFoundException {
        ConnectionPoolImpl.getInstance().init();
        productService = ProductServiceImpl.getInstance();

       ProductCategoryDao productCategory = ProductCategoryDao.INSTANCE;
        productBuilder = Product.builder()
                .withProductName("black_shoes")
                .withPrice(BigDecimal.valueOf(33.3))
                .withBrand(Brand.GRIOOOL)
                .withProductCategory(productCategory.findByName("Shoes"))
                .withNameOfImage("blackShoesFile")
                .withProductDescription("Black shoes")
                .withArticle(299299);
    }

    @Test
    public void createProduct_ShouldReturnEmptyOptional() throws ServiceException {
        assertFalse(productService.saveProduct(productBuilder.build()).isPresent());
    }

    @Test
    public void createProduct_ShouldReturnNameAlreadyTakenMessage() throws ServiceException {
        assertTrue(productService.saveProduct(productBuilder.build()).isPresent());
    }

    @Test
    public void calcOrderCost_ShouldReturn50() {
        Map<Product, Integer> cart = new HashMap<>();
        productBuilder.withPrice(BigDecimal.TEN);
        cart.put(productBuilder.build(), 5);

        assertEquals(productService.calcOrderCost(cart), BigDecimal.valueOf(50));
    }


    @AfterAll
    static void tearDown() {
        Optional<Product> productOptional = productService.findProductByName(productBuilder.build().getProductName());
        productOptional.ifPresent(product -> productService.deleteProduct(product));
        ConnectionPoolImpl.getInstance().shutDown();
    }
}