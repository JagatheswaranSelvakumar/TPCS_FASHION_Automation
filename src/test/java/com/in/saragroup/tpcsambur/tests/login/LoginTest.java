package com.in.saragroup.tpcsambur.tests.login;

import base.BaseTest;
import com.in.saragroup.tpcsambur.utilities.ConfigReader;
import com.in.saragroup.tpcsambur.utilities.DriverFactory;
import com.in.saragroup.tpcsambur.utilities.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.in.saragroup.tpcsambur.pages.LoginPage;

public class LoginTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(LoginTest.class);
    private LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        log.info("Setting up the test environment");
        ConfigReader.loadConfig();
        log.info("Configuration loaded successfully");
        DriverFactory.launchBrowser();
        log.info("Browser launched successfully");
        loginPage = new LoginPage();
        log.info("Page objects initialized successfully");
    }

    @AfterMethod
    public void tearDown() {
        log.info("Tearing down the test environment");
        if (driver != null) {
            driver.quit();
            log.info("Browser closed successfully");
        }
    }

    @Test(description = "Validate login with valid credentials")
    public void loginTest() {
        log.info("Starting test: validateValidLogin - Verify the user able to login successfully");
        try {
            log.info("Logging in to the application");
            String username = ConfigReader.prop.getProperty("username");
            String password = ConfigReader.prop.getProperty("password");
            loginPage.login(username, password);
            log.info("Login attempted with username: " + username);
            Assert.assertEquals(driver.getTitle(), "Fashion");
            log.info("Login successful, page title verified as 'Fashion'");
        } catch (Exception e) {
            log.error("Test failed due to exception: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "loginTest");
        }
    }

        @Test(description = "Validate login with invalid credentials shows OTP field")
        public void InValidLoginTest () {
            try {
                log.info("Logging in to the application with invalid credentials");
                String username = ConfigReader.prop.getProperty("invalidUsername");
                String password = ConfigReader.prop.getProperty("invalidUsername");
                loginPage.login(username, password);
                log.info("Login attempted with invalid credentials: " + username);
                Assert.assertTrue(loginPage.isOtpFieldDisplayed(), "OTP text field should be displayed on invalid login.");
                log.info("Login unsuccessful, OTP field displayed as expected");
            } catch (Exception e) {
                log.error("Test failed due to exception: " + e.getMessage());
                ScreenshotUtils.captureScreenshot(driver, "InValidLoginTest");
            }
        }
    }