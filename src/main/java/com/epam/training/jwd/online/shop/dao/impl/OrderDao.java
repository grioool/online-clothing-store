package com.epam.training.jwd.online.shop.dao.impl;

import com.epam.training.jwd.online.shop.dao.AbstractDao;
import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPool;
import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPoolImpl;
import com.epam.training.jwd.online.shop.dao.entity.*;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.field.OrderField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class OrderDao extends AbstractDao<Order> {

    public static final OrderDao INSTANCE = new OrderDao(ConnectionPoolImpl.getInstance());

    private static final String SQL_FIND_ALL = "SELECT order.id, payment_method, status_id, order_cost, delivery_date," +
            " order_date, delivery_country_id, user_id, delivery_town_id FROM order" +
            " INNER JOIN order_status ON order_status.id = order.status_id" +
            " INNER JOIN payment_method ON payment_method.id = order.payment_method" +
            " INNER JOIN \"user\" ON user.id = order.user_id" +
            " INNER JOIN country ON country.id = order.delivery_country_id" +
            " INNER JOIN town ON town.id = order.delivery_town_id";

    private static final String SQL_SAVE_ORDER = "INSERT INTO order(payment_method, status_id, order_cost, delivery_date," +
            " order_date, delivery_country_id, user_id, delivery_town_id)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_ORDER = "UPDATE order SET payment_method = ?, status_id = ?, order_cost = ?, delivery_date = ?," +
            "order_date = ?, delivery_country_id = ?, user_id = ?, delivery_town_id = ? WHERE id = ?";

    private static final String SQL_DELETE_ORDER = "DELETE FROM order WHERE id = ?";

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
        preparedStatement.setInt(1, order.getPaymentMethod().ordinal() + 1);
        preparedStatement.setInt(2, order.getOrderStatus().ordinal() + 1);
        preparedStatement.setTimestamp(3, Timestamp.valueOf(order.getDeliveryDate()));
        preparedStatement.setTimestamp(4, Timestamp.valueOf(order.getOrderDate()));
        preparedStatement.setInt(5, order.getDeliveryCountry().ordinal() + 1);
        preparedStatement.setInt(6, order.getUser().getId());
        preparedStatement.setInt(7, order.getDeliveryTown().ordinal() + 1);
    }

    @Override
    protected Optional<Order> parseResultSet(ResultSet resultSet) throws SQLException, DaoException {
        Order order = Order.builder()
                .withId(resultSet.getInt(OrderField.ID.getField()))
                .withPaymentMethod(PaymentMethod.getById(resultSet.getInt(OrderField.PAYMENT_METHOD.getField())))
                .withOrderStatus(OrderStatus.getById(resultSet.getInt(OrderField.STATUS.getField())))
                .withProducts(ProductDao.INSTANCE.findProductsInOrder(resultSet.getInt(OrderField.PRODUCTS.getField())))
                .withDeliveryDate(resultSet.getTimestamp(OrderField.DELIVERY_DATE.getField()).toLocalDateTime())
                .withOrderDate(resultSet.getTimestamp(OrderField.ORDER_DATE.getField()).toLocalDateTime())
                .withDeliveryCountry(Country.getById(resultSet.getInt(OrderField.COUNTRY.getField())))
                .withUser(UserDao.INSTANCE.findUserById(resultSet.getInt(OrderField.USER.getField())))
                .withDeliveryTown(Town.getById(resultSet.getInt(OrderField.TOWN.getField())))
                .build();
        return Optional.of(order);
    }
}
