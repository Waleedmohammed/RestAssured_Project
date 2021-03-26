package JiraAPI.Issues;

import common.commonMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;


public class getIssueDetails {

    @Test(dependsOnGroups = {"getIssueID", "getCommentID"})
    public void getIssueDetailsTest(ITestContext context) {

        RestAssured.baseURI = "http://localhost:8080/";

        String issueDetailsResponse = given().log().all().relaxedHTTPSValidation().header("Accept", "application/json")
                .header("cookie", "JSESSIONID=" + context.getAttribute("accessToken"))
                .pathParam("issueIdOrKey", context.getAttribute("issueId"))
                .queryParam("fields", "comment")
                .when().get("/rest/api/2/issue/{issueIdOrKey}")
                .then().log().all().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response().asString();

        JsonPath js = commonMethods.rowToJson(issueDetailsResponse);
        int commentsCount = js.getInt("fields.comment.comments.size()");

        for (int i = 0; i < commentsCount; i++) {
            if (js.get("fields.comment.comments[" + i + "].id").equals(context.getAttribute("commentID"))) {
                Assert.assertEquals(js.get("fields.comment.comments[" + i + "].body"), "This is comment for Test2");
            }
        }
    }
}
