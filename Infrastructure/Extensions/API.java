package Extensions;

import Utilities.common;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;


import static org.testng.Assert.fail;

public class API extends common {
    /**
     * Gets & validates status line & code of HTTP methods
     * @param method - HTTP method (e.g - 'POST', 'GET', 'PUT', 'DELETE')
     * @param response - HTTP response
     */
    protected void getStatusLine(String method, Response response) {
        try {
            performanceMetrics(response);

            if (response.statusCode() == 200 || response.statusCode() == 201 || response.statusCode() == 204) {
                test.log(LogStatus.PASS, "API status - '" + method + "'", "Returned status of '" + method + "' method" +
                        " is: " + response.getStatusLine());
            } else {
                test.log(LogStatus.ERROR, "API status - '" + method + "'", "Returned status of '" + method + "' " +
                        "method is: " + response.getStatusLine());
                test.log(LogStatus.ERROR, "API error message", "Returned error message of '" + method + "' " +
                        "method is: " + response.getBody().asString());
                softAssert.fail(response.getBody().asString());
                fail();
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Status line", "Please handle the exception: " + e.getMessage());
            softAssert.fail(e.getMessage());
            fail();
        } catch (AssertionError ae) {
            test.log(LogStatus.FAIL, "Status line", "Please handle the assertion error: " + ae.getMessage());
            softAssert.fail(ae.getMessage());
            fail();
        }
    }

    /**
     * Controls HTTP methods execution
     * @param method - HTTP method (e.g - 'POST', 'GET', 'PUT', 'DELETE')
     * @param type - Object's type (e.g - users)
     * @param query - Uses query parameters to extract certain objects
     * @param object - The object & its properties
     */
    private void httpRequest(String method, String type, String query, JSONObject object) {
        try {
            URL = getData("URL");
            switch (type.toLowerCase()) {
                case "user":
                    URL = String.format("%s/%s", URL, getData("Path", 1));
                    break;
            }
            if (!query.isEmpty()) {
                URL = String.format("%s/%s", URL, query);
            }
            test.log(LogStatus.INFO, "API path", URL);
            RestAssured.baseURI = URL;
            httpRequest = RestAssured.given();
            httpRequest.header("Content-Type", "application/json");
            switch (method.toUpperCase()) {
                case "POST":
                    httpRequest.body(object.toString());
                    response = httpRequest.post(URL);
                    getStatusLine(method.toUpperCase(), response);
                    test.log(LogStatus.INFO, "Response's body", response.body().asString());
                    jp = response.jsonPath();
                    break;
                case "GET":
                    response = httpRequest.get(URL);
                    getStatusLine(method.toUpperCase(), response);
                    test.log(LogStatus.INFO, "Response's body", response.body().asString());
                    jp = response.jsonPath();
                    if (!query.isEmpty()) {
                        assertions.assertEqualsString(jp.get("name"), object.getString("name"));
                        assertions.assertEqualsString(jp.get("id"), object.getString("id"));
                    } else {
                        List<Object> objectsCount = jp.getList(".");
                        int objectsCountSize = objectsCount.size();
                        test.log(LogStatus.INFO, "Objects count", String.format("Found %d objects", objectsCountSize));
                        for (int i = 0; i < objectsCountSize; ++i) {
                            test.log(LogStatus.INFO, String.format("Object #%d", i), objectsCount.get(i).toString());
                        }
                    }
                    break;
                case "PUT":
                    httpRequest.body(object.toString());
                    response = httpRequest.put(URL);
                    getStatusLine(method.toUpperCase(), response);
                    test.log(LogStatus.INFO, "Response's body", response.body().asString());
                    jp = response.jsonPath();
                    assertions.assertEqualsString(jp.get("name"), object.getString("name"));
                    assertions.assertEqualsString(jp.get("id"), object.getString("id"));
                    break;
                case "DELETE":
                    response = httpRequest.delete(URL);
                    getStatusLine(method.toUpperCase(), response);
                    jp = response.jsonPath();
                default:
                    test.log(LogStatus.ERROR, "Invalid method", String.format("The '%s' is invalid", method.toUpperCase()));
                    break;
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "HTTP response", "Please handle the exception: " + e.getMessage());
            softAssert.fail(e.getMessage());
            fail();
        } catch (AssertionError ae) {
            test.log(LogStatus.FAIL, "HTTP response", "Please handle the assertion error: " + ae.getMessage());
            softAssert.fail(ae.getMessage());
            fail();
        }
    }

    /**
     * Measures response's time & validates it's between the set boundaries.
     * @param response - HTTP response
     */
    private void performanceMetrics(Response response) {
        try {
            long metric = response.timeIn(TimeUnit.MILLISECONDS);
            if (metric < Integer.parseInt(getConfig("MinTime"))) {
                test.log(LogStatus.WARNING, "Performance measurement receded",
                        "Measured response time was " + metric + "ms, under the min. of " + getConfig("MinTime"));
                softAssert.fail("Low response time: " + metric + "ms");
            } else if (metric > Integer.parseInt(getConfig("MaxTime"))) {
                test.log(LogStatus.WARNING, "Performance measurement exceeded ",
                        "Measured response time was " + metric + "ms, above the max. of " + getConfig("MaxTime"));
                softAssert.fail("High response time: " + metric + "ms");
            } else {
                test.log(LogStatus.PASS, "Performance measurement", "Measured response time was " + metric + "ms.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Performance metrics", "Please handle the exception: " + e.getMessage());
            softAssert.fail(e.getMessage());
            fail();
        }


    }

    /**
     * Uses the 'POST' method in order to add a new object, according to type
     * @param type - Object's type (e.g - users)
     * @param object - The object & its properties
     */
    protected void postObject(String type, JSONObject object) {
        try {
            query = "";
            test.log(LogStatus.INFO, "OBJECT", object.toString());
            httpRequest("post", type, query, object);
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Post OBJECT", "Please handle the exception: " + e.getMessage());
            softAssert.fail(e.getMessage());
            fail();
        }
    }

    /**
     * Uses the 'GET' method to retrieve objects by a query (e.g - by ID)
     * @param type - Object's type (e.g - users)
     * @param object - The object & its properties
     */
    protected void getByObjectId(String type, JSONObject object) {
        try {
            test.log(LogStatus.INFO, "OBJECT", object.toString());
            query = object.getString("id");
            httpRequest("get", type, query, object);
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Get OBJECT", "Please handle the exception: " + e.getMessage());
            softAssert.fail(e.getMessage());
            fail();
        } catch (AssertionError ae) {
            test.log(LogStatus.FAIL, "Get OBJECT", "Please handle the assertion error: " + ae.getMessage());
            softAssert.fail(ae.getMessage());
            fail();
        }
    }

    /**
     * Uses the 'PUT' method to edit an object
     * @param type - Object's type (e.g - users)
     * @param targetId - Targeted object to be changed by ID
     * @param object - The object & its properties
     */
    protected void putByObjectId(String type, String targetId, JSONObject object) {
        try {
            test.log(LogStatus.INFO, "OBJECT", object.toString());
            test.log(LogStatus.INFO, "Target", targetId);
            query = targetId;
            httpRequest("put", type, query, object);
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Put OBJECT", "Please handle the exception: " + e.getMessage());
            softAssert.fail(e.getMessage());
            fail();
        } catch (AssertionError ae) {
            test.log(LogStatus.FAIL, "Get OBJECT", "Please handle the assertion error: " + ae.getMessage());
            softAssert.fail(ae.getMessage());
            fail();
        }
    }

    /**
     * Uses the 'DELETE' method to delete an object by its properties (e.g - ID)
     * @param type - Object's type (e.g - users)
     * @param object - The object & its properties
     */
    protected void deleteByObjectId(String type, JSONObject object) {
        try {
            test.log(LogStatus.INFO, "Target ID", object.toString());
            query = object.getString("id");
            httpRequest("delete", type, query, object);
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Delete OBJECT", "Please handle the exception: " + e.getMessage());
            softAssert.fail(e.getMessage());
            fail();
        } catch (AssertionError ae) {
            test.log(LogStatus.FAIL, "Get OBJECT", "Please handle the assertion error: " + ae.getMessage());
            softAssert.fail(ae.getMessage());
            fail();
        }
    }
}
