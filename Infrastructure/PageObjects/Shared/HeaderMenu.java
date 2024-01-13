package PageObjects.Shared;

import Extensions.assertions;
import Utilities.common;
import com.relevantcodes.extentreports.LogStatus;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.testng.Assert.fail;

public class HeaderMenu extends common {
    @FindBy(xpath = "//li[@class='li_8cxs15']")
    private List<WebElement> header_titles;

    @Step("Iterate and assert headers menu titles")
    public void validateHeadersMenuTitles() {
        try {
            for (WebElement element : header_titles) {
                test.log(LogStatus.INFO, "Current title", String.format("\"%s\"", element.getText()));
                assertions.assertEqualsString(element.getText(), getData("Title", header_titles.indexOf(element)));
            }
        }
        catch (Exception e) {
            test.log(LogStatus.FAIL, "Title validation", "Please handle the exception: " + e.getMessage());
            softAssert.fail(e.getMessage());
            fail();
        }
        catch (AssertionError ae) {
            test.log(LogStatus.FAIL, "Title validation", "Please handle the assertion error: " + ae.getMessage());
            softAssert.fail(ae.getMessage());
            fail();
        }
    }
}
