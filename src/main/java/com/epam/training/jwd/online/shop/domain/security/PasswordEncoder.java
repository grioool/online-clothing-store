package com.epam.training.jwd.online.shop.domain.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

import static at.favre.lib.crypto.bcrypt.BCrypt.MIN_COST;

/**
 * The class provide encrypt user password
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class PasswordEncoder {

    public static String encryptPassword(String password) {
        return BCrypt.withDefaults().hashToString(MIN_COST, password.toCharArray());
    }
}
