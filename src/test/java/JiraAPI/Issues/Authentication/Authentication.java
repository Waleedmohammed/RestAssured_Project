package JiraAPI.Issues.Authentication;

import common.commonMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;


public class Authentication {

    @Test
    public static  String getSessionId() {

        RestAssured.baseURI = "http://localhost:8080/";

        String response = given().log().all().header("content-type", "application/json")
                .body("{\n" +
                        "   \"username\":\"waleediti32\",\n" +
                        "   \"password\":\"Passwordmmhh88\"\n" +
                        "}")
                .when().post("/rest/auth/1/session")
                .then().log().all().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response().asString();

        JsonPath Json = commonMethods.rowToJson(response);

        String sessionID = Json.get("session.value");

        System.out.println("Generated Session Id = " + sessionID);

        return sessionID;

    }

}
