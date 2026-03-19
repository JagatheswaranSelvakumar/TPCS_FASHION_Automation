package base;

import com.in.saragroup.tpcsambur.utilities.ConfigReader;
import com.in.saragroup.tpcsambur.utilities.DriverFactory;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    private static final Logger log = LogManager.getLogger(BaseTest.class);

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        log.info("BaseTest BeforeMethod method started");
        try {
            log.info("Loading configuration using ConfigReader");
            ConfigReader.loadConfig();
            log.info("Configuration loaded successfully");
            log.info("Launching browser using DriverFactory");
            DriverFactory.launchBrowser();
            log.info("Browser launched successfully");
            driver = DriverFactory.driver;
            log.info("WebDriver instance assigned successfully");
        } catch (Exception e) {
            log.info("Base Test BeforeMethod method failed: " + e.getMessage());
            log.error("Error during browser setup: " + e.getMessage(), e);
            System.err.println("Error during browser setup: " + e.getMessage());
            throw new RuntimeException("Failed to launch browser", e);
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        log.info("BaseTest AfterMethod method started for test: " + result.getName());
        // Capture screenshot if test failed
        if (result.getStatus() == ITestResult.FAILURE) {
            log.info("Test failed: " + result.getName() + ". Attempting to capture screenshot.");
            String testName = result.getName();
            log.info("Test failed: " + testName + ". Capturing screenshot.");
            saveScreenshotPNG(driver, testName);
            log.info("Screenshot captured successfully for test: " + testName);
        }

        if (driver != null) {
            log.info("Closing browser for test: " + result.getName());
            driver.quit();
            log.info("Browser closed successfully for test: " + result.getName());
        }
    }


    /**
     * Attach screenshot to Allure report
     */
    @Attachment(value = "{testName} - Screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver, String testName) {
        log.info("Capturing screenshot for test: " + testName);
        try {
            log.info("Attempting to capture screenshot for test: " + testName);
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            log.info("Failed to capture screenshot for test: " + testName + ". Error: " + e.getMessage());
            System.err.println("Failed to capture screenshot for " + testName + ": " + e.getMessage());
            return new byte[0];
        }

    }
}