package com.epam.training.jwd.online.shop.dao.impl;

import com.epam.training.jwd.online.shop.dao.AbstractDao;
import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPool;
import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPoolImpl;
import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.field.ProductCategoryField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * The class provide CRUD operations for {@link ProductCategory}
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class ProductCategoryDao extends AbstractDao<ProductCategory> {

    public static final ProductCategoryDao INSTANCE = new ProductCategoryDao(ConnectionPoolImpl.getInstance());

    private static final String SQL_FIND_ALL = "SELECT product_category.id, category_name, filename_of_image FROM product_category";

    private static final String SQL_SAVE_PRODUCT_CATEGORY = "INSERT INTO product_category(category_name, filename_of_image)" +
            " VALUES (?, ?)";

    private static final String SQL_UPDATE_PRODUCT_CATEGORY = "UPDATE product_category SET category_name = ?, filename_of_image = ?, WHERE id = ?";

    private static final String SQL_DELETE_PRODUCT_CATEGORY = "DELETE FROM product_category WHERE id = ?";

    private final ConnectionPool pool = ConnectionPoolImpl.getInstance();

    protected ProductCategoryDao(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public String getFindAllSql() {
        return SQL_FIND_ALL;
    }

    @Override
    protected String getSaveSql() {
        return SQL_SAVE_PRODUCT_CATEGORY;
    }

    @Override
    protected String getUpdateSql() {
        return SQL_UPDATE_PRODUCT_CATEGORY;
    }

    @Override
    protected String getDeleteSql() {
        return SQL_DELETE_PRODUCT_CATEGORY;
    }

    @Override
    protected void prepareSaveStatement(PreparedStatement preparedStatement, ProductCategory entity) throws SQLException {
        prepareAllStatement(preparedStatement, entity);
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement preparedStatement, ProductCategory entity) throws SQLException {
        prepareAllStatement(preparedStatement, entity);
        preparedStatement.setInt(3, entity.getId());
    }

    @Override
    protected void prepareAllStatement(PreparedStatement preparedStatement, ProductCategory productCategory) throws SQLException {
        preparedStatement.setString(1, productCategory.getCategoryName());
        preparedStatement.setString(2, productCategory.getImgFileName());
    }

    @Override
    protected Optional<ProductCategory> parseResultSet(ResultSet resultSet) throws SQLException {
        ProductCategory productCategory = ProductCategory.builder()
                .withId(resultSet.getInt("id"))
                .withCategoryName(resultSet.getString("category_name"))
                .withImgFileName(resultSet.getString("filename_of_image"))
                .build();
        return Optional.of(productCategory);
    }

    public ProductCategory findByName(String name) throws DaoException {
        return findByField(name, ProductCategoryField.NAME).stream()
                .findFirst().orElse(null);
    }
}
