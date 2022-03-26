package RATests;


import com.google.gson.JsonObject;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FirstTest extends BaseTest {
    // this is the test
    @Test
    public void shouldRun() {
        given()
                .baseUri("https://api.trello.com/1/")
                .queryParam("key", "d8b4a9adf21cc384d7643ae35c69beb0")
                .queryParam("token", "6f94a562d1cfbb684255f657f6b16f98ace82a6945c4041e966c1eb9cea074df")
                .when()
                .get("members/me")
                .then()
                .statusCode(200)
                .body("fullName", equalTo("Kuba Rosiński"));
    }

    @Test
    public void shouldRunTheOtherWay() {
        Response response = given()
                .baseUri("https://api.trello.com/1/")
                .queryParam("key", "d8b4a9adf21cc384d7643ae35c69beb0")
                .queryParam("token", "6f94a562d1cfbb684255f657f6b16f98ace82a6945c4041e966c1eb9cea074df")
                .when()
                .get("members/me");
        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        Assertions.assertEquals("Kuba Rosiński",
                response.getBody().jsonPath().get("fullName"));
//                .then()
//                .statusCode(200)
//                .body("fullName",equalTo("Kuba Rosiński"));
    }

    @Test
    public void shouldRunBetter() {
        Response response = get("members/me");
        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        Assertions.assertEquals("Kuba Rosiński",
                response.getBody().jsonPath().get("fullName"));
    }

    @Test
    public void shouldCreateNewBoard() {
        String name = "Zrodzony z KODU!!!";
        Response response =
                given()
                        .queryParam("name", name)
                        .when()
                        .post("boards");
        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        Assertions.assertEquals(name,
                response.getBody().jsonPath().getString("name"));
    }

    @Test
    public void shouldGetBoard() {
        String id = "623efd141666ad046a74ec83";
        String name = "Zrodzony z KODU!!!";
        Response response = get("boards/" + id);
        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        Assertions.assertEquals(name, response.getBody().jsonPath().get("name"));
    }

    @Test
    public void shouldUpdateNameByQueryParams() {
        String id = "623efd141666ad046a74ec83";
        String newName = "Zmieniony z KODU!!!";
        Response response =
                given().queryParam("name", newName)
                        .put("boards/" + id);
        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        Assertions.assertEquals(newName, response.getBody().jsonPath().getString("name"));
    }

    @Test
    public void shouldUpdateNameByBody() {
        String id = "623efd141666ad046a74ec83";
        String newName = "Test Change from code";
        Response response =
                given().
                        body("{\"name\": \"" + newName + "\"}")
                        .put("boards/" + id);
        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        Assertions.assertEquals(newName, response.getBody().jsonPath().getString("name"));
    }

    @Test
    public void shouldDeleteAllExistingBoards() {
        //check for all boards in memmbers/me/boards
        //delete each found bord
        //verify there are no more boards

        Response response = get("members/me/boards");
        List<String> ids = response.getBody().jsonPath().get("id");
        for (String s : ids) {
            response = delete("boards/" + s);
            Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
        }
        response = get("members/me/boards");
        ids = response.getBody().jsonPath().get("id");
        Assertions.assertEquals(0, ids.size());
    }

    @Test
    public void shouldDoWholeCRUDFlowTest() {
        String id;
        //create
        String name = "Zrodzony z KODU!!!";
        Response response =
                given()
                        .queryParam("name", name)
                        .when()
                        .post("boards");
        Assertions.assertEquals(HttpStatus.SC_OK,response.statusCode());
        id = response.getBody().jsonPath().get("id");
        //get
        response = get("boards/"+id);
        Assertions.assertEquals(HttpStatus.SC_OK,response.statusCode());
        Assertions.assertEquals(name,response.getBody().jsonPath().get("name"));
        //update     (+get)
        String changedName = "Zmieniony z kodu";
        response = given()
                .queryParam("name",changedName)
                .when()
                .put("boards/"+id);
        Assertions.assertEquals(HttpStatus.SC_OK,response.statusCode());
        response = get("boards/"+id);
        Assertions.assertEquals(HttpStatus.SC_OK,response.statusCode());
        Assertions.assertEquals(changedName,response.getBody().jsonPath().get("name"));
        //delete           (+get)
        response = delete("boards/"+id);
        Assertions.assertEquals(HttpStatus.SC_OK,response.statusCode());
        response = get("boards/"+id);
        Assertions.assertEquals(HttpStatus.SC_NOT_FOUND,response.statusCode());
    }

}
