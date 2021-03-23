package basics;

import common.commonMethods;
import files.payload;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class Basics {

    public static void main(String[] args) throws IOException {

        // Validate if Add Place API is working as expected

        // given - all input details
        // when  - submit the API
        // then  - validate the response

        // Passing Json from file
        // Will convert Json to Byte then from Byte to String

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        // Test Case 1: Add Place

        //Import manually as it is static package
        String responseBody = given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                //.body(payload.AddPlace())
                //send payload from external file
                .body(new String(Files.readAllBytes(Paths.get("src/test/resources/AddPlace.json"))))
                .when().post("/maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(HttpStatus.SC_OK)
                //.then().assertThat().statusCode(HttpStatus.SC_OK)

                // Import manually hamcrest for equal to method import
                .body("scope", equalTo("APP"))
                .header("Server", equalTo("Apache/2.4.18 (Ubuntu)"))

                // Extract response body as string to get added place ID
                .extract().response().asString();

        //System.out.println(responseBody);

        // Test Case 2: Update the address of added place from previous test case

        // Parse response body using JsonPath class
        //JsonPath js = new JsonPath(responseBody);
        //String placeID = js.get("place_id");

        String placeID = commonMethods.rowToJson(responseBody).get("place_id");

        System.out.println("Add Place ID = " + placeID);

        String newAddress = "Carl-Wery-Str. 64 , 81739 Munchen";

        given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(payload.UpdatePlace(placeID, newAddress))

                .when().put("/maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(HttpStatus.SC_OK)
                .body("msg", equalTo("Address successfully updated"));


        // get the place after update to verify the new address is updated

        String updatedPlace = given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeID)

                .when().get("/maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(HttpStatus.SC_OK).extract().response().asString();

        //JsonPath js2 = new JsonPath(updatedPlace);
        //String updatedAddress = js2.get("address");
        String updatedAddress = commonMethods.rowToJson(updatedPlace).get("address");


        // use TestNg Assertion to verify two values are equals
        Assert.assertEquals(updatedAddress, newAddress);


    }
}
