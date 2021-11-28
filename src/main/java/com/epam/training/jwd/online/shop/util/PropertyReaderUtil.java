package com.epam.training.jwd.online.shop.util;

import com.epam.training.jwd.online.shop.service.exception.ApplicationStartException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReaderUtil {

    private PropertyReaderUtil() {
    }

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
