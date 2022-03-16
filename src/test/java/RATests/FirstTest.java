package RATests;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class FirstTest {
    @Test
    public void shouldRun(){
        given()
                .baseUri("https://api.trello.com/1/")
                .when()
                .get("members/me")
                .then()
                .statusCode(404);
    }
}
