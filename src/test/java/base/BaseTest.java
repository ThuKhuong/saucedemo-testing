package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    protected void login(String username, String password) {
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys(username);

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(password);

        driver.findElement(By.id("login-button")).click();

        // wait until login success
        wait.until(ExpectedConditions.urlContains("inventory"));
    }

    protected void openCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
    }

    protected int getCartBadgeCount() {
        return driver.findElements(By.className("shopping_cart_badge")).size();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}