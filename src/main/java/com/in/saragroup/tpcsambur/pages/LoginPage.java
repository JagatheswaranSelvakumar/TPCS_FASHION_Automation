package com.in.saragroup.tpcsambur.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.in.saragroup.tpcsambur.utilities.DriverFactory;

import static com.in.saragroup.tpcsambur.utilities.DriverFactory.driver;

public class LoginPage  {

    private final DriverFactory driverFactory;

    public LoginPage() {
        PageFactory.initElements(driver, this);
        driverFactory = new DriverFactory();
    }

    // ====== WebElements ======
    @FindBy(id = "login_name")
    private WebElement usernameTextbox;

    @FindBy(id = "password")
    private WebElement passwordTextbox;

    @FindBy(id = "login")
    private WebElement loginButton;

    @FindBy(xpath = "//button[@data-bb-handler='ok']")
    private WebElement okButton;

    @FindBy(xpath = "//a[@data-toggle='dropdown' and .//i[contains(@class,'glyphicon-user')]]")
    private WebElement dropDownMegaLogo;

    @FindBy(xpath = "//a[text()='Logout']")
    private WebElement logoutButton;

    @FindBy(id = "login_otp")
    private WebElement otpTextField;

    // ====== Private Methods ======
    private void enterUsername(String username) {
        DriverFactory.enterText(usernameTextbox, username);
    }

    private void enterPassword(String password) {
        DriverFactory.enterText(passwordTextbox, password);
    }

    private void clickLoginButton() {
        driverFactory.clickElement(loginButton);
    }

    private void handlePopup() {
        if (driverFactory.waitForElementVisible(okButton,5)) { // Use the WebElement version
            driverFactory.clickElement(okButton);
        }
    }

    private void clickProfileDropDown() {
        driverFactory.clickElement(dropDownMegaLogo);
    }

    private void clickLogoutButton() {
        driverFactory.clickElement(logoutButton);
    }

    // ====== Public Actions ======
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        handlePopup();
    }

    public void logout() {
        clickProfileDropDown();
        clickLogoutButton();
    }

    public boolean isOtpFieldDisplayed() {
        return driverFactory.waitForElementVisible(otpTextField, 5);
    }
}