package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class CartTest extends BaseTest {

    @Test
    void TC_CART_01_addOneProductToCart() {

        login("standard_user", "secret_sauce");

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        Assertions.assertEquals(
                "1",
                driver.findElement(By.className("shopping_cart_badge")).getText(),
                "Cart badge should be 1"
        );
    }

    @Test
    void TC_CART_02_addTwoProductToCart() {

        login("standard_user", "secret_sauce");

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();

        Assertions.assertEquals(
                "2",
                driver.findElement(By.className("shopping_cart_badge")).getText(),
                "Cart badge should be 2"
        );
    }

    @Test
    void TC_CART_03_removeOneProductFromCart() {

        login("standard_user", "secret_sauce");

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        driver.findElement(By.id("remove-sauce-labs-bike-light")).click();

        Assertions.assertEquals(
                "1",
                driver.findElement(By.className("shopping_cart_badge")).getText(),
                "Cart badge should display 1"
        );
    }

    @Test
    void TC_CART_04_verifyProductNameInCart() {

        login("standard_user", "secret_sauce");

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        openCart();

        String productName = driver.findElement(
                By.className("inventory_item_name")
        ).getText();

        Assertions.assertEquals(
                "Sauce Labs Backpack",
                productName,
                "Product name in cart should be correct"
        );
    }

    @Test
    void TC_CART_05_continueShopping() {

        login("standard_user", "secret_sauce");

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        openCart();

        driver.findElement(By.id("continue-shopping")).click();

        Assertions.assertTrue(
                driver.getCurrentUrl().contains("inventory"),
                "Should navigate back to inventory page"
        );
    }

    @Test
    void TC_CART_06_removeItemInCart() {

        login("standard_user", "secret_sauce");

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        openCart();

        driver.findElement(By.id("remove-sauce-labs-backpack")).click();

        Assertions.assertEquals(
                0,
                getCartBadgeCount(),
                "Cart badge should disappear after removing item"
        );
    }

    @Test
    void TC_CART_07_removeItemInInventory() {

        login("standard_user", "secret_sauce");

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();

        Assertions.assertEquals(
                0,
                getCartBadgeCount(),
                "Cart badge should disappear after removing item"
        );
    }
}