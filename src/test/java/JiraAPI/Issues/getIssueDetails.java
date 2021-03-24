package JiraAPI.Issues;

import com.sun.deploy.uitoolkit.impl.awt.AWTDragHelper;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.testng.ITestContext;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;


public class getIssueDetails {

    @Test(dependsOnGroups={"getIssueID"})
    public void getIssueDetailsTest(ITestContext context){

        RestAssured.baseURI="http://localhost:8080/";

        given().log().all().header("Accept","application/json")
                .header("cookie", "JSESSIONID=" + context.getAttribute("accessToken"))
                .pathParam("issueIdOrKey",context.getAttribute("issueId"))
                .queryParam("fields","description")
        .when().get("/rest/api/2/issue/{issueIdOrKey}")
        .then().log().all().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response().asString();


    }
}
