package com.in.saragroup.tpcsambur.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    public static Properties prop;

    public static void loadConfig() {
        try {
            prop = new Properties();
            FileInputStream file = new FileInputStream(
                    System.getProperty("user.dir") + "/src/main/resources/config.properties");
            prop.load(file);
            log.info("Loaded config.properties successfully");
        } catch (IOException e) {
            log.error("Failed to load config.properties: " + e.getMessage());
        }
    }
}
