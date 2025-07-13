package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public boolean isElementDisplayed(By locator) {
        return findElement(locator).isDisplayed();
    }

    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }
    public void waitForElementToBeVisible(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void clickOnObject(By locator) {
        findElement(locator).click();
    }
    public void acceptCookiesIfPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            By cookieButton = By.id("wt-cli-accept-all-btn");
            WebElement acceptBtn = wait.until(ExpectedConditions.elementToBeClickable(cookieButton));
            acceptBtn.click();
            System.out.println("✅ Cookies accepted.");
        } catch (Exception e) {
            System.out.println("Cookie banner not found or not clickable.");
        }
    }
}

