package tests_API;

import Extensions.API;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class get extends API
{
    @Test(testName = "Valid users - Get all users", description = "Get all users", groups = {"Users, Sanity, get"})
    public void getAllObjects() throws ParserConfigurationException, IOException, SAXException {
        object.put("id", "");
        getByObjectId("user", object);
    }

    @Test(testName = "Valid user - Get user", description = "Get a valid user by its ID", groups = {"Users, Sanity, get"})
    public void getValidObject() throws ParserConfigurationException, IOException, SAXException {
        object.put("name", getData("Name", 1));
        object.put("id", getData("Id", 1));
        getByObjectId("user", object);
    }

    // Test is equivalent to 'post.postDuplicateObjects' only since there are no objects to start from.
    @Test(testName = "Invalid user - Duplicated Id", description = "Get users that have duplicate IDs", groups = {"Users, Functional, get"})
    public void getDuplicatedIdObject() throws ParserConfigurationException, IOException, SAXException {
        object.put("name", getData("Name", 2));
        object.put("id", getData("Id", 2));
        getByObjectId("user", object);
    }

    @Test(testName = "Invalid user - Non existing ID", description = "Get a user with non existing ID (404)", groups = {"Users, Functional, get"})
    public void getNonExistingIdObject() throws ParserConfigurationException, IOException, SAXException {
        object.put("id", getData("Id", 4));
        getByObjectId("user", object);
    }

    // Test is equivalent to 'edit.putValidObject' only since there are no objects to start from.
    @Test(testName = "Valid user - Edited object", description = "Get a user that was edited", groups = {"Users, Functional, get"})
    public void getEditedObject() throws ParserConfigurationException, IOException, SAXException {
        object.put("name", getData("Name", 2));
        object.put("id", getData("Id", 2));
        getByObjectId("user", object);
    }

    // Test is equivalent to 'delete.deleteValidObject' only since there are no objects to start from.
    @Test(testName = "Invalid user - Deleted Id", description = "Get a user that was deleted", groups = {"Users, Negative, Functional, get"}, enabled = false)
    public void getDeletedIdObject() throws ParserConfigurationException, IOException, SAXException {
        object.put("id", getData("Id", 2));
        getByObjectId("user", object);
    }


}
