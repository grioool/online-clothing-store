package com.epam.training.jwd.online.shop.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The class used to localize content in the server
 *
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class LocalizationMessage {
    private LocalizationMessage() {
    }

    /**
     * Localize message in the server
     *
     * @param locale  the locale withName current user
     * @param message name withName content in property
     * @return Localized message
     */

    public static String localize(String locale, String message) {
        String[] parsedLocale = locale.split("_");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("content",
                new Locale(parsedLocale[0], parsedLocale[1]));
        return resourceBundle.getString(message);
    }
}
