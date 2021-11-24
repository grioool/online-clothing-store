package com.epam.training.jwd.online.shop.service.dto;

import com.epam.training.jwd.online.shop.dao.entity.User;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.dao.field.UserField;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserServiceImpl {

    Optional<String> registerUser(User user) throws ServiceException;

    Optional<User> findById(int id) throws ServiceException;

    List<User> findAllUsers() throws ServiceException;

    Optional<String> loginUser(String username, String password, Map<String, Object> session) throws ServiceException;

    void updateUser(User user) throws ServiceException;

    Optional<User> findByUsername(String username) throws ServiceException;

    Optional<User> findByEmail(String email) throws ServiceException;

    Optional<User> findUserByUniqueField(String searchableField, UserField nameOfField) throws ServiceException;

}
