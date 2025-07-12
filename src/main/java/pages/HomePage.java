package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
        WebElement companyMenu = driver.findElement(By.xpath("//*[@id=\"navbarDropdownMenuLink\"]"));
        companyMenu.click();

        WebElement careersLink = driver.findElement(By.xpath("//*[@id=\"navbarNavDropdown\"]/ul[1]/li[6]/div/div[2]/a[2]"));
        careersLink.click();
    }
}
