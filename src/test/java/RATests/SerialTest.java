package RATests;

import POJOs.Board;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;


public class SerialTest extends BaseTest{

    @Test
    public void firstSerialTest(){
        Response response = given()
                .queryParam("name","New board")
                .when()
                .post("boards");
        Assertions.assertEquals(HttpStatus.SC_OK,response.statusCode());
        Board board = response.as(Board.class);
        System.out.println(board);
        Assertions.assertEquals("New board",board.getName());

        board.setName("dupadupa8");
        response = given()
                .body(board)
                .when()
                .put("boards/"+board.getId());
        Assertions.assertEquals(HttpStatus.SC_OK,response.statusCode());
        board = response.as(Board.class);
        Assertions.assertEquals("dupadupa8",board.getName());
        response = when().delete("boards/"+board.getId());
        Assertions.assertEquals(HttpStatus.SC_OK,response.statusCode());
        board = response.as(Board.class);
        Assertions.assertNull(board.getName());
    }

    @Test
    public void shouldDeleteAllBoards(){
        Board[] boards = get("members/me/boards")
                .as(Board[].class);
        for (Board b:boards ) {
                Board deleted = delete("boards/"+b.getId())
                        .as(Board.class);
                get("boards/"+b.getId())
                        .then()
                        .statusCode(404);
        }
    }

}
