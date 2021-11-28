package com.epam.training.jwd.online.shop.dao;

import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPool;
import com.epam.training.jwd.online.shop.dao.connectionpool.ConnectionPoolImpl;
import com.epam.training.jwd.online.shop.dao.entity.AbstractEntity;
import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;
import com.epam.training.jwd.online.shop.dao.entity.User;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.field.EntityField;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractDao<T extends AbstractEntity<Integer>> implements Dao<T> {
    protected final ConnectionPool connectionPool;

    protected AbstractDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    protected abstract String getFindAllSql();

    protected abstract String getSaveSql();

    protected abstract String getUpdateSql();

    protected abstract String getDeleteSql();

    protected abstract void prepareSaveStatement(PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract void prepareUpdateStatement(PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract Optional<T> parseResultSet(ResultSet resultSet) throws SQLException, DaoException;

    protected abstract void prepareAllStatement(PreparedStatement preparedStatement, final T entity) throws SQLException;

    @Override
    public void save(final T entity) {
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(getSaveSql(), Statement.RETURN_GENERATED_KEYS);
            prepareAllStatement(preparedStatement, entity);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getInt(1));
            }
        } catch (InterruptedException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public T update(final T entity) {
        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getUpdateSql())) {
                prepareUpdateStatement(preparedStatement, entity);
                preparedStatement.execute();
            }
        } catch (SQLException | InterruptedException e) {
            throw new DaoException(e);
        }
        return entity;
    }

    @Override
    public void delete(T entity) {
        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getDeleteSql())) {
                preparedStatement.setInt(1, entity.getId());
                preparedStatement.execute();
            }
        } catch (SQLException | InterruptedException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<T> findAll() {
        List<T> entitiesList = new ArrayList<>();
        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getFindAllSql())) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()) {
                    Optional<T> entityOptional = parseResultSet(resultSet);
                    entityOptional.ifPresent(entitiesList::add);
                }
            }
        } catch (SQLException | InterruptedException e) {
            throw new DaoException(e);
        }
        return entitiesList;
    }

    public List<T> findByField(String searchableField, EntityField<T> fieldName) {
        List<T> list = new ArrayList<>();
        try (Connection connection = ConnectionPoolImpl.getInstance().takeConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getFindAllSql() + " WHERE " + fieldName.getField() + " = ?")) {
                preparedStatement.setString(1, searchableField);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Optional<T> optionalT = parseResultSet(resultSet);
                    optionalT.ifPresent(list::add);
                }
            }
        } catch (SQLException | InterruptedException e) {
            throw new DaoException(e);
        }
        return list;
    }

    public List<T> findByField(Integer searchableField, EntityField<T> fieldName) {
        List<T> list = new ArrayList<>();
        try (Connection connection = ConnectionPoolImpl.getInstance().takeConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getFindAllSql() + " WHERE " + fieldName.getField() + " = ?")) {
                preparedStatement.setInt(1, searchableField);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Optional<T> optionalT = parseResultSet(resultSet);
                    optionalT.ifPresent(list::add);
                }
            }
        } catch (SQLException | InterruptedException e) {
            throw new DaoException(e);
        }
        return list;
    }

    public T findById(Integer id) {
        try (Connection connection = ConnectionPoolImpl.getInstance().takeConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getFindAllSql() + " WHERE id = ?")) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) return parseResultSet(resultSet).orElse(null);
            }
        } catch (SQLException | InterruptedException e) {
            throw new DaoException(e);
        }
        return null;
    }
}

