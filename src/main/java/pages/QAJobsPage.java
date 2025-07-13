package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class QAJobsPage<CareersPage> extends BasePage {

    public QAJobsPage(WebDriver driver) {
        super(driver);
    }

    public void clickSeeAllQAJobs() {
        acceptCookiesIfPresent();
        WebElement seeAllQAJobsBtn = driver.findElement(By.xpath("//a[contains(text(),'See all QA jobs')]"));
        seeAllQAJobsBtn.click();
    }

    /*public void selectLocation(String location) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Step 1: Click on the Location Select2 container
        WebElement locationDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("span#select2-filter-by-location-container")));
        locationDropdown.click();
        Thread.sleep(500); // Allow time for the dropdown to open
        locationDropdown.click();
        Thread.sleep(500); // Allow time for the dropdown to open

        // Step 2: Type into the input that appears
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#top-filter-form > div:nth-child(1) > span > span.selection > span")));
        searchBox.sendKeys(location);

        WebElement result2 = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("li[data-select2-id*= '"+location+"' ]")));
        Thread.sleep(500); // Allow time for the options to load
        result2.click();

        // Step 3: Wait for and click the matching dropdown result
        WebElement result = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[contains(@class,'select2-results__option') and text()='" + location + "']")));
        result.click();
        Thread.sleep(500); // Allow time for the selection to register
        result.click();
        Thread.sleep(500); // Allow time for the selection to register
        result.click();Thread.sleep(500); // Allow time for the selection to register
        result.click(); // Sometimes it requires double-click
        System.out.println("Location filter applied: " + location);


    }*/
    private By departmentFilter = By.cssSelector("#select2-filter-by-department-container");
    private By departmentFilterQATitle = By.cssSelector("#select2-filter-by-department-container[title='Quality Assurance']");
    private By locationFilter = By.cssSelector("#select2-filter-by-location-container");

    public void filterJobsByLocation(String location) throws InterruptedException {
        waitForElementToBeVisible(departmentFilterQATitle, 20); //Added due to long loading time of job filters.
        clickOnObject(locationFilter);
        clickOnObject(By.cssSelector("li[data-select2-id*= '" + location + "' ]"));
        //Thread.sleep(1000); // Wait for the selection to register
    }

    public void filterJobsByDepartment(String department) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 1. Wait for the native <select> to be present
        WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("filter-by-department")));

        // 2. Use Select to choose by visible text
        Select departmentDropdown = new Select(selectElement);
        departmentDropdown.selectByVisibleText(department);

        System.out.println("✅ Department selected using native <select>: " + department);
    }

    public void clickFirstViewRoleAndSwitchToNewTab() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. Locate the first job card (position)
        By jobCardSelector = By.cssSelector("div.position-list.position-list.col-12.d-flex.flex-wrap.mt-5.pl-2.pr-2 > div");
        wait.until(ExpectedConditions.presenceOfElementLocated(jobCardSelector)); // wait for stability

        WebElement jobCard = driver.findElements(jobCardSelector).get(0); // En baştaki kart
        js.executeScript("arguments[0].scrollIntoView(true);", jobCard);
        Thread.sleep(500);

        try {
            // 2. Mouse ove r the job card to reveal the 'View Role' button
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


    public boolean isJobListVisible() {
        try {
            return isElementDisplayed(By.cssSelector("#jobs-list"));
        } catch (Exception e) {
            System.out.println("Can't find job list");
            return false;
        }
    }

    public void clickFirstViewRoleAndSwitchToNewTab2() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Step 1: Locate the first job card (position)
        WebElement jobCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.position-list.position-list.col-12.d-flex.flex-wrap.mt-5.pl-2.pr-2 > div"))); // first child card
        System.out.println("1_Scrolling into the first job element: " + jobCard.getText());
        Thread.sleep(500);
        js.executeScript("arguments[0].scrollIntoView(true);", jobCard);
        Thread.sleep(500); // let animation complete

        // Step 2: Hover to trigger the View Role button to appear
        actions.moveToElement(jobCard).perform();
        System.out.println("2_Hovered over the job card to reveal View Role button.");
        Thread.sleep(500); // allow View Role to appear

        // Step 3: Click on the visible "View Role" button
        WebElement viewRoleBtn = jobCard.findElement(By.xpath(".//a[contains(text(), 'View Role')]"));
        String originalTab = driver.getWindowHandle();
        System.out.println("3_Clicking on View Role button: " + viewRoleBtn.getText());
        viewRoleBtn.click();
        Thread.sleep(1000); // wait for new tab to open
        System.out.println("4_View Role button clicked, new tab should open.");

        // Step 4: Switch to new tab
        Set<String> allTabs = driver.getWindowHandles();
        Thread.sleep(500); // wait for tabs to be ready
        for (String tab : allTabs) {
            if (!tab.equals(originalTab)) {
                System.out.println("5_Switching to new tab: " + tab);
                Thread.sleep(500); // wait for tab switch
                driver.switchTo().window(tab);
                break;
            }
        }

    }

}
