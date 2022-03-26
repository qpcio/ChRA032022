package Endpoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.*;

public class BoardsEndpoint {
    private String boardEndpointAddress = "boards";
    private Response response;

    public String createBoard(String name) {
        response = given()
                .queryParam("name", name)
                .when()
                .post(boardEndpointAddress);
        return response.getBody().jsonPath().get("id");
    }

    public boolean isLastStatusCodeOK() {
        return response.statusCode() == HttpStatus.SC_OK;
    }

    public int getLastStatusCode(){
        return response.statusCode();
    }

    public String getBoardName(String id){
        response = get(boardEndpointAddress+"/"+id);
        return response.body().jsonPath().get("name");
    }

    public void getBoard(String id){
        response = get(boardEndpointAddress+"/"+id);
    }

    public void updateName(String id, String newName){
        response = given()
                .queryParam("name",newName)
                .when()
                .put(boardEndpointAddress+"/"+id);
    }

    public void deleteBoard(String id){
        response = delete(boardEndpointAddress+"/"+id);
    }

    public BoardsEndpoint() {
        RestAssured.baseURI = "https://api.trello.com/1/";
        requestSpecification =
                given()
                        .queryParam("token", "6f94a562d1cfbb684255f657f6b16f98ace82a6945c4041e966c1eb9cea074df")
                        .queryParam("key", "d8b4a9adf21cc384d7643ae35c69beb0")
                        .contentType(ContentType.JSON);
    }
}
