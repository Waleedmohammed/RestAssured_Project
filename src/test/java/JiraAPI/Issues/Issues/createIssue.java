package JiraAPI.Issues.Issues;

import JiraAPI.Issues.Authentication.Authentication;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class createIssue {

    @Test
    public void createIssue() {

        RestAssured.baseURI = "http://localhost:8080/";

        given().log().all().header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("cookie", "JSESSIONID=" + Authentication.getSessionId())
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
                .when().post("/rest/api/2/issue")
                .then().log().all().assertThat().statusCode(HttpStatus.SC_CREATED)
                .extract().response().asString();

    }

}
