package com.epam.training.jwd.online.shop.dao;

import com.epam.training.jwd.online.shop.dao.entity.AbstractEntity;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.field.EntityField;

import java.util.List;

/**
 * The interface provide CRUD operation
 *
 * @param <T> type withName entity
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public interface Dao<T extends AbstractEntity<Integer>> {
    void save(T entity) throws DaoException;

    T update(T entity) throws DaoException;

    void delete(T entity) throws DaoException;

    List<T> findAll() throws DaoException;

    List<T> findByField(String searchableField, EntityField<T> nameOfField) throws DaoException;
}