package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CareerPage extends BasePage {

    public CareerPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLocationsBlockVisible() {
        WebElement locations = driver.findElement(By.xpath("//h3[contains(text(),'Find your calling')]"));
        return locations.isDisplayed();
    }

    public boolean isTeamsBlockVisible() {
        WebElement teams = driver.findElement(By.xpath("//*[@id=\"career-find-our-calling\"]/div/div/a"));
        return teams.isDisplayed();
    }

    public boolean isLifeAtInsiderBlockVisible() {
        WebElement lifeAtInsider = driver.findElement(By.xpath("/html/body/div[1]/section[4]/div/div/div/div[1]/div/h2"));
        return lifeAtInsider.isDisplayed();
    }
}
