package com.epam.training.jwd.online.shop.service.dto;

import com.epam.training.jwd.online.shop.dao.entity.User;
import java.util.List;

public interface UserService {

    User create(User user);

    boolean canLogIn(User user);

    User findByLogin(String login);

    List<User> findAll();

    void clear();

    static UserService simple() {
        return SimpleUserService.INSTANCE;
    }

}
