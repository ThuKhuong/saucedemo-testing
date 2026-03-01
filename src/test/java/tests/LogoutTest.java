package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LogoutTest extends BaseTest {

    @Test
    void TC_LOGOUT_01_logoutSuccessfully() {
        // login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.urlContains("inventory"));
        // open menu
        driver.findElement(By.id("react-burger-menu-btn")).click();
        // đợi logout link xuất hiện và clickable rồi mới click
        wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        // verify
        Assertions.assertFalse(driver.getCurrentUrl().contains("inventory"),
                "Logout failed: still on inventory page");

        Assertions.assertTrue(driver.findElement(By.id("login-button")).isDisplayed(),
                "Login button not displayed after logout");
    }
}
