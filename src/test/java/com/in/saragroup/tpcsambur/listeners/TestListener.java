package com.in.saragroup.tpcsambur.listeners;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;

public class TestListener implements ITestListener {
    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        log.info("Test failed: " + result.getName());
        captureScreenshot(result, "FAILURE");
    }


    @Override
    public void onTestStart(ITestResult result) {
        log.info("Test started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Test passed: " + result.getName());
        captureScreenshot(result, "SUCCESS");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.info("Test skipped: " + result.getName());
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        log.info("Test failed with timeout: " + result.getName());
        onTestFailure(result);
    }

    private void captureScreenshot(ITestResult result, String status) {
        try {
            Object testClass = result.getInstance();
            WebDriver driver = getDriverFromTestClass(testClass);

            if (driver != null) {
                try {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    String attachmentName = result.getName() + " - [" + status + "]";
                    Allure.addAttachment(attachmentName, "image/png", new ByteArrayInputStream(screenshot), ".png");
                    log.info("Screenshot attached to Allure for test '" + result.getName() + "' with status: " + status);
                } catch (Exception e) {
                    log.error("Failed to capture screenshot for test '" + result.getName() + "': " + e.getMessage(), e);
                }
            } else {
                log.warn("WebDriver is null for test: " + result.getName());
            }
        } catch (Exception e) {
            log.error("Error accessing driver from test instance: " + e.getMessage(), e);
        }
    }

    /**
     * Get driver from test class by looking in current class and parent classes
     */
    private WebDriver getDriverFromTestClass(Object testClass) throws IllegalAccessException {
        Class<?> clazz = testClass.getClass();

        // Search in the class and its parent classes
        while (clazz != null) {
            try {
                Field driverField = clazz.getDeclaredField("driver");
                driverField.setAccessible(true);
                WebDriver driver = (WebDriver) driverField.get(testClass);
                if (driver != null) {
                    log.info("Found driver field in class: " + clazz.getName());
                    return driver;
                }
            } catch (NoSuchFieldException e) {
                // Continue to parent class
                log.debug("Driver field not found in class: " + clazz.getName() + ", checking parent class...");
            }
            clazz = clazz.getSuperclass();
        }

        log.error("Driver field not found in test class hierarchy");
        return null;
    }
}
