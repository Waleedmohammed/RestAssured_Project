package JiraAPI.Issues;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.testng.ITestContext;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;

public class deleteIssue {

    @Test(dependsOnGroups = {"getIssueID"})
    public void deleteIssueTest(ITestContext context) {

        RestAssured.baseURI = "http://localhost:8080/";
        given().log().all().pathParam("issueIdOrKey", context.getAttribute("issueId"))
                .header("cookie", "JSESSIONID=" + context.getAttribute("accessToken"))
                .when().delete("/rest/api/2/issue/{issueIdOrKey}")
                .then().log().all().assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().response().asString();


    }
}
