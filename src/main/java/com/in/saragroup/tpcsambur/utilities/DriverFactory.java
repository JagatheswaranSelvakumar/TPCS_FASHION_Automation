package com.in.saragroup.tpcsambur.utilities;

import java.io.ByteArrayInputStream;
import java.time.Duration;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Utility class for WebDriver operations.
 * Handles clicking, typing, waiting, scrolling, and highlighting elements.
 * Throws runtime exceptions for any failures.
 */
public class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class);
    private static final int DEFAULT_TIMEOUT = 10;
    public static WebDriver driver;


    // ------------------- Browser Launch -------------------
    public static void launchBrowser() {
        String browserName = System.getProperty("browser");
        if (browserName == null || browserName.isEmpty()) {
            browserName = ConfigReader.prop.getProperty("browser", "chrome");
        }
        browserName = ConfigReader.prop.getProperty("browser", "chrome");

        switch (browserName.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_TIMEOUT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        String url = ConfigReader.prop.getProperty("url");
        driver.get(url);
        log.info("Launching browser: {}", browserName);
    }

    // ------------------- Scroll -------------------
    public static void scrollToElement(WebElement element) {
        if (element == null) throw new RuntimeException("WebElement is null. Cannot scroll.");
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            throw new RuntimeException("Failed to scroll to element: " + e.getMessage(), e);
        }
    }

    // ------------------- Click -------------------
    public static void clickElement(WebElement element) {
        if (element == null) throw new RuntimeException("WebElement is null. Cannot click.");
        try {
            highlightElement(element);
            element.click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to click element: " + e.getMessage(), e);
        }
    }

    public static void actionClick(WebElement element) {
        if (element == null) throw new RuntimeException("WebElement is null. Cannot action click.");
        try {
            Actions actions = new Actions(driver);
            highlightElement(element);
            actions.moveToElement(element).click().build().perform();
        } catch (Exception e) {
            throw new RuntimeException("Failed to perform action click: " + e.getMessage(), e);
        }
    }

    // ------------------- Waits -------------------
    public static boolean waitForElementVisible(WebElement element, int timeoutInSeconds) {
        if (element == null) throw new RuntimeException("WebElement is null. Cannot wait for visibility.");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            log.error("Element not visible after " + timeoutInSeconds + " seconds."+ e);
            return false;
        }
    }

    public static boolean waitForElementVisible(By locator, int timeoutInSeconds) {
        if (locator == null) throw new RuntimeException("Locator is null. Cannot wait for visibility.");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            log.error("Element not visible after " + timeoutInSeconds + " seconds."+ e);
            return false;
        }
    }

    // ------------------- Input -------------------
    public static void enterText(WebElement element, String value) {
        if (element == null) throw new RuntimeException("WebElement is null. Cannot enter text.");
        try {
            element.clear();
            highlightElement(element);
            element.sendKeys(value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter text: " + e.getMessage(), e);
        }
    }

    // ------------------- Get Values -------------------
    public static String getText(WebElement element) {
        if (element == null) return "";
        try {
            highlightElement(element);
            return element.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getAttribute(WebElement element, String attributeName) {
        if (element == null) return "";
        try {
            return element.getAttribute(attributeName);
        } catch (Exception e) {
            return "";
        }
    }

    // ------------------- Highlight -------------------
    public static void highlightElement(WebElement element) {
        if (element == null) return;
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String originalStyle = element.getAttribute("style");
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
                    element, "border: 3px solid red; background-color: yellow;");
            Thread.sleep(300);
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, originalStyle);
        } catch (Exception e) {
            log.error("Failed to highlight element: " + e.getMessage());
        }
    }
}