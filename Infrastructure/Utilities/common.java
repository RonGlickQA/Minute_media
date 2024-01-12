package Utilities;

import com.relevantcodes.extentreports.LogStatus;
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

public class common extends base
{
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() throws ParserConfigurationException, SAXException, IOException
    {
    }

    @BeforeTest(alwaysRun = true)
    public void initReport() throws ParserConfigurationException, SAXException, IOException
    {
        init.initExtentReport();
    }

    @BeforeClass(alwaysRun = true)
    public void initSession() throws ParserConfigurationException, SAXException, IOException
    {
    }

    @BeforeMethod(alwaysRun = true)
    public void openSession(Method method) throws IOException, SAXException, ParserConfigurationException
    {
        Test test = method.getAnnotation(Test.class);
        init.initTestReport(getClass().getPackageName() + " - " + test.testName(), test.description());

        init.initJSONObject();
        init.initQueryParams();
        init.initSoftAssert();
    }

    @AfterMethod(alwaysRun = true)
    public void closeSession() throws ParserConfigurationException, SAXException, IOException
    {
        init.finalizeTestReport();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass()
    {
    }

    @AfterTest(alwaysRun = true)
    public void finalizeReport()
    {
        init.finalizeExtentReport();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite()
    {
    }

    public static String getConfig(String nodeName) throws ParserConfigurationException, SAXException, IOException //Reads and sets a configuration variable from XML file that has only 1 value.
    {
        File fXmlFile = new File("./Configuration/config.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(nodeName).item(0).getTextContent();
    }

    public static String getData(String nodeName) throws ParserConfigurationException, SAXException, IOException //Reads and sets a data variable from XML file that has only 1 value.
    {
        File fXmlFile = new File("./Data/data.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(nodeName).item(0).getTextContent();
    }

    public static String getData(String nodeName, int i) throws ParserConfigurationException, SAXException, IOException //Reads and sets a data variables from XML file that has more than  1 value.
    {
        File fXmlFile = new File("./Data/data.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(nodeName).item(i).getTextContent();
    }

    public static String getReportDate()
    {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        return date = ZonedDateTime.now().toLocalDate().format(dateFormat);
    }

    public static String getTime()
    {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH_mm_ss");
        return time = ZonedDateTime.now().toLocalTime().format(timeFormat);
    }

    public static void getSoftAssertStatus()
    {
        try
        {
            softAssert.assertAll();
            test.log(LogStatus.PASS, "Test success", "No failures detected");
        }
        catch (AssertionError e)
        {
            test.log(LogStatus.FAIL, "Test failure", e.getMessage());
        }
    }
}
