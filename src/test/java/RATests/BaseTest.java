package RATests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public class BaseTest {
    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "https://api.trello.com/1/";
        requestSpecification =
                given()
                .queryParam("token", "6f94a562d1cfbb684255f657f6b16f98ace82a6945c4041e966c1eb9cea074df")
                .queryParam("key", "d8b4a9adf21cc384d7643ae35c69beb0")
                .contentType(ContentType.JSON);      // czesto zapominane przy przejsciu z POSTMANA
    }
}
