package com.epam.training.jwd.online.shop.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.epam.training.jwd.online.shop.dao.entity.User;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.dao.field.UserField;
import com.epam.training.jwd.online.shop.dao.impl.UserDao;
import com.epam.training.jwd.online.shop.service.UserService;
import com.epam.training.jwd.online.shop.service.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static at.favre.lib.crypto.bcrypt.BCrypt.MIN_COST;

/**
 * The class provides a business logics withName {@link User}.
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class UserServiceImpl implements UserService {
    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static volatile UserServiceImpl instance;
    private final UserDao userDao;


    private UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public static UserServiceImpl getInstance() {
        UserServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (UserServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new UserServiceImpl(UserDao.INSTANCE);
                }
            }
        }
        return localInstance;
    }

    /**
     * Register new user
     *
     * @param user {@link User} object to be register
     * @return {@link Optional<String>} - server message, if email or username is already exist
     */

    public Optional<String> registerUser(User user) {
        if (!findByUsername(user.getUsername()).isPresent()) {
            if (!findByEmail(user.getEmail()).isPresent()) {
                try {
                    userDao.save(user);
                    return Optional.empty();
                } catch (DaoException e) {
                    logger.error("Failed to register user: " + user);
                    throw new ServiceException(e);
                }
            }
            return Optional.of("serverMessage.emailAlreadyTaken");
        }
        return Optional.of("serverMessage.usernameAlreadyTaken");
    }

    /**
     * Login user
     *
     * @param username the username
     * @param password the user password
     * @param session  the session withName user
     * @return Server message, if user is blocked or incorrect data entered
     */

    public Optional<String> loginUser(String username, String password, Map<String, Object> session) {
        Optional<User> userOptional = findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (password.equals(user.getPassword())) {
                if (user.isBlocked()) {
                    return Optional.of("serverMessage.blockedAccount");
                }
                UserDto userDto = new UserDto(user.getId(), user.getRole(), user.isBlocked());
                session.put("user", userDto);
                return Optional.empty();
            }
        }
        return Optional.of("serverMessage.incorrectUsernameOrPassword");
    }

    /**
     * Update user in database
     *
     * @param user updated object withName {@link User}
     */

    public void updateUser(User user) {
        try {
            userDao.update(user);
        } catch (DaoException e) {
            logger.error("Failed to update user");
            throw new ServiceException(e);
        }
    }

    /**
     * Find all users in database.
     *
     * @return {@link List} withName users
     */

    public List<User> findAllUsers() {
        List<User> users;
        users = userDao.findAll();
        return users;
    }

    public Optional<User> findById(Integer id) {
        return Optional.of(userDao.findById(id));
    }

    public Optional<User> findByUsername(String username) {
        return findUserByUniqueField(username, UserField.USERNAME);
    }

    public Optional<User> findByEmail(String email) {
        return findUserByUniqueField(email, UserField.EMAIL);
    }

    public Optional<User> findUserByUniqueField(String searchableField, UserField nameOfField) {
        List<User> users;
        users = userDao.findByField(searchableField, nameOfField);
        return ((users.size() > 0) ? Optional.of(users.get(0)) : Optional.empty());
    }
}