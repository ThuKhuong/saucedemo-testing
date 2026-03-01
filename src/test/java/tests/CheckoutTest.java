package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class CheckoutTest extends BaseTest {

    private void addBackpackToCartAndOpenCheckout() {
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        openCart();
        driver.findElement(By.id("checkout")).click();
    }

    @Test
    void TC_CHECKOUT_01_checkoutSuccessfully() {

        login("standard_user", "secret_sauce");
        addBackpackToCartAndOpenCheckout();

        driver.findElement(By.id("first-name")).sendKeys("Lê");
        driver.findElement(By.id("last-name")).sendKeys("Khương");
        driver.findElement(By.id("postal-code")).sendKeys("70000");
        driver.findElement(By.id("continue")).click();
        driver.findElement(By.id("finish")).click();

        String result = driver.findElement(By.className("complete-header")).getText();

        Assertions.assertEquals("Thank you for your order!", result);
        Assertions.assertTrue(driver.getCurrentUrl().contains("checkout-complete"));
    }

    @Test
    void TC_CHECKOUT_02_missingFirstName() {

        login("standard_user", "secret_sauce");
        addBackpackToCartAndOpenCheckout();

        driver.findElement(By.id("last-name")).sendKeys("Khương");
        driver.findElement(By.id("postal-code")).sendKeys("70000");
        driver.findElement(By.id("continue")).click();

        String error = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();

        Assertions.assertTrue(error.contains("First Name is required"));
    }

    @Test
    void TC_CHECKOUT_03_missingLastName() {

        login("standard_user", "secret_sauce");
        addBackpackToCartAndOpenCheckout();

        driver.findElement(By.id("first-name")).sendKeys("Lê");
        driver.findElement(By.id("postal-code")).sendKeys("70000");
        driver.findElement(By.id("continue")).click();

        String error = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();

        Assertions.assertTrue(error.contains("Last Name is required"));
    }

    @Test
    void TC_CHECKOUT_04_missingPostalCode() {

        login("standard_user", "secret_sauce");
        addBackpackToCartAndOpenCheckout();

        driver.findElement(By.id("first-name")).sendKeys("Lê");
        driver.findElement(By.id("last-name")).sendKeys("Khương");
        driver.findElement(By.id("continue")).click();

        String error = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();

        Assertions.assertTrue(error.contains("Postal Code is required"));
    }

    @Test
    void TC_CHECKOUT_05_cancelCheckoutStep1() {

        login("standard_user", "secret_sauce");
        addBackpackToCartAndOpenCheckout();

        driver.findElement(By.id("cancel")).click();

        Assertions.assertTrue(driver.getCurrentUrl().contains("cart"));
    }

    @Test
    void TC_CHECKOUT_06_cancelCheckoutStep2() {

        login("standard_user", "secret_sauce");
        addBackpackToCartAndOpenCheckout();

        driver.findElement(By.id("first-name")).sendKeys("Lê");
        driver.findElement(By.id("last-name")).sendKeys("Khương");
        driver.findElement(By.id("postal-code")).sendKeys("70000");
        driver.findElement(By.id("continue")).click();

        driver.findElement(By.id("cancel")).click();

        Assertions.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Test
    void TC_CHECKOUT_07_emptyCartStillCheckout() {

        login("standard_user", "secret_sauce");

        openCart();

        Assertions.assertEquals(
                0,
                driver.findElements(By.className("cart_item")).size(),
                "Precondition failed: cart is not empty"
        );

        driver.findElement(By.id("checkout")).click();

        driver.findElement(By.id("first-name")).sendKeys("Lê");
        driver.findElement(By.id("last-name")).sendKeys("Khương");
        driver.findElement(By.id("postal-code")).sendKeys("70000");
        driver.findElement(By.id("continue")).click();

        Assertions.assertTrue(
                driver.getCurrentUrl().contains("checkout-step-two"),
                "Should be on Overview page"
        );

        Assertions.fail("BUG: Checkout succeeds with empty cart");
    }
}