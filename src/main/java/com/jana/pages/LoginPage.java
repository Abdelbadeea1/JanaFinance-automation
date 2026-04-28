package com.jana.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private AppiumDriver driver;

    // ===== Locators =====

    @AndroidFindBy(accessibility = "username")
    @iOSXCUITFindBy(accessibility = "username")
    private WebElement usernameField;

    @AndroidFindBy(accessibility = "password")
    @iOSXCUITFindBy(accessibility = "password")
    private WebElement passwordField;

    @AndroidFindBy(accessibility = "login-button")
    @iOSXCUITFindBy(accessibility = "login-button")
    private WebElement loginButton;

    @AndroidFindBy(accessibility = "error-message")
    @iOSXCUITFindBy(accessibility = "error-message")
    private WebElement errorMessage;

    // ===== Constructor =====

    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    // ===== Actions =====

    public void enterUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public boolean isLoginButtonDisplayed() {
        return loginButton.isDisplayed();
    }
}