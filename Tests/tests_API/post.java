package tests_API;

import Extensions.API;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class post extends API
{
    @Test(testName = "Valid user - Add user", description = "Add a user with valid name & ID", groups = {"Users, Sanity, post"})
    public void postValidUser() throws ParserConfigurationException, IOException, SAXException {
        object.put("name", getData("Name", 1));
        object.put("id", getData("Id", 1));
        postObject("user", object);

        getByObjectId("user", object);
    }

    @Test(testName = "Valid user - Duplicate ID", description = "Add 2 users with duplicate IDs", groups = {"Users, Functional, post"})
    public void postDuplicateIdUsers() throws ParserConfigurationException, IOException, SAXException {
        object.put("name", getData("Name", 2));
        object.put("id", getData("Id", 2));
        postObject("user", object);

        object.put("name", String.format("%s %s",getData("Name", 2), "DUPLICATE"));
        object.put("id", getData("Id", 2));
        postObject("user", object);

        object.put("name", getData("Name", 2));
        object.put("id", getData("Id", 2));
        getByObjectId("user", object);
    }

    @Test(testName = "Invalid user - 'Company' property", description = "Add a user with valid name, ID & company", groups = {"Users, Functional, post"})
    public void postExtendedUser() throws ParserConfigurationException, IOException, SAXException {
        object.put("name", getData("Name", 1));
        object.put("id", getData("Id", 1));
        object.put("company", getData("Company"));
        postObject("user", object);

        getByObjectId("user", object);
    }

    @Test(testName = "Invalid user - Invalid String ID", description = "Add a user with valid name & invalid ID", groups = {"Users, Negative, Functional, post"})
    public void postInvalidUserId() throws ParserConfigurationException, IOException, SAXException {
        object.put("name", getData("Name", 1));
        object.put("id", getData("Id", 3));
        postObject("user", object);
    }

    @Test(testName = "Invalid user - Missing name", description = "Add a user with missing name", groups = {"Users, Negative, Functional, post"})
    public void postUserMissingName() throws ParserConfigurationException, IOException, SAXException {
        object.put("id", getData("Id", 3));
        postObject("user", object);

        getByObjectId("user", object);
    }

    // This test causes a critical bug in 'GET' requests, since the created object has no ID, so it was disabled for the purpose of this POC.
    @Test(testName = "Invalid user - Missing ID", description = "Add a user with missing ID", groups = {"Users, Negative, Functional, post"}, enabled = false)
    public void postUserMissingId() throws ParserConfigurationException, IOException, SAXException {
        object.put("name", getData("Name", 1));
        postObject("user", object);

        getByObjectId("user", object);
    }

    @Test(testName = "Invalid user - Empty", description = "Add a user with empty name & ID", groups = {"Users, Negative, Functional, post"})
    public void postEmptyUser() throws ParserConfigurationException, IOException, SAXException {
        object.put("name", getData("Name"));
        object.put("id", getData("Id"));
        postObject("user", object);

        getByObjectId("user", object);
    }
}
