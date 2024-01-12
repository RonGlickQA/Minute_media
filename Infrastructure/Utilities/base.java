package Utilities;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.asserts.SoftAssert;

public class base
{
    public static ExtentReports report;
    public static ExtentTest test;

    public static SoftAssert softAssert;

    public static RequestSpecification httpRequest;
    public static Response response;
    public static JsonPath jp;

    public static String URL, query, date, time;

    public static JSONObject object;


}
