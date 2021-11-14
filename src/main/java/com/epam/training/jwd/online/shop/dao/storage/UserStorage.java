package com.epam.training.jwd.online.shop.dao.storage;



import com.epam.training.jwd.online.shop.dao.entity.User;

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
