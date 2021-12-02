package com.epam.training.jwd.online.shop.service;

import com.epam.training.jwd.online.shop.dao.entity.Brand;
import com.epam.training.jwd.online.shop.dao.entity.Product;
import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;
import com.epam.training.jwd.online.shop.dao.field.ProductField;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface provide product service operation
 *
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public interface ProductService {

    List<Product> findAllProducts();

    Optional<Product> findProductById(Integer productId);

    Optional<Product> findProductByName(String name);

    List<Product> findProductsByCategoryId(Integer categoryId);

    Optional<String> saveProduct(Product product);

    Map<Product, Integer> receiveProducts(Map<Integer, Integer> cart);

    BigDecimal calcOrderCost(Map<Product, Integer> productsInCart);

    Optional<String> editProduct(Integer id, String productName, String productDescription, Brand brand, Double price, Integer article);

    void deleteProduct(Product product);

    void deleteAllProductsByCategoryId(Integer categoryId);

    Optional<Product> findProductByUniqueField(String searchableField, ProductField nameOfField);

    void updateProduct(Product product);
}
