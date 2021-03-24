package JiraAPI.Issues;

import JiraAPI.Authentication.Authentication;
import common.commonMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class createIssue {

    @Test(groups={"getIssueID"})
    public void createIssueTest(ITestContext context) {

        RestAssured.baseURI = "http://localhost:8080/";

        String createIssueResponse = given().log().all().header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("cookie", "JSESSIONID=" + context.getAttribute("accessToken"))
                .body("{\n" +
                        "   \"fields\":{\n" +
                        "      \"project\":{\n" +
                        "         \"key\":\"TES\"\n" +
                        "      },\n" +
                        "      \"summary\":\"Test Create Issue\",\n" +
                        "      \"description\":\"Create issue using RestApi\",\n" +
                        "      \"issuetype\":{\n" +
                        "         \"name\":\"Bug\"\n" +
                        "      }\n" +
                        "   }\n" +
                        "}")
                //.filter(context.getAttribute("accessToken"))
                .when().post("/rest/api/2/issue")
                .then().log().all().assertThat().statusCode(HttpStatus.SC_CREATED)
                .extract().response().asString();
        JsonPath json = commonMethods.rowToJson(createIssueResponse);
        String issueId = json.get("id");
        context.setAttribute("issueId", issueId);
    }

    @Test(dependsOnMethods = "createIssueTest")
    public void addCommentToIssue(ITestContext context){

        RestAssured.baseURI = "http://localhost:8080/";

        given().log().all().pathParam("issueIdOrKey",context.getAttribute("issueId"))
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("cookie", "JSESSIONID=" + context.getAttribute("accessToken"))
                .body("{\n" +
                        "   \"visibility\":{\n" +
                        "      \"type\":\"role\",\n" +
                        "      \"value\":\"Administrators\"\n" +
                        "   },\n" +
                        "   \"body\":\"This is comment for Test\"\n" +
                        "}")
        .when().post("/rest/api/2/issue/{issueIdOrKey}/comment")
                .then().log().all().assertThat().statusCode(HttpStatus.SC_CREATED)
                .extract().response().asString();
    }

}
