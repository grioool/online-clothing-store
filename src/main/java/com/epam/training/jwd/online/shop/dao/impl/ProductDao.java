package com.epam.training.jwd.online.shop.dao.impl;

import com.epam.training.jwd.online.shop.dao.AbstractDao;
import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPool;
import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPoolImpl;
import com.epam.training.jwd.online.shop.dao.entity.*;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.field.ProductField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * The class provide CRUD operations for {@link Product}
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class ProductDao extends AbstractDao<Product> {

    public static final ProductDao INSTANCE = new ProductDao(ConnectionPoolImpl.getInstance());
    private final Logger logger = LogManager.getLogger(ProductDao.class);

    private static final String SQL_FIND_ALL = "SELECT product.id, product_name, price, name_of_image, product_description," +
            " article, brand_id, category_id FROM product" +
            " INNER JOIN brand ON brand.id = product.brand_id" +
            " INNER JOIN product_category ON product_category.id = product.category_id";

    private static final String SQL_SAVE_PRODUCT = "INSERT INTO product (product_name, price, brand_id, category_id, " +
            " name_of_image, product_description, article)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_PRODUCT = "UPDATE product SET product_name = ?, price = ?, brand_id = ?, category_id = ?," +
            "name_of_image = ?, product_description = ?, article = ? WHERE id = ?";

    private static final String SQL_DELETE_PRODUCT = "DELETE FROM product WHERE id = ?";

    private static final String SQL_FIND_BY_ORDER_ID = "SELECT id, product_name, price, name_of_image, product_description, " +
            "category_id, article, brand_id FROM product INNER JOIN product_in_order as op on product.id = op.product_id " +
            "WHERE op.order_id = ?";

    private final ConnectionPool pool = ConnectionPoolImpl.getInstance();

    protected ProductDao(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public String getFindAllSql() {
        return SQL_FIND_ALL;
    }

    @Override
    public String getSaveSql() {
        return SQL_SAVE_PRODUCT;
    }

    @Override
    public String getUpdateSql() {
        return SQL_UPDATE_PRODUCT;
    }

    @Override
    public String getDeleteSql() {
        return SQL_DELETE_PRODUCT;
    }


    @Override
    protected void prepareSaveStatement(PreparedStatement preparedStatement, Product entity) throws SQLException {
        prepareAllStatement(preparedStatement, entity);
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement preparedStatement, Product entity) throws SQLException {
        prepareAllStatement(preparedStatement, entity);
        preparedStatement.setInt(8, entity.getId());
    }

    @Override
    protected void prepareAllStatement(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setString(1, product.getProductName());
        preparedStatement.setBigDecimal(2, product.getPrice());
        preparedStatement.setInt(3, product.getBrand().getId());
        preparedStatement.setInt(4, product.getCategory().getId());
        preparedStatement.setString(5, product.getImgFileName() );
        preparedStatement.setString(6, product.getProductDescription());
        preparedStatement.setInt(7, product.getArticle());
    }

    @Override
    protected Optional<Product> parseResultSet(ResultSet resultSet) throws SQLException, DaoException {
        Product product = Product.builder()
                .withId(resultSet.getInt(ProductField.ID.getField()))
                .withProductName(resultSet.getString(ProductField.NAME.getField()))
                .withPrice(resultSet.getBigDecimal(ProductField.PRICE.getField()))
                .withBrand(Brand.getById(resultSet.getInt(ProductField.BRAND.getField())))
                .withProductCategory(ProductCategoryDao.INSTANCE.findById(resultSet.getInt(ProductField.CATEGORY.getField())))
                .withNameOfImage(resultSet.getString(ProductField.NAME_OF_IMAGE.getField()))
                .withProductDescription(resultSet.getString(ProductField.DESCRIPTION.getField()))
                .withArticle(resultSet.getInt(ProductField.ARTICLE.getField()))
                .build();
        return Optional.of(product);
    }

    protected Map<Product, Integer> findProductsInOrder(Integer orderId) throws DaoException {
        Map<Product, Integer> products = new HashMap<>();
        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ORDER_ID)) {
                preparedStatement.setInt(1, orderId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Optional<Product> productOptional = parseResultSet(resultSet);
                    Integer amount = resultSet.getInt("amount");
                    productOptional.ifPresent(product -> products.put(product, amount));
                }
            }
        } catch (SQLException | InterruptedException e) {
            logger.error("Failed to find products in order by id.", e);
            throw new DaoException("Failed to find products in order by id.");
        }
        return products;
    }

    public Product findByProductName(String name) throws DaoException {
        return this.findByField(name, ProductField.NAME).stream().findFirst().orElse(null);
    }
}
