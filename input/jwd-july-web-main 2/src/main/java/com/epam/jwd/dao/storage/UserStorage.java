package com.epam.jwd.dao.storage;

import com.epam.jwd.dao.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    List<User> findAll();

    User save(User user);

    Optional<User> findByName(String name);

    void clear();

    static UserStorage inMemory() {
        return InMemoryUserStorage.INSTANCE;
    }

}
