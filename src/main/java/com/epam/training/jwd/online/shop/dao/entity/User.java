package com.epam.training.jwd.online.shop.dao.entity;

import java.util.Objects;

public class User extends AbstractEntity<Integer> {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
    private boolean isBlocked;
    private String phoneNumber;

    public User(){}

    public User(String username, String password) {
        this(null, username, null, null, null, password, UserRole.USER, false, null);
    }

    public User(String username, String password, UserRole role) {
        this(null, username, null, null, null, password, role, false, null);
    }

    public User(Integer id, String username, String firstName, String lastName, String email, String password, UserRole role, boolean isBlocked, String phoneNumber) {
        super(id);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isBlocked = isBlocked;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isBlocked == user.isBlocked && username.equals(user.username) && firstName.equals(user.firstName) && lastName.equals(user.lastName) && email.equals(user.email) && role.equals(user.role) && phoneNumber.equals(user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, firstName, lastName, email, role, isBlocked, phoneNumber);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", isBlocked=" + isBlocked +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private UserRole role;
        private boolean isBlocked;
        private String phoneNumber;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withRole(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withIsBlocked(boolean isBlocked) {
            this.isBlocked = isBlocked;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public User build() {
            return new User(this.id,
                    this.username,
                    this.firstName,
                    this.lastName,
                    this.email,
                    this.password,
                    this.role,
                    this.isBlocked,
                    this.phoneNumber);
        }
    }
}
