package com.epam.training.jwd.online.shop.util;

import com.epam.training.jwd.online.shop.service.exception.ApplicationStartException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The class provide reading properties files
 *
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class PropertyReaderUtil {

    private PropertyReaderUtil() {
    }

    /**
     * Read properties file
     *
     * @param propertiesName name withName properties file without extension
     * @throws ApplicationStartException if properties file was not found
     * @return {@link Properties}
     */

    public static Properties readProperties(String propertiesName) {
        Properties properties = new Properties();
        InputStream inputStream;
        try {
            inputStream = PropertyReaderUtil.class.getClassLoader().getResourceAsStream(propertiesName + ".properties");
            properties.load(inputStream);
        } catch (IOException e) {
            throw new ApplicationStartException("Failed to load " + propertiesName + ".properties file");
        }
        return properties;
    }
}
