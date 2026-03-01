package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SortTest extends BaseTest {

    private void login() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("inventory"));
    }
    @Test
    void TC_SORT_01_sortProductsByNameAToZ() {
        login();

        Select sortDropdown = new Select(
                driver.findElement(By.cssSelector("[data-test='product-sort-container']"))
        );
        sortDropdown.selectByVisibleText("Name (A to Z)");

        String firstItemName = driver.findElements(
                By.className("inventory_item_name")
        ).get(0).getText();
        Assertions.assertEquals(
                "Sauce Labs Backpack",
                firstItemName,
                "First product should be Sauce Labs Backpack when sorted A to Z"
        );
    }
    @Test
    void TC_SORT_02_sortProductsByNameZToA() {
        login();

        Select sortDropdown = new Select(
                driver.findElement(By.cssSelector("[data-test='product-sort-container']"))
        );
        sortDropdown.selectByVisibleText("Name (Z to A)");

        String firstItemName = driver.findElements(
                By.className("inventory_item_name")
        ).get(0).getText();
        Assertions.assertEquals(
                "Test.allTheThings() T-Shirt (Red)",
                firstItemName,
                "First product should be Test.allTheThings() T-Shirt (Red) when sorted Z to A"
        );
    }
    @Test
    void TC_SORT_03_sortProductsByPriceLowToHigh() {
        login();

        Select sortDropdown = new Select(
                driver.findElement(By.cssSelector("[data-test='product-sort-container']"))
        );
        sortDropdown.selectByVisibleText("Price (low to high)");

        List<Double> prices = driver.findElements(
                        By.className("inventory_item_price")
                ).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .toList();
        Assertions.assertTrue(
                prices.get(0) <= prices.get(prices.size() - 1),
                "Products should be sorted by price from low to high"
        );
    }
    @Test
    void TC_SORT_04_sortProductsByPriceHighToLow() {
        login();

        Select sortDropdown = new Select(
                driver.findElement(By.cssSelector("[data-test='product-sort-container']"))
        );
        sortDropdown.selectByVisibleText("Price (high to low)");

        List<Double> prices = driver.findElements(
                        By.className("inventory_item_price")
                ).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .toList();

        Assertions.assertTrue(
                prices.get(0) >= prices.get(prices.size() - 1),
                "Products should be sorted by price from high to low"
        );
    }
}
