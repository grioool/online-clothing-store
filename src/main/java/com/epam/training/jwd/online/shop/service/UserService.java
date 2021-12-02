package com.epam.training.jwd.online.shop.service;

import com.epam.training.jwd.online.shop.dao.entity.User;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.dao.field.UserField;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface provide user service operation
 *
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public interface UserService {

    Optional<String> registerUser(User user);

    Optional<User> findById(Integer id);

    List<User> findAllUsers();

    Optional<String> loginUser(String username, String password, Map<String, Object> session);

    void updateUser(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findUserByUniqueField(String searchableField, UserField nameOfField);

}
