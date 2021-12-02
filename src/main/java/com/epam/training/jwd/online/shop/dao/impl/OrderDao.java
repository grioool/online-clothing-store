package com.epam.training.jwd.online.shop.dao.impl;

import com.epam.training.jwd.online.shop.dao.AbstractDao;
import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPool;
import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPoolImpl;
import com.epam.training.jwd.online.shop.dao.entity.*;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.field.OrderField;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The class provide CRUD operations for {@link Order}
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class OrderDao extends AbstractDao<Order> {

    public static final OrderDao INSTANCE = new OrderDao(ConnectionPoolImpl.getInstance());

    private static final String SQL_FIND_ALL = "SELECT id, payment_method, status_id, delivery_date," +
            " order_date, delivery_country_id, user_id, delivery_town_id FROM \"order\"";

    private static final String SQL_SAVE_ORDER = "INSERT INTO \"order\"(payment_method, status_id, delivery_date," +
            " order_date, delivery_country_id, user_id, delivery_town_id)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_ORDER = "UPDATE \"order\" SET payment_method = ?, status_id = ?, delivery_date = ?," +
            "order_date = ?, delivery_country_id = ?, user_id = ?, delivery_town_id = ? WHERE id = ?";

    private static final String SQL_DELETE_ORDER = "DELETE FROM \"order\" WHERE id = ?";

    private static final String SQL_CREATE_ORDER_PRODUCTS = "INSERT INTO product_in_order(order_id, product_id," +
            " amount) VALUES (?, ?, ?)";

    private static final String SQL_DELETE_ORDER_PRODUCTS = "DELETE FROM product_in_order where product_id = ?";


    public OrderDao(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    protected String getFindAllSql() {
        return SQL_FIND_ALL;
    }

    @Override
    protected String getSaveSql() {
        return SQL_SAVE_ORDER;
    }

    @Override
    protected String getUpdateSql() {
        return SQL_UPDATE_ORDER;
    }

    @Override
    protected String getDeleteSql() {
        return SQL_DELETE_ORDER;
    }

    @Override
    protected void prepareSaveStatement(PreparedStatement preparedStatement, Order entity) throws SQLException {
        prepareAllStatement(preparedStatement, entity);
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement preparedStatement, Order entity) throws SQLException {
        prepareAllStatement(preparedStatement, entity);
        preparedStatement.setInt(8, entity.getId());
    }

    @Override
    protected void prepareAllStatement(PreparedStatement preparedStatement, Order order) throws SQLException {
        preparedStatement.setInt(1, order.getPaymentMethod().getId());
        preparedStatement.setInt(2, order.getOrderStatus().getId());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(order.getDeliveryDate()));
        preparedStatement.setTimestamp(4, Timestamp.valueOf(order.getOrderDate()));
        preparedStatement.setInt(5, order.getDeliveryCountry().getId());
        preparedStatement.setInt(6, order.getUser().getId());
        preparedStatement.setInt(7, order.getDeliveryTown().getId());
    }
    @Override
    protected Optional<Order> parseResultSet(ResultSet resultSet) throws SQLException {
        Order order = Order.builder()
                .withId(resultSet.getInt(OrderField.ID.getField()))
                .withPaymentMethod(PaymentMethod.getById(resultSet.getInt(OrderField.PAYMENT_METHOD.getField())))
                .withOrderStatus(OrderStatus.getById(resultSet.getInt(OrderField.STATUS.getField())))
                .withProducts(ProductDao.INSTANCE.findProductsInOrder(resultSet.getInt(OrderField.ID.getField())))
                .withDeliveryDate(resultSet.getTimestamp(OrderField.DELIVERY_DATE.getField()).toLocalDateTime())
                .withOrderDate(resultSet.getTimestamp(OrderField.ORDER_DATE.getField()).toLocalDateTime())
                .withDeliveryCountry(Country.getById(resultSet.getInt(OrderField.COUNTRY.getField())))
                .withUser(UserDao.INSTANCE.findById(resultSet.getInt(OrderField.USER.getField())))
                .withDeliveryTown(Town.getById(resultSet.getInt(OrderField.TOWN.getField())))
                .withProducts(ProductDao.INSTANCE.findProductsInOrder(resultSet.getInt("id")))
                .withOrderCost(resultSet.getBigDecimal(OrderField.ORDER_COST.getField()))
                .build();
        return Optional.of(order);
    }

    public List<Order> findByUser(User user) {
        return findByField(user.getId(), OrderField.USER);
    }

    @Override
    public void save(Order entity) {
        try (Connection connection = connectionPool.takeConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatementCreateOrder = null;
            PreparedStatement preparedStatementAddProducts = null;
            try {
                preparedStatementCreateOrder = connection.prepareStatement(SQL_SAVE_ORDER, Statement.RETURN_GENERATED_KEYS);
                prepareAllStatement(preparedStatementCreateOrder, entity);
                preparedStatementCreateOrder.executeUpdate();
                ResultSet resultSet = preparedStatementCreateOrder.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    for (Map.Entry<Product, Integer> entry : entity.getProducts().entrySet()) {
                        preparedStatementAddProducts = connection.prepareStatement(SQL_CREATE_ORDER_PRODUCTS);
                        preparedStatementAddProducts.setLong(1, id);
                        preparedStatementAddProducts.setInt(2, entry.getKey().getId());
                        preparedStatementAddProducts.setInt(3, entry.getValue());
                        preparedStatementAddProducts.execute();
                        preparedStatementAddProducts.close();
                    }
                    connection.commit();
                    preparedStatementCreateOrder.close();
                } else {
                    throw new SQLException();
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException(e);
            } finally {
                if (preparedStatementCreateOrder != null) {
                    preparedStatementCreateOrder.close();
                }
                if (preparedStatementAddProducts != null) {
                    preparedStatementAddProducts.close();
                }
            }
        } catch (SQLException | InterruptedException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Order entity) {
        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ORDER_PRODUCTS)) {
                preparedStatement.setInt(1, entity.getId());
                preparedStatement.execute();
            }
        } catch (SQLException | InterruptedException e) {
            throw new DaoException(e);
        }
    }

    public void deleteOrderProductByProductId(Integer productId) {
        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ORDER_PRODUCTS)) {
                preparedStatement.setInt(1, productId);
                preparedStatement.execute();
            }
        } catch (SQLException | InterruptedException e) {
            throw new DaoException(e);
        }
    }
}
