package com.epam.training.jwd.online.shop.service.dto;

import com.epam.training.jwd.online.shop.dao.entity.UserRole;

import java.util.Objects;

/**
 * The class representation {@link com.epam.training.jwd.online.shop.dao.entity.User} in session
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class UserDto {
    private final int id;
    private final UserRole role;
    private final boolean isBlocked;

    public UserDto(int id, UserRole role, boolean isBlocked) {
        this.id = id;
        this.role = role;
        this.isBlocked = isBlocked;
    }

    public int getId() {
        return id;
    }

    public UserRole getRole() {
        return role;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id == userDto.id && role == userDto.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }
}
