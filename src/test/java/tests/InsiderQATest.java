package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.CareerPage;
import pages.HomePage;
import pages.QAJobsPage;

import java.time.Duration;

public class InsiderQATest {

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

    @Test(priority = 1)
    public void testHomePageIsOpened() {
        driver.get("https://useinsider.com/");
        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page is not loaded!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
