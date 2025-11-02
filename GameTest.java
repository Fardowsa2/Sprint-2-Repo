import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void alterTurn_switchesPlayerTurnFlag() {
        // NOTE: This will show your board size/mode dialogs — click through them.
        Game game = new Game();

        // Blue starts
        assertTrue(game.isPlayerTurn(), "Expected Blue to start");

        // After one call → Red
        game.alterTurn();
        assertFalse(game.isPlayerTurn(), "Expected Red after one alterTurn()");

        // After second call → back to Blue
        game.alterTurn();
        assertTrue(game.isPlayerTurn(), "Expected Blue after two alterTurn()");
    }
}
