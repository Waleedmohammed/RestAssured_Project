package DynamicJson;

import common.commonMethods;
import files.payload;
import helper.utils;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class DynamicPayload {


    @Test(enabled = false)
    public void addBook() {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().log().all().header("Content-Type", "application/json")
                .body(payload.AddBook("abcde", "22222eeewwddfff"))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response().asString();

        JsonPath js = commonMethods.rowToJson(response);
        String bookId = js.get("ID");

        System.out.println("Added Book ID = " + bookId);
    }

    @Test(dataProvider = "AddBookData")
    public void addBookwithDataProvider(String isbn, String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().log().all().header("Content-Type", "application/json")
                .body(payload.AddBook(isbn, aisle))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response().asString();

        JsonPath js = commonMethods.rowToJson(response);
        String bookId = js.get("ID");

        System.out.println("Added Book ID = " + bookId);
    }


    @DataProvider(name = "AddBookData")
    public Object[][] AddBookData() {

        return new Object[][]{
                {"Test", utils.getRandomNumeric("5")},
                {"Test2", utils.getRandomNumeric("5")},
                {"Test3", utils.getRandomNumeric("5")}

        };
    }

}
