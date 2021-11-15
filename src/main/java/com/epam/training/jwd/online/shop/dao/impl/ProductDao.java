package com.epam.training.jwd.online.shop.dao.impl;

import com.epam.training.jwd.online.shop.dao.AbstractDao;
import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPool;
import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPoolImpl;
import com.epam.training.jwd.online.shop.dao.entity.*;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.field.ProductCategoryField;
import com.epam.training.jwd.online.shop.dao.field.ProductField;
import com.epam.training.jwd.online.shop.dao.field.UserField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
            "name_of_img = ?, product_description = ?, article = ? WHERE id = ?";

    private static final String SQL_DELETE_PRODUCT = "DELETE FROM product WHERE id = ?";

    private static final String SQL_FIND_BY_ORDER_ID = "SELECT id, product_name, price, img_name, product_description, " +
            "type_id, amount FROM product INNER JOIN order_product as op on product.id = op.product_id " +
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
        preparedStatement.setDouble(2, product.getPrice());
        preparedStatement.setInt(3, product.getBrand().getId());
        preparedStatement.setInt(4, product.getCategory().getId());
        preparedStatement.setString(5, product.getNameOfImage() );
        preparedStatement.setString(6, product.getProductDescription());
        preparedStatement.setInt(7, product.getArticle());
    }

    @Override
    protected Optional<Product> parseResultSet(ResultSet resultSet) throws SQLException, DaoException {
        Product product = Product.builder()
                .withId(resultSet.getInt(ProductField.ID.getField()))
                .withProductName(resultSet.getString(ProductField.NAME.getField()))
                .withPrice(resultSet.getDouble(ProductField.PRICE.getField()))
                .withBrand(Brand.getById(resultSet.getInt(ProductField.BRAND.getField())))
                .withProductCategory(findProductCategoryById(resultSet.getInt(ProductField.CATEGORY.getField())))
                .withNameOfImage(resultSet.getString(ProductField.NAME_OF_IMAGE.getField()))
                .withProductDescription(resultSet.getString(ProductField.DESCRIPTION.getField()))
                .withArticle(resultSet.getInt(ProductField.ARTICLE.getField()))
                .build();
        return Optional.of(product);
    }

    protected ProductCategory findProductCategoryById(Integer id) throws DaoException {
        List<ProductCategory> productTypeList = ProductCategoryDao.INSTANCE.findByField(String.valueOf(id), ProductCategoryField.ID);
        if (productTypeList.size() < 1) {
            logger.warn("Failed to load product category.");
            throw new DaoException("Failed to load product type");
        }
        return productTypeList.get(0);
    }

    protected Map<Product, Integer> findProductsInOrder(int id) throws DaoException {
        Map<Product, Integer> products = new HashMap<>();
        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ORDER_ID)) {
                preparedStatement.setInt(1, id);
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
}
