package com.in.saragroup.tpcsambur.utilities;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {
    private static final Logger log = LogManager.getLogger(ScreenshotUtils.class);

    public static String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String screenshotPath = System.getProperty("user.dir") + "/test-output/screenshots/" + testName + "_" + timestamp + ".png";

        try {
            log.info("Capturing screenshot for test: " + testName);
            TakesScreenshot ts = (TakesScreenshot) driver;
            log .info("Taking screenshot using WebDriver");
            File source = ts.getScreenshotAs(OutputType.FILE);
            log.info("Screenshot captured successfully, saving to: " + screenshotPath);
            FileUtils.copyFile(source, new File(screenshotPath));
            log.info("Screenshot saved at: " + screenshotPath);
        } catch (IOException e) {
            log.error("Failed to capture screenshot: " + e.getMessage());
        }
        return screenshotPath;
    }
}
