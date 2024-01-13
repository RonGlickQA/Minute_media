package Utilities;

import com.relevantcodes.extentreports.LogStatus;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.testng.Assert.fail;

public class common extends base {
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() throws ParserConfigurationException, SAXException, IOException {
    }

    /**
     * Sets & initializes required components by test platform (currently API by default).
     * @param platformName - Tests platform (API/UI) - Optional parameter to run individually, would be set automatically when executed from 'run.xml'.
     * @param browserName - UI test browser - Optional parameter to run individually, would be set automatically when executed from 'run.xml'.
     * @param timeout - Timeout limit - Optional parameter to run individually, would be set automatically when executed from 'run.xml'.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    @BeforeTest(alwaysRun = true)
    @Parameters({"PlatformName", "BrowserName", "Timeout"})
    @Description("initialization of report's instances")
    public void beforeTest(@Optional("Please select a platform (API/WEB)") String platformName, @Optional("Please select a browser (Chrome/Firefox)") String browserName, @Optional("Please select timeout limit") String timeout) throws ParserConfigurationException, SAXException, IOException {
        try {
            init.initExtentReport();
            platform = platformName;
            switch (platformName.toLowerCase()) {
                case "web":
                    init.initBrowser(browserName);
                    driver.manage().window().maximize();
                    driver.get(getData("URL", 1));
                    init.initWait(timeout);
                    init.initPages();
                    break;
                case "mobile":
                    // Todo: Add mobile initialization & handling
                    break;
                default:
                    break;
            }
        }
        catch (Exception e) {
            test.log(LogStatus.FAIL, "Get OBJECT", "Please handle the exception: " + e.getMessage());
            softAssert.fail(e.getMessage());
            fail();
        }
        catch (AssertionError ae) {
            test.log(LogStatus.FAIL, "HTTP response", "Please handle the assertion error: " + ae.getMessage());
            softAssert.fail(ae.getMessage());
            fail();
        }
    }

    @BeforeClass(alwaysRun = true)
    public void initSession() throws ParserConfigurationException, SAXException, IOException {
    }

    @BeforeMethod(alwaysRun = true)
    @Description("Initialization of method's instances")
    public void openSession(Method method) throws IOException, SAXException, ParserConfigurationException {
        Test test = method.getAnnotation(Test.class);
        init.initTestReport(getClass().getPackageName() + " - " + test.testName(), test.description());

        init.initJSONObject();
        init.initQueryParams();
        init.initSoftAssert();
    }

    @AfterMethod(alwaysRun = true)
    @Step("Summary of soft assertions")
    @Description("Closing test-case's execution")
    public void closeSession() throws ParserConfigurationException, SAXException, IOException {
        init.finalizeTestReport();
    }

    @AfterClass(alwaysRun = true)
    @Parameters({"PlatformName"})
    public void afterClass(@Optional("Please select a browser (Chrome/Firefox)") String platformName) {
        platform = platformName;
        if (platformName.equalsIgnoreCase("web") || platformName.equalsIgnoreCase("mobile")) {
            driver.quit();
        }
    }

    @AfterTest(alwaysRun = true)
    @Description("Finalization of report's instances")
    public void finalizeReport() {
        init.finalizeExtentReport();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
    }

    /**
     * Reads and sets a configuration variable from XML file that has only 1 value.
     * @param nodeName - Specified name of the requested node of 'config.xml'.
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static String getConfig(String nodeName) throws ParserConfigurationException, SAXException, IOException
    {
        File fXmlFile = new File("./Configuration/config.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(nodeName).item(0).getTextContent();
    }

    /**
     * Reads and sets a data variable from XML file that has only 1 value.
     * @param nodeName - Specified name of the requested node of 'data.xml'.
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static String getData(String nodeName) throws ParserConfigurationException, SAXException, IOException
    {
        File fXmlFile = new File("./Data/data.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(nodeName).item(0).getTextContent();
    }

    /**
     * Reads and sets a data variables from XML file that has more than  1 value.
     * @param nodeName - Specified name of the requested node of 'data.xml'.
     * @param i - Node index.
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static String getData(String nodeName, int i) throws ParserConfigurationException, SAXException, IOException
    {
        File fXmlFile = new File("./Data/data.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(nodeName).item(i).getTextContent();
    }

    /**
     * Initializes reports' date of execution.
     * @return
     */
    public static String getReportDate() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        return date = ZonedDateTime.now().toLocalDate().format(dateFormat);
    }

    /**
     * Initializes reports' time of execution.
     * @return
     */
    public static String getTime() {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH_mm_ss");
        return time = ZonedDateTime.now().toLocalTime().format(timeFormat);
    }

    /**
     * Get soft assertions status at the end of an execution.
     */
    public static void getSoftAssertStatus() {
        try {
            softAssert.assertAll();
            test.log(LogStatus.PASS, "Test success", "No failures detected");
        } catch (AssertionError e) {
            test.log(LogStatus.FAIL, "Test failure", e.getMessage());
        }
    }
}
