package JiraAPI.Authentication;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import org.apache.http.HttpStatus;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;

import static io.restassured.RestAssured.*;


public class Authentication {

    @BeforeSuite
    public void getSessionId(ITestContext context) {

        RestAssured.baseURI = "http://localhost:8080/";

        SessionFilter session = new SessionFilter();

        //String response =
                given().log().all().header("content-type", "application/json")
                .body("{\n" +
                        "   \"username\":\"waleediti32\",\n" +
                        "   \"password\":\"Passwordmmhh88\"\n" +
                        "}")
                .filter(session)
                .when().post("/rest/auth/1/session")
                .then().log().all().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response().asString();

        //JsonPath Json = commonMethods.rowToJson(response);
        //String sessionID = Json.get("session.value");

        context.setAttribute("accessToken", session.getSessionId());

        System.out.println("Generated Session Id = " + context.getAttribute("accessToken"));

    }

}
