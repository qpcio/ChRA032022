package RATests;

import Endpoints.BoardsEndpoint;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class POTests {
    @Test
    public void sampleTest(){
        String name = "dupazkrola";
        BoardsEndpoint boardsEndpoint = new BoardsEndpoint();
        String id = boardsEndpoint.createBoard(name);
        Assertions.assertEquals(HttpStatus.SC_OK,boardsEndpoint.getLastStatusCode());
        String receivedName = boardsEndpoint.getBoardName(id);
        Assertions.assertEquals(HttpStatus.SC_OK,boardsEndpoint.getLastStatusCode());
        Assertions.assertEquals(name,receivedName);
        String newName = "Zmienione";
        boardsEndpoint.updateName(id,newName);
        Assertions.assertEquals(HttpStatus.SC_OK,boardsEndpoint.getLastStatusCode());
        Assertions.assertEquals(newName,boardsEndpoint.getBoardName(id));
        boardsEndpoint.deleteBoard(id);
        Assertions.assertEquals(HttpStatus.SC_OK,boardsEndpoint.getLastStatusCode());
        boardsEndpoint.getBoard(id);
        Assertions.assertEquals(HttpStatus.SC_NOT_FOUND,boardsEndpoint.getLastStatusCode());

    }
}
