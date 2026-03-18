package com.in.saragroup.tpcsambur.pages;

import com.in.saragroup.tpcsambur.utilities.DriverFactory;
import com.in.saragroup.tpcsambur.utilities.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.in.saragroup.tpcsambur.utilities.DriverFactory.driver;

public class ColorPage {

    private final DriverFactory driverFactory;

    public ColorPage() {
        PageFactory.initElements(driver, this);
        driverFactory = new DriverFactory();
    }

    // ====== WebElements ======
    @FindBy(id = "bo_form_add_new")
    private WebElement addNewColorButton;

    @FindBy(xpath = "//input[@id='variant_code' and @name='variant_code']")
    private WebElement colorCodeField;

    @FindBy(xpath = "//button[@type='button' and contains(@class,'dropdown-toggle')]")
    private WebElement statusDropdown;

    @FindBy(xpath = "//span[text()='Inactive']")
    private WebElement inactiveOption;

    @FindBy(xpath = "//input[@id='variant_name']")
    private WebElement colorNameField;

    @FindBy(xpath = "//textarea[@id='remarks']")
    private WebElement remarksField;

    @FindBy(id = "save")
    private WebElement saveButton;

    @FindBy(xpath = "//form[@id='validate-form']/div/div[3]/div/div[5]/div/div/button/span")
    private WebElement viewButton;

    // TODO: Add locator for toast message
    @FindBy(xpath = "//div[@id='toast-container']/div/div")
    private WebElement toastMessage;

    @FindBy(xpath = "//tr[1]//td[5]")
    private WebElement firstColorNameInList;

    // ====== Actions ======
    public ColorPage clickAddNewColor() {
        driverFactory.clickElement(addNewColorButton);
        return this;
    }

    public ColorPage clickStatusField() {
        driverFactory.clickElement(statusDropdown);
        return this;
    }

    public ColorPage clickInactiveOption() {
        driverFactory.clickElement(inactiveOption);
        return this;
    }

    public ColorPage enterColorName(String colorName) {
        DriverFactory.enterText(colorNameField, colorName);
        return this;
    }

    public ColorPage enterRandomRemark() {
        String randomRemark = RandomUtils.generateRandomString(50);
        DriverFactory.enterText(remarksField, randomRemark);
        return this;
    }

    public ColorPage clickSaveButton() {
        driverFactory.clickElement(saveButton);
        return this;
    }


    public String verifyAndGetTheToastMessageText() {
        boolean isDisplayed = driverFactory.waitForElementVisible(toastMessage, 10);
        if (!isDisplayed) {
            throw new AssertionError("Toast message not found or timeout reached");
        }

        String actualMessage = driverFactory.getText(toastMessage).trim();
        return actualMessage;
    }

    public ColorPage clickViewButton() {
        driverFactory.clickElement(viewButton);
        return this;
    }

    public String getFirstColorNameInList() {
        boolean isDisplayed = driverFactory.waitForElementVisible(firstColorNameInList, 10);
        if (!isDisplayed) {
            throw new RuntimeException("Color list is not visible or not loaded");
        }
        return driverFactory.getText(firstColorNameInList);
    }
}