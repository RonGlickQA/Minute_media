package tests_UI;

import Utilities.common;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class selenium extends common {
    @Test
    @Description("This test opens the '90min.com' homepage, going over headers titles, provided by a common class & validates them.")
    public void validate_headers_menu_titles() throws ParserConfigurationException, IOException, SAXException {
        headerMenu.validateHeadersMenuTitles();
    }
}
