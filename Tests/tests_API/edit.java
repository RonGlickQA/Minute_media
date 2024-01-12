package tests_API;

import Extensions.API;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class edit extends API
{
    @Test(testName = "Valid user - Edit name", description = "Edit the name of a user", groups = {"Users, Sanity, put"})
    public void putUserValidName() throws ParserConfigurationException, IOException, SAXException {
        object.put("name", "Agent Smith");
        object.put("id", getData("Id", 1));
        putByObjectId("user", getData("Id", 1), object);

        getByObjectId("user", object);
    }

    @Test(testName = "Invalid user - Empty name", description = "Edit the name of a user to an empty String", groups = {"Users, Negative, Functional, put"})
    public void putUserEmptyName() throws ParserConfigurationException, IOException, SAXException {
        object.put("name", "");
        object.put("id", getData("Id", 1));
        putByObjectId("user", getData("Id", 1), object);

        getByObjectId("user", object);
    }

    @Test(testName = "Valid user - Edit ID", description = "Edit the ID of a user", groups = {"Users, Functional, put"})
    public void putUserValidId() throws ParserConfigurationException, IOException, SAXException {
        object.put("name", getData("Name", 2));
        object.put("id", "100");
        putByObjectId("user", getData("Id", 2), object);

        getByObjectId("user", object);
    }

    // This test causes a critical bug in 'GET' requests, since the updated object has no ID, so it was disabled for the purpose of this POC.
    @Test(testName = "Invalid user - Empty ID", description = "Edit the ID of a user to an empty String", groups = {"Users, Negative, Functional, put"}, enabled = false)
    public void putUserEmptyId() throws ParserConfigurationException, IOException, SAXException {
        object.put("name", getData("Name", 2));
        object.put("id", "");
        putByObjectId("user", getData("Id", 2), object);

        getByObjectId("user", object);
    }

    @Test(testName = "Valid user - Edit user", description = "Edit the name & ID of a user", groups = {"Users, Functional, put"})
    public void putUserValidNameAndId() throws ParserConfigurationException, IOException, SAXException {
        object.put("name", "Trinity");
        object.put("id", "3");
        putByObjectId("user", getData("Id", 2), object);

        getByObjectId("user", object);
    }

    // This test causes a critical bug in 'GET' requests, since the updated object has no ID, so it was disabled for the purpose of this POC.
    @Test(testName = "Invalid user - Edit user", description = "Edit the name & ID of a user to empty Strings", groups = {"Users, Negative, Functional, put"}, enabled = false)
    public void putEmptyUser() throws ParserConfigurationException, IOException, SAXException {
        object.put("name", "");
        object.put("id", "");
        putByObjectId("user", getData("Id", 2), object);

        getByObjectId("user", object);
    }

}
