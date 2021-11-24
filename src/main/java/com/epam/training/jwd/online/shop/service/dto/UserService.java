package com.epam.training.jwd.online.shop.service.dto;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.epam.training.jwd.online.shop.dao.entity.User;
import com.epam.training.jwd.online.shop.dao.exception.DaoException;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.dao.field.UserField;
import com.epam.training.jwd.online.shop.dao.impl.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static at.favre.lib.crypto.bcrypt.BCrypt.MIN_COST;

public class UserService implements UserServiceImpl{
    private final Logger LOGGER = LogManager.getLogger(UserService.class);
    private static volatile UserService instance;
    private final UserDao userDao;
    private final BCrypt.Hasher hasher;
    private final BCrypt.Verifyer verifyer;


    private UserService(UserDao userDao) {
        this.userDao = userDao;
        this.hasher = BCrypt.withDefaults();
        this.verifyer = BCrypt.verifyer();
    }

    public static UserService getInstance() {
        UserService localInstance = instance;
        if (localInstance == null) {
            synchronized (UserService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new UserService(UserDao.INSTANCE);
                }
            }
        }
        return localInstance;
    }

    public Optional<String> registerUser(User user) throws ServiceException {
        final char[] rawPassword = user.getPassword().toCharArray();
        final String encryptedPassword = hasher.hashToString(MIN_COST, rawPassword);
        if (!findByUsername(user.getUsername()).isPresent()) {
            if (!findByEmail(user.getEmail()).isPresent()) {
                try {
                    userDao.save(new User(user.getId(), user.getUsername(), user.getFirstName(),
                            user.getLastName(), user.getEmail(), encryptedPassword, user.getRole(), user.isBlocked(), user.getPhoneNumber()));
                    return Optional.empty();
                } catch (DaoException e) {
                    LOGGER.error("Failed to register user: " + user);
                    throw new ServiceException(e);
                }
            }
            return Optional.of("serverMessage.emailAlreadyTaken");
        }
        return Optional.of("serverMessage.usernameAlreadyTaken");
    }

    public Optional<String> loginUser(String username, String password, Map<String, Object> session) throws ServiceException {
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

    public void updateUser(User user) throws ServiceException {
        try {
            userDao.update(user);
        } catch (DaoException e) {
            LOGGER.error("Failed to update user");
            throw new ServiceException(e);
        }
    }

    public List<User> findAllUsers() throws ServiceException {
        List<User> users;
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Failed to find all users in database");
            throw new ServiceException(e);
        }
        return users;
    }

    public Optional<User> findById(int id) throws ServiceException {
        return findUserByUniqueField(String.valueOf(id), UserField.ID);
    }

    public Optional<User> findByUsername(String username) throws ServiceException {
        return findUserByUniqueField(username, UserField.USERNAME);
    }

    public Optional<User> findByEmail(String email) throws ServiceException {
        return findUserByUniqueField(email, UserField.EMAIL);
    }

    public Optional<User> findUserByUniqueField(String searchableField, UserField nameOfField) throws ServiceException {
        List<User> users;
        try {
            users = userDao.findByField(searchableField, nameOfField);
        } catch (DaoException e) {
            LOGGER.error("Failed on a user search with field = " + nameOfField);
            throw new ServiceException("Failed search user by unique field", e);
        }
        return ((users.size() > 0) ? Optional.of(users.get(0)) : Optional.empty());
    }
}