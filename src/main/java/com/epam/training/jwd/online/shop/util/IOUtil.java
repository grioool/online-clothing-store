package com.epam.training.jwd.online.shop.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * The class provide deleted all uploaded files
 *
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class IOUtil {
    private static final Logger LOGGER = LogManager.getLogger(IOUtil.class);
    private static final String DATA_DIR = "\\Users\\olga\\online-clothing-store\\target\\online-store-1.0-SNAPSHOT\\data";
    private IOUtil() {
    }

    public static void deleteData(String fileName) {
        File file = new File(DATA_DIR + fileName);
        if (!file.delete()) {
            LOGGER.error("Failed to delete upload with filename = " + fileName);
        }
    }
}
