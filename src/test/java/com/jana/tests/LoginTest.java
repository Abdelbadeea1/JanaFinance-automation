package com.jana.tests;

import com.jana.base.BaseDriver;
import com.jana.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseDriver {

    private LoginPage loginPage;

    @BeforeMethod
    public void initPage() {
        loginPage = new LoginPage(driver);
    }

    @Test(description = "Verify successful login with valid credentials")
    public void testValidLogin() {
        loginPage.login("standard_user", "secret_sauce");
        // Assert we moved to next screen
        // We will add proper assertion after connecting real device
        System.out.println("Valid login test executed successfully");
    }

    @Test(description = "Verify error message with invalid credentials")
    public void testInvalidLogin() {
        loginPage.login("invalid_user", "wrong_password");
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertNotNull(errorMsg, "Error message should be displayed");
        System.out.println("Invalid login test executed successfully");
    }

    @Test(description = "Verify login button is displayed on launch")
    public void testLoginPageLoaded() {
        boolean isDisplayed = loginPage.isLoginButtonDisplayed();
        Assert.assertTrue(isDisplayed, "Login button should be visible");
        System.out.println("Login page loaded test executed successfully");
    }
}