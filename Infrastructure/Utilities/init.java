package Utilities;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.json.JSONObject;
import org.testng.asserts.SoftAssert;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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
