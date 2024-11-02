package test.model;

import main.model.Player;
import main.model.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerTest {

    private Player player;
    private Piece piece1;
    private Piece piece2;
    private List<Piece> pieces;

    @BeforeEach
    void setUp() {
        // Mock individual pieces for the player
        piece1 = mock(Piece.class);
        piece2 = mock(Piece.class);

        // Create a list of mocked pieces and initialize the player with them
        pieces = new ArrayList<>();
        pieces.add(piece1);
        pieces.add(piece2);

        // Initialize the player with name and color (assuming these are necessary arguments)
        player = new Player("Player1", "Red");
        // Set up player pieces directly, as there may be no setter (depends on actual implementation)
        player.getPieces().addAll(pieces);
    }

    @Test
    void movePiece() {
        // Define how many moves to make
        int moves = 3;

        // Call movePiece on player with a specific piece and move count
        player.movePiece(piece1, moves);

        // Verify that the piece's move method was called with the correct number of moves
        verify(piece1, times(1)).moveForward(moves);
    }

    @Test
    void haveAnyPiecePlaying() {
        // Test when no piece is in playing state
        when(piece1.isPlaying()).thenReturn(false);
        when(piece2.isPlaying()).thenReturn(false);
        assertFalse(player.haveAnyPiecePlaying(), "Player should not have any piece in play");

        // Test when at least one piece is in playing state
        when(piece1.isPlaying()).thenReturn(true);
        assertTrue(player.haveAnyPiecePlaying(), "Player should have at least one piece in play");
    }

    @Test
    void isWinner() {
        // Assume player wins if a condition sets winner to true
        player.setWinner(true);
        assertTrue(player.isWinner(), "Player should be a winner");

        // Reset winner status and check again
        player.setWinner(false);
        assertFalse(player.isWinner(), "Player should not be a winner");
    }

    @Test
    void isPlaying() {
        // Set player to playing state and check
        player.setPlaying(true);
        assertTrue(player.isPlaying(), "Player should be in playing state");

        // Set player out of playing state and verify
        player.setPlaying(false);
        assertFalse(player.isPlaying(), "Player should not be in playing state");
    }
}
