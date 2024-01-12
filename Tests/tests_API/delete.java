package tests_API;

import Extensions.API;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class delete extends API
{
    @Test(testName = "Valid user - Delete user", description = "Delete a user with a valid ID", groups = {"Users, Sanity, delete"})
    public void deleteValidUserId() throws ParserConfigurationException, IOException, SAXException {
        object.put("id", getData("Id", 1));
        deleteByObjectId("user", object);
    }

    @Test(testName = "Invalid user - Non existing user ID", description = "Delete a user with a non existing ID", groups = {"Users, Negative, Functional, delete"})
    public void deleteNonExistingUserId() throws ParserConfigurationException, IOException, SAXException {
        object.put("id", "120");
        deleteByObjectId("user", object);
    }

}
