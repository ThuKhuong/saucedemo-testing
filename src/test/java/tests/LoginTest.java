package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginTest extends BaseTest {

    @Test
    void TC_LOGIN_01_loginWithValidAccount() {

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();


        Assertions.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "Login fail");
    }
    @Test
    void TC_LOGIN_02_loginWithInvalidPassword() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce1");
        driver.findElement(By.id("login-button")).click();

        String errorText = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();

        Assertions.assertTrue(errorText.contains("Username and password do not match"),
                "Expected error message was not displayed");

        Assertions.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"),
                "User should stay on login page");
    }
    @Test
    void TC_LOGIN_03_loginWithInvalidUsername() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user1");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String errorText = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        Assertions.assertTrue(errorText.contains("Username and password do not match"),
                "Expected error message was not displayed");

        Assertions.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"),
                "User should stay on login page");

    }
    @Test
    void TC_LOGIN_04_loginWithEmptyUsername() {
        // user-name để trống
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String errorText = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();

        Assertions.assertTrue(
                errorText.contains("Username is required"),
                "Expected 'Username is required' error message"
        );
        Assertions.assertTrue(
                driver.getCurrentUrl().contains("saucedemo.com"),
                "User should stay on login page"
        );
    }
    @Test
    void TC_LOGIN_05_loginWithEmptyPassword() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        //password để trống
        driver.findElement(By.id("login-button")).click();
        // Expected: hiện message lỗi
        String errorText = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        Assertions.assertTrue(
                errorText.contains("Password is required"),
                "Expected 'Password is required' error message"
        );
        // Vẫn ở trang login
        Assertions.assertTrue(
                driver.getCurrentUrl().contains("saucedemo.com"),
                "User should stay on login page"
        );
    }
    @Test
    void TC_LOGIN_06_lockedOutUser() {

        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Verify error message
        String errorText = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();

        Assertions.assertTrue(
                errorText.contains("Sorry, this user has been locked out."),
                "Expected locked out message, actual: " + errorText
        );
        // Verify still on login page
        Assertions.assertTrue(
                driver.getCurrentUrl().contains("saucedemo.com"),
                "User should stay on login page"
        );
    }
    @Test
    void TC_LOGIN_07_performanceGlitchUser() {
        //Login
        driver.findElement(By.id("user-name")).sendKeys("performance_glitch_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("inventory"));

        Assertions.assertTrue(
                driver.getCurrentUrl().contains("inventory"),
                "Login FAILED not redirected to Inventory page"
        );
    }
}
