package tests;

import base.BaseTest;
import data.LoginData;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

public class LogoutTest extends BaseTest {

    @Test
    public void TC_LOGOUT_01_logoutSuccessfully() {

        LoginPage loginPage = new LoginPage(driver, wait);
        InventoryPage inventoryPage = new InventoryPage(driver);

        // login
        loginPage.login(LoginData.VALID_USER, LoginData.VALID_PASS);

        wait.until(ExpectedConditions.urlContains("inventory"));

        // logout
        inventoryPage.logout();

        // verify redirect to login page
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));

        Assert.assertFalse(
                driver.getCurrentUrl().contains("inventory"),
                "Logout failed: still on inventory page"
        );

        Assert.assertTrue(
                driver.findElement(By.id("login-button")).isDisplayed(),
                "Login button not displayed after logout"
        );
    }
    @Test
        public void TC_LOGOUT_02_cannotAccessInventoryAfterLogout() {

        LoginPage loginPage = new LoginPage(driver, wait);
        InventoryPage inventoryPage = new InventoryPage(driver);

        loginPage.login(LoginData.VALID_USER, LoginData.VALID_PASS);

        inventoryPage.logout();

        driver.get("https://www.saucedemo.com/inventory.html");

        Assert.assertTrue(
                driver.getCurrentUrl().contains("saucedemo"),
                "User should not access inventory after logout"
        );
    }
}