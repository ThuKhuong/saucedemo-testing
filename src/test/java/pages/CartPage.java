package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

    private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }
    private By checkoutBtn = By.id("checkout");
    private By continueShoppingBtn = By.id("continue-shopping");
    private By removeBackpackBtn = By.id("remove-sauce-labs-backpack");
    private By firstProductName = By.className("inventory_item_name");

    public void clickCheckout() {
        driver.findElement(checkoutBtn).click();
    }

    public void clickContinueShopping() {
        driver.findElement(continueShoppingBtn).click();
    }

    public void removeBackpack() {
        driver.findElement(removeBackpackBtn).click();
    }

    public String getFirstProductName() {
        return driver.findElement(firstProductName).getText();
    }
}