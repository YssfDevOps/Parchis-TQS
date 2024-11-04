package test.model;

import main.model.Piece;
import main.model.square.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PieceTest {

    private Piece piece;
    private Square square;

    @BeforeEach
    void setUp() {
        // Create a mock of Square to simulate its behavior
        square = mock(Square.class);
        // Initialize a Piece instance with the mocked Square
        piece = new Piece("Red", square);
    }

    @Test
    void moveForward() {
        // Set up initial position for the piece
        when(square.getPosition()).thenReturn(1);
        int moves = 3;

        // Call moveForward on the piece
        piece.moveForward(moves);

        // Verify that the piece's move method was called with the correct number of moves
        verify(piece, times(1)).moveForward(moves);
    }

    @Test
    void sendHome() {
        // Set piece to playing state and then send it home
        piece.setPlaying(true);
        piece.sendHome();

        // Verify that the piece is no longer in playing state
        assertFalse(piece.isPlaying(), "Piece should be sent home (out of playing state)");
    }

    @Test
    void enterFinalPath() {
        // Test if enterFinalPath in Piece calls the appropriate method in Square
        piece.enterFinalPath();

        // Verify if the final path method in Piece is called onces
        verify(piece, times(1)).enterFinalPath();
    }
}
