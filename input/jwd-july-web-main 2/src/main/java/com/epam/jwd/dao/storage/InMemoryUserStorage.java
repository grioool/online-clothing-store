package com.epam.jwd.dao.storage;

import com.epam.jwd.dao.model.Role;
import com.epam.jwd.dao.model.User;
import com.epam.jwd.service.exception.BusinessValidationException;
import com.epam.jwd.service.exception.UniqueConstraintViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

public enum InMemoryUserStorage implements UserStorage {
    INSTANCE;

    private static final String DUPLICATE_USER_MSG = "User with such name already exists. Name: %s";
    private static final String UNAUTHORIZED_MSG = "Unauthorized user can not be saved to storage";

    private final Map<Long, User> content;
    private final AtomicLong userAmount;

    InMemoryUserStorage() {
        this.content = new ConcurrentHashMap<>();
        this.userAmount = new AtomicLong(0);
    }

    @Override
    public List<User> findAll() {
       return new ArrayList<>(content.values());
    }

    @Override
    public User save(User user) {
        if (userWithSuchNameAlreadyExists(user.getName())) {
            throw new UniqueConstraintViolationException(String.format(DUPLICATE_USER_MSG, user.getName()));
        }
        if (Role.UNAUTHORIZED.equals(user.getRole())) {
            throw new BusinessValidationException(UNAUTHORIZED_MSG);
        }
        final long id = userAmount.incrementAndGet();
        return content.put(id, new User(id, user.getName(), user.getPassword(), user.getRole()));
    }

    @Override
    public Optional<User> findByName(String name) {
        final Predicate<User> userNameEqualsGiven = user -> user.getName().equals(name);
        return content.values()
                .stream()
                .filter(userNameEqualsGiven)
                .findAny();
    }

    @Override
    public void clear() {
        this.content.clear();
        userAmount.set(0);
    }

    private boolean userWithSuchNameAlreadyExists(String name) {
        return this.content.values()
                .stream()
                .map(User::getName)
                .anyMatch(name::equals);
    }
}
