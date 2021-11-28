package com.epam.training.jwd.online.shop.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;


public class IOUtil {
    private static final Logger LOGGER = LogManager.getLogger(IOUtil.class);
    private static final String DATA_DIR = "C:\\Users\\Aleksey\\Desktop\\EPAM\\EpamCafe\\target\\epam_cafe-1.0-SNAPSHOT\\data\\";

    private IOUtil() {
    }

    public static void deleteData(String fileName) {
        File file = new File(DATA_DIR + fileName);
        if (!file.delete()) {
            LOGGER.error("Failed to delete upload with filename = " + fileName);
        }
    }
}
