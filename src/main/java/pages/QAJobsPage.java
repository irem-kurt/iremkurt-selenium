package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class QAJobsPage extends BasePage {
    private By departmentFilterQATitle = By.cssSelector("#select2-filter-by-department-container[title='Quality Assurance']");
    private By locationFilter = By.cssSelector("#select2-filter-by-location-container");
    private By departmentFilter = By.cssSelector("#select2-filter-by-department-container");

    public QAJobsPage(WebDriver driver) {
        super(driver);
    }
    public void clickSeeAllQAJobs() {
        acceptCookiesIfPresent();
        WebElement seeAllQAJobsBtn = driver.findElement(By.xpath("//a[contains(text(),'See all QA jobs')]"));
        seeAllQAJobsBtn.click();
    }
    public void filterJobsByLocation(String location) throws InterruptedException {
        waitForElementToBeVisible(departmentFilterQATitle, 20); //Added due to long loading time of job filters.
        clickOnObject(locationFilter);
        clickOnObject(By.cssSelector("li[data-select2-id*= '" + location + "' ]"));
        //Thread.sleep(1000); // Wait for the selection to register
    }
    public void filterJobsByDepartment(String department) {
        waitForElementToBeVisible(departmentFilterQATitle, 20); //Added due to long loading time of job filters.
        clickOnObject(departmentFilter);
        //clickOnObject(By.cssSelector("li[data-select2-id*= '"+department+"' ]"));
        clickOnObject(By.xpath("//li[contains(@class,'select2-results__option') and text()='" + department + "']"));

        System.out.println("✅ Department selected using native <select>: " + department);
    }
    public void clickFirstViewRoleAndSwitchToNewTab() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. Locate the first job card (position)
        By jobCardSelector = By.cssSelector("div.position-list > div.position-list-item");
        // Updated selector to match the new structure
        wait.until(ExpectedConditions.presenceOfElementLocated(jobCardSelector)); // wait for stability

        WebElement jobCard = driver.findElements(jobCardSelector).get(0); // Get the first job card
        js.executeScript("arguments[0].scrollIntoView(true);", jobCard);
        Thread.sleep(500);

        try {
            // 2. Mouse over the job card to reveal the 'View Role' button
            actions.moveToElement(jobCard).perform();
            Thread.sleep(500);

            // 3. Click on the 'View Role' button
            WebElement viewRoleBtn = jobCard.findElement(By.xpath(".//a[contains(text(), 'View Role')]"));
            String originalTab = driver.getWindowHandle();
            viewRoleBtn.click();

            // 4. Wait for the new tab to open and switch to it
            Set<String> allTabs = driver.getWindowHandles();
            for (String tab : allTabs) {
                if (!tab.equals(originalTab)) {
                    driver.switchTo().window(tab);
                    break;
                }
            }

        } catch (StaleElementReferenceException e) {

            WebElement jobCardRetry = driver.findElements(jobCardSelector).get(0);
            WebElement viewRoleRetry = jobCardRetry.findElement(By.xpath(".//a[contains(text(), 'View Role')]"));
            System.out.println("Retrying to click View Role button..." + viewRoleRetry.getText());
            viewRoleRetry.click();

            Set<String> allTabs = driver.getWindowHandles();
            for (String tab : allTabs) {
                if (!tab.equals(driver.getWindowHandle())) {
                    driver.switchTo().window(tab);
                    break;
                }
            }
        }
    }
    public void waitUntilJobsAreLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div.position-list-item")
        ));
        System.out.println("✅ Job list loaded.");
    }
    public List<WebElement> getAllVisibleJobCards() {
        return driver.findElements(By.cssSelector("div.position-list-item"));
    }
    public void scrollIntoElement() throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(1000);
    }

}
