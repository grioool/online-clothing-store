package com.epam.jwd.dao.model;

import java.util.Objects;

public class User {
    private final Long id;
    private final String name;
    private final String password;
    private final Role role;

    public User(String name, String password) {
        this(null, name, password, Role.USER);
    }

    public User(String name, String password, Role role) {
        this(null, name, password, role);
    }

    public User(Long id, String name, String password, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(name, user.name)
                && Objects.equals(password, user.password)
                && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
