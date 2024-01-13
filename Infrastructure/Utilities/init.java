package Utilities;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

public class init extends common
{

    public static void initJSONObject()
    {
        object = new JSONObject();
    }
    public static void initQueryParams()
    {
        query = "";
    }

    public static WebDriver initBrowser(String browser) {
        switch (browser.toUpperCase()) {
            case "CHROME":
                return driver = initChromeBrowser();
            case "FIREFOX":
                return driver = initFireFoxBrowser();
            default:
                throw new RuntimeException("Invalid browser type");
        }
    }

    private static WebDriver initChromeBrowser() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        return driver;
    }

    private static WebDriver initFireFoxBrowser() {
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();
        return driver;
    }

    protected static WebDriverWait initWait(String timeout) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(timeout)));
        return wait;
    }

    protected static void initPages() {
        headerMenu = PageFactory.initElements(driver, PageObjects.Shared.HeaderMenu.class);
    }

    public static void initSoftAssert()
    {
        softAssert = new SoftAssert();
    }

    public static void initExtentReport() throws IOException, SAXException, ParserConfigurationException
    {
        report = new ExtentReports(getConfig("ReportPath") + getReportDate() + "\\" + getTime() + " - " + getConfig("ReportName"));
    }

    public static void initTestReport(String testName, String TestDescription)
    {
        test = report.startTest(testName, TestDescription);
        test.log(LogStatus.INFO, "Here we go", "The test has started");
    }

    public static void finalizeTestReport()
    {
        getSoftAssertStatus();
        test.log(LogStatus.INFO, "Wrap it up", "This test has ended");
        report.flush();
        report.endTest(test);
    }

    public static void finalizeExtentReport()
    {
        test.log(LogStatus.INFO, "Finished", "All tests have ended");
        report.flush();
        report.endTest(test);
        report.close();
    }
}
