package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CareerPage;
import pages.HomePage;
import pages.QAJobsPage;

import java.time.Duration;

public class QATest {

    private WebDriver driver;
    private HomePage homePage;
    private CareerPage careerPage;
    private QAJobsPage qaJobsPage;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        homePage = new HomePage(driver);
        careerPage = new CareerPage(driver);
        qaJobsPage = new QAJobsPage(driver);
    }

    @Test(priority = 1, description = "Verify that Insider homepage is opened successfully.")
    public void testHomePageIsOpened() {
        driver.get("https://useinsider.com/");
        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page is not loaded!");
    }

    @Test(priority = 2, description = "Verify that Career page blocks (Locations, Teams, Life at Insider) are visible.")
    public void testCareerPageBlocksAreVisible() {
        homePage.goToCareersPage();

        Assert.assertTrue(careerPage.isLocationsBlockVisible(), "Locations block not visible!");
        Assert.assertTrue(careerPage.isTeamsBlockVisible(), "Teams block not visible!");
        Assert.assertTrue(careerPage.isLifeAtInsiderBlockVisible(), "Life at Insider block not visible!");
    }

    @Test(priority = 3, description = "Verify succesful filtering of QA jobs by location and department. " +
            "Redirects to application form. May Department selection fail on 13inch due to layout shift but it selects Senior Software Quality Assurance Engineer job")

    public void testQAJobsFilterAndValidation() throws InterruptedException {

        driver.get("https://useinsider.com/careers/quality-assurance/");

        qaJobsPage.clickSeeAllQAJobs();
        qaJobsPage.filterJobsByLocation("Istanbul, Turkiye");
        qaJobsPage.filterJobsByDepartment("Quality Assurance");
        Thread.sleep(2000);
        qaJobsPage.waitUntilJobsAreLoaded();

        WebElement firstJob = driver.findElement(By.cssSelector("div.position-list-item"));

        Assert.assertTrue(firstJob.getText().contains("Quality Assurance"), "❌ Job not filtered by department!");
        Assert.assertTrue(firstJob.getText().contains("Istanbul"), "❌ Job not filtered by location!");
    }

    @Test(priority = 4, description = "Verify that clicking 'View Role' redirects to the Lever application form.")
    public void testViewRoleRedirectsToLeverPage() throws InterruptedException {
        qaJobsPage.clickFirstViewRoleAndSwitchToNewTab();
        Assert.assertTrue(driver.getCurrentUrl().contains("lever.co"), "Not redirected to Lever application form.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
