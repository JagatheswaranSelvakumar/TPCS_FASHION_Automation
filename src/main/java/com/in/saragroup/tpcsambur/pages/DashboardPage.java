package com.in.saragroup.tpcsambur.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.in.saragroup.tpcsambur.utilities.DriverFactory;

import static com.in.saragroup.tpcsambur.utilities.DriverFactory.driver;

public class DashboardPage {

    private DriverFactory driverFactory;

    public DashboardPage() {
        PageFactory.initElements(driver, this);
        driverFactory = new DriverFactory();
    }

    // ====== WebElements ======
    @FindBy(xpath = "//a[@id='model_row_8']")
    private WebElement adminDropdown;

    @FindBy(id = "sub_doc_row_14_322")
    private WebElement colorButton;

    @FindBy(xpath = "(//a[@id='sub_doc_row_14_408'])[1]")
    private WebElement unitButton;

    @FindBy(xpath = "(//a[@id='sub_doc_row_14_398'])[1]")
    private WebElement sizeButton;

    @FindBy(xpath = "(//a[@id='sub_doc_row_14_91'])[1]")
    private WebElement itemButton;

    @FindBy(id = "bo_form_add_new")
    private WebElement addNewColorButton;

    @FindBy(xpath = "//input[@id='variant_code' and @name='variant_code']")
    private WebElement colorTextField;

    // ====== Page Actions ======
    public DashboardPage clickAdmin() {
        driverFactory.clickElement(adminDropdown);
        return this;
    }

    public DashboardPage clickColor() {
        driverFactory.clickElement(colorButton);
        return this;
    }

    public DashboardPage clickUnit() {
        driverFactory.clickElement(unitButton);
        return this;
    }

    public DashboardPage clickSize() {
        driverFactory.actionClick(sizeButton);
        return this;
    }

    public DashboardPage clickItem() {
        driverFactory.actionClick(itemButton);
        return this;
    }

    public DashboardPage clickAddNewColor() {
        driverFactory.actionClick(addNewColorButton);
        return this;
    }

    public void enterColorText(String color) {
        DriverFactory.enterText(colorTextField, color);
    }
}