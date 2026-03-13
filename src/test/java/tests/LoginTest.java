package tests;

import base.BaseTest;
import data.LoginData;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void setUpPage() {
        loginPage = new LoginPage(driver, wait);
    }
    @Test
    public void TC_LOGIN_01_loginWithValidAccount() {
        loginPage.login(LoginData.VALID_USER, LoginData.VALID_PASS);
        wait.until(ExpectedConditions.urlContains("inventory"));
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "Login fail");
    }

    @Test
    public void TC_LOGIN_02_loginWithInvalidPassword() {
        loginPage.login(LoginData.VALID_USER, LoginData.INVALID_PASS);
        String errorText = loginPage.getErrorText();
        Assert.assertTrue(errorText.contains("Username and password do not match"),
                "Expected error message was not displayed. Actual: " + errorText);
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"),
                "User should stay on login page");
    }

    @Test
    public void TC_LOGIN_03_loginWithInvalidUsername() {
        loginPage.login(LoginData.INVALID_USER, LoginData.VALID_PASS);
        String errorText = loginPage.getErrorText();
        Assert.assertTrue(errorText.contains("Username and password do not match"),
                "Expected error message was not displayed. Actual: " + errorText);
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"),
                "User should stay on login page");
    }

    @Test
    public void TC_LOGIN_04_loginWithEmptyUsername() {
        loginPage.login("", LoginData.VALID_PASS);
        String errorText = loginPage.getErrorText();
        Assert.assertTrue(errorText.contains("Username is required"),
                "Expected 'Username is required'. Actual: " + errorText);
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"),
                "User should stay on login page");
    }

    @Test
    public void TC_LOGIN_05_loginWithEmptyPassword() {
        loginPage.login(LoginData.VALID_USER, "");
        String errorText = loginPage.getErrorText();
        Assert.assertTrue(errorText.contains("Password is required"),
                "Expected 'Password is required'. Actual: " + errorText);
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"),
                "User should stay on login page");
    }

    @Test
    public void TC_LOGIN_06_loginWithEmptyUsernamePassword() {
        loginPage.login("", "");
        String errorText = loginPage.getErrorText();
        Assert.assertTrue(errorText.contains("Username is required"),
                "Expected 'Username is required'. Actual: " + errorText);
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"),
                "User should stay on login page");
    }

    @Test
    public void TC_LOGIN_07_loginWithInvalidUsernamePassword() {
        loginPage.login(LoginData.INVALID_USER, LoginData.INVALID_PASS);
        String errorText = loginPage.getErrorText();
        Assert.assertTrue(errorText.contains("Username and password do not match"),
                "Expected error message was not displayed. Actual: " + errorText);
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"),
                "User should stay on login page");
    }

    @Test
    public void TC_LOGIN_08_lockedOutUser() {
        loginPage.login(LoginData.LOCKED_USER, LoginData.VALID_PASS);
        String errorText = loginPage.getErrorText();
        Assert.assertTrue(errorText.contains("locked out"),
                "Expected locked out message. Actual: " + errorText);
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"),
                "User should stay on login page");
    }

    @Test
    public void TC_LOGIN_09_performanceGlitchUser() {
        loginPage.login("performance_glitch_user", LoginData.VALID_PASS);
        wait.until(ExpectedConditions.urlContains("inventory"));
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "Login FAILED not redirected to Inventory page");
    }

    @Test
    public void TC_LOGIN_10_usernameWithLeadingTrailingSpaces() {
        loginPage.login("  standard_user", LoginData.VALID_PASS);
        String errorText = loginPage.getErrorText();
        Assert.assertTrue(
                errorText.contains("Username and password do not match"),
                "Login should fail when username has spaces"
        );
    }

    @Test
    public void TC_LOGIN_11_usernameWithSpecialCharacters() {
        loginPage.login("@@@standard_user", LoginData.VALID_PASS);
        String errorText = loginPage.getErrorText();
        Assert.assertTrue(
                errorText.contains("Username and password do not match"),
                "Login should fail with special characters"
        );
    }
}