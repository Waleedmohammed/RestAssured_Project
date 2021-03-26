package JiraAPI.Issues;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;


public class addAttachment {

    @Test(dependsOnGroups = {"getIssueID"})
    public void addAttachmentTest(ITestContext context) {

        RestAssured.baseURI = "http://localhost:8080/";

        given().log().all().header("cookie", "JSESSIONID=" + context.getAttribute("accessToken"))
                .header("X-Atlassian-Token", "no-check")
                .pathParam("issueIdOrKey", context.getAttribute("issueId"))
                .multiPart("file", new File("C:\\My files\\Projects\\RestAssured_TestAutomation\\src\\test\\resources\\jiraAttachment.txt"))
                .when().post("/rest/api/2/issue/{issueIdOrKey}/attachments")
                .then().log().all().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response().asString();

    }

}
