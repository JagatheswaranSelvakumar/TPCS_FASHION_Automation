package com.in.saragroup.tpcsambur.tests.color;

import base.BaseTest;
import com.in.saragroup.tpcsambur.pages.ColorPage;
import com.in.saragroup.tpcsambur.pages.DashboardPage;
import com.in.saragroup.tpcsambur.pages.LoginPage;
import com.in.saragroup.tpcsambur.utilities.ConfigReader;
import com.in.saragroup.tpcsambur.utilities.DriverFactory;
import com.in.saragroup.tpcsambur.utilities.RandomUtils;
import com.in.saragroup.tpcsambur.utilities.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.in.saragroup.tpcsambur.utilities.DriverFactory.driver;
import static com.in.saragroup.tpcsambur.utilities.ScreenshotUtils.captureScreenshot;

public class ColorTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(ColorTest.class);
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private ColorPage colorPage;

    @BeforeMethod
    public void setup() throws Exception {
        log.info("Setting up the test environment");
        ConfigReader.loadConfig();
        log.info("Configuration loaded successfully");
        DriverFactory.launchBrowser();
        log.info("Browser launched successfully");
        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        colorPage = new ColorPage();
        log.info("Page objects initialized successfully");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = captureScreenshot(driver, result.getName());
            test.fail("Test Failed: " + result.getThrowable());
            try {
                test.addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                log.error("Failed to attach screenshot to report: " + e.getMessage());
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed");
        }
        log.info("Tearing down the test environment");
        if (driver != null) {
            driver.quit();
            log.info("Browser closed successfully");
        }
    }

    @Test(description = "Add a new color in the application")
    public void addNewColorTest() {
        startTest("addNewColorTest", "Verify that a new color can be added successfully");
        log.info("Starting test: addNewColorTest - Verify that a new color can be added successfully");
        try {
            log.info("logging in to the application");
            test.info("Logging in to the application");
            String username = ConfigReader.prop.getProperty("username");
            String password = ConfigReader.prop.getProperty("password");
            loginPage.login(username, password);

            Assert.assertEquals(driver.getTitle(), "Fashion");
            test.pass("Login successful");
            log.info("Login successful, navigating to dashboard");
            test.info("Navigating to Color page");
            dashboardPage.clickAdmin().clickColor();
            log.info("Navigated to Color page, adding a new color");
            test.info("Adding a new color");
            colorPage.clickAddNewColor().clickStatusField().clickInactiveOption();

            String colorName = RandomUtils.generateRandomColor();
            colorPage.enterColorName(colorName);
            colorPage.enterRandomRemark();
            colorPage.clickSaveButton();

            String actualToast = colorPage.verifyAndGetTheToastMessageText();
            String expectedToast = "Color is Inserted";
            if (!expectedToast.equals(actualToast)) {
                log.error("Toast message mismatch: expected '" + expectedToast + "', got '" + actualToast + "'");
                throw new AssertionError("Expected toast message: '" + expectedToast + "', but got: '" + actualToast + "'");
            } else {
                log.info("Toast message verified successfully: " + actualToast);
                test.pass("Toast message verified successfully: " + actualToast);
            }
            test.pass("Color added successfully");
            log.info("Color added successfully, verifying color in the list");

            colorPage.clickViewButton();
            String actualColorName = colorPage.getFirstColorNameInList();

            if (!actualColorName.equals(colorName)) {
                log.error("Color mismatch. Expected: " + colorName + ", Found: " + actualColorName);
                throw new AssertionError("Expected color name: " + colorName + ", but found: " + actualColorName);
            } else {
                log.info("Color verified successfully: " + actualColorName);
                test.pass("Color verified successfully: " + actualColorName);
            }
            test.pass("Color verified in list successfully");
            log.info("Color verified in list successfully");
        } catch (Exception e) {
            test.fail("Test failed due to exception: " + e.getMessage());
            log.error("Test failed due to exception: ", e);
            captureScreenshot(driver,"addNewColorTest");
        }
    }
}