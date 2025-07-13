package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Check if homepage is loaded by checking visibility of main navigation bar
    public boolean isHomePageLoaded() {
        WebElement navBar = driver.findElement(By.xpath("//*[@id=\"desktop_hero_24\"]"));
        return navBar.isDisplayed();
    }

    // Navigate to Careers page via Company menu
    public void goToCareersPage() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement companyMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Company')]")));

        Actions actions = new Actions(driver);
        actions.moveToElement(companyMenu).perform();  // mouse over

        WebElement careersLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Careers')]")));
        careersLink.click();
    }
}
