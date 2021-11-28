package com.epam.training.jwd.online.shop.dao.impl;

import com.epam.training.jwd.online.shop.dao.AbstractDao;
import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPool;
import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPoolImpl;
import com.epam.training.jwd.online.shop.dao.entity.User;
import com.epam.training.jwd.online.shop.dao.entity.UserRole;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.field.UserField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDao extends AbstractDao<User> {

    public static UserDao INSTANCE = new UserDao(ConnectionPoolImpl.getInstance());

    private final Logger logger = LogManager.getLogger(UserDao.class);

    private static final String SQL_FIND_ALL = "SELECT id, username, password, first_name, last_name," +
            " email, is_blocked, phone_number, role_id FROM \"user\"";

    private static final String SQL_SAVE_USER = "INSERT INTO \"user\"(username, password, first_name, last_name," +
            " email, is_blocked, phone_number, role_id)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_USER = "UPDATE \"user\" SET username = ?, password = ?, first_name = ?, last_name = ?," +
            "email = ?, is_blocked = ?, phone_number = ?, role_id = ? WHERE id = ?";

    private static final String SQL_DELETE_USER = "DELETE FROM \"user\" WHERE id = ?";

    public UserDao(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public String getFindAllSql() {
        return SQL_FIND_ALL;
    }

    @Override
    public String getSaveSql() {
        return SQL_SAVE_USER;
    }

    @Override
    public String getUpdateSql() {
        return SQL_UPDATE_USER;
    }

    @Override
    public String getDeleteSql() {
        return SQL_DELETE_USER;
    }

    @Override
    protected void prepareAllStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getLastName());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setBoolean(6, user.isBlocked());
        preparedStatement.setString(7, user.getPhoneNumber());
        preparedStatement.setInt(8, user.getRole().getId());
    }

    @Override
    protected void prepareSaveStatement(PreparedStatement preparedStatement, User entity) throws SQLException {
        prepareAllStatement(preparedStatement, entity);
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement preparedStatement, User entity) throws SQLException {
        prepareAllStatement(preparedStatement, entity);
        preparedStatement.setInt(9, entity.getId());
    }

    @Override
    protected Optional<User> parseResultSet(ResultSet resultSet) throws SQLException {
        User user = User.builder()
                .withId(resultSet.getInt(UserField.ID.getField()))
                .withUsername(resultSet.getString(UserField.USERNAME.getField()))
                .withFirstName(resultSet.getString(UserField.FIRST_NAME.getField()))
                .withLastName(resultSet.getString(UserField.LAST_NAME.getField()))
                .withEmail(resultSet.getString(UserField.EMAIL.getField()))
                .withPassword(resultSet.getString(UserField.PASSWORD.getField()))
                .withRole(UserRole.getById(resultSet.getInt(UserField.ROLE.getField())))
                .withIsBlocked(resultSet.getBoolean(UserField.IS_BLOCK.getField()))
                .withPhoneNumber(resultSet.getString(UserField.PHONE_NUMBER.getField()))
                .build();
        return Optional.of(user);
    }

    public User findByUsername(String username) throws DaoException {
       return this.findByField(username, UserField.USERNAME).stream().findFirst().orElse(null);
    }
}

