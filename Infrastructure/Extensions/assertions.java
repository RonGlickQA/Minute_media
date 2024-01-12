package Extensions;

import Utilities.common;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;

public class assertions extends common
{
    public static void assertEqualsString(String actual, String expected)
    {
        try
        {
            Assert.assertEquals(actual, expected);
            test.log(LogStatus.PASS, "Assertion - String", "String values are equal to \"" + expected + "\"");
        }
        catch (Exception e)
        {
            test.log(LogStatus.FAIL, "Assertion - String", "Please handle the exception: " + e.getMessage());
            softAssert.fail(e.getMessage());
        }
        catch (AssertionError e)
        {
            softAssert.assertEquals(actual, expected);
            test.log(LogStatus.ERROR,"Assertion - String", "Actual string is " + "\"" + actual + "\"" + ", instead of expected " + "\"" + expected + "\"");
            test.log(LogStatus.INFO,"Assertion error - String","Further information: " + e.getMessage());
            softAssert.fail(e.getMessage());
        }
    }

    public static void assertEqualsInt(int actual, int expected)
    {
        try
        {
            Assert.assertEquals(actual, expected);
            test.log(LogStatus.PASS, "Assertion - Int", "Values are both equal to \'" + expected + "\'");
        }
        catch (Exception e)
        {
            test.log(LogStatus.FAIL, "Assertion - Int", "Please handle the exception: " + e.getMessage());
            softAssert.fail(e.getMessage());
        }
        catch (AssertionError e)
        {
            softAssert.assertEquals(actual, expected);
            test.log(LogStatus.FAIL,"Assertion - Int", "Value of element is " + "\"" + actual + "\"" + ", instead of " + "\"" + expected + "\"");
            test.log(LogStatus.ERROR,"Assertion error - Int","Further information: " + e.getMessage());
            softAssert.fail(e.getMessage());
        }
    }
}
