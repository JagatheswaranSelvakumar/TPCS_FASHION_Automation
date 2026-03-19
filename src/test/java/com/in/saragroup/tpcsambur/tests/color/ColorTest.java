package com.in.saragroup.tpcsambur.tests.color;

import base.BaseTest;
import com.in.saragroup.tpcsambur.pages.ColorPage;
import com.in.saragroup.tpcsambur.pages.DashboardPage;
import com.in.saragroup.tpcsambur.pages.LoginPage;
import com.in.saragroup.tpcsambur.utilities.ConfigReader;
import com.in.saragroup.tpcsambur.utilities.RandomUtils;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static com.in.saragroup.tpcsambur.utilities.ScreenshotUtils.captureScreenshot;

@Listeners({AllureTestNg.class})
public class ColorTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(ColorTest.class);
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private ColorPage colorPage;

    @BeforeMethod
    public void setup() throws Exception {
        log.info("ColorTest BeforeMethod method started");
        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        colorPage = new ColorPage();
        log.info("Page objects initialized successfully");
    }


    @Test(description = "Add a new color in the application")
    @Epic("Color Management")
    @Feature("Add Color")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewColorTest() {
        log.info("Starting test: addNewColorTest - Verify that a new color can be added successfully");
        try {
            log.info("logging in to the application");
            String username = ConfigReader.prop.getProperty("username");
            String password = ConfigReader.prop.getProperty("password");
            loginPage.login(username, password);

            Assert.assertEquals(driver.getTitle(), "Fashion");
            log.info("Login successful, navigating to dashboard");
            dashboardPage.clickAdmin().clickColor();
            log.info("Navigated to Color page, adding a new color");
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
            }
            log.info("Color added successfully, verifying color in the list");

            colorPage.clickViewButton();
            String actualColorName = colorPage.getFirstColorNameInList();

            if (!actualColorName.equals(colorName)) {
                log.error("Color mismatch. Expected: " + colorName + ", Found: " + actualColorName);
                throw new AssertionError("Expected color name: " + colorName + ", but found: " + actualColorName);
            } else {
                log.info("Color verified successfully: " + actualColorName);
            }
            log.info("Color verified in list successfully");
        } catch (Exception e) {
            log.error("Test failed due to exception: ", e);
            captureScreenshot(driver,"addNewColorTest");
        }
    }

}