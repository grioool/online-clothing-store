package com.epam.training.jwd.online.shop.service.dto;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.epam.training.jwd.online.shop.dao.entity.User;
import com.epam.training.jwd.online.shop.dao.exception.EntityNotFoundException;
import com.epam.training.jwd.online.shop.dao.storage.UserStorage;

import java.util.List;
import static at.favre.lib.crypto.bcrypt.BCrypt.MIN_COST;
import static java.nio.charset.StandardCharsets.UTF_8;

public enum SimpleUserService implements UserService {
    INSTANCE;

    private static final String NOT_FOUND_MSG = "User with such login does not exist. Login: %s";

    private final UserStorage storage;
    private final BCrypt.Hasher hasher;
    private final BCrypt.Verifyer verifyer;

    SimpleUserService() {
        this.storage = UserStorage.inMemory();
        this.hasher = BCrypt.withDefaults();
        this.verifyer = BCrypt.verifyer();
    }

    @Override
    public User create(User user) {
        final char[] rawPassword = user.getPassword().toCharArray();
        final String encryptedPassword = hasher.hashToString(MIN_COST, rawPassword);
        return storage.save(new User(user.getId(), user.getUsername(), user.getFirstName(),
                user.getLastName(), user.getEmail(), encryptedPassword, user.getRole(), user.isBlocked(), user.getPhoneNumber()));
    }

    @Override
    public boolean canLogIn(User user) {
        final byte[] enteredPassword = user.getPassword().getBytes(UTF_8);
        try {
            final User persistedUser = this.findByLogin(user.getUsername());
            final byte[] encryptedPasswordFromDb = persistedUser.getPassword().getBytes(UTF_8);
            return verifyer.verify(enteredPassword, encryptedPasswordFromDb).verified;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    @Override
    public User findByLogin(String login) throws EntityNotFoundException {
        return storage.findByName(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format(NOT_FOUND_MSG, login)));
    }

    @Override
    public List<User> findAll() {
        return storage.findAll();
    }

    @Override
    public void clear() {
        this.storage.clear();
    }
}
