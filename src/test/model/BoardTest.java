package test.model;

import main.model.Board;
import main.model.Player;
import main.model.Piece;
import main.model.square.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    private Player yellowPlayer;
    private Player redPlayer;
    private Player bluePlayer;
    private Player greenPlayer;

    @BeforeEach
    void setUp() {
        board = new Board();
        yellowPlayer = new Player("Yellow Player", "Yellow");
        redPlayer = new Player("Red Player", "Red");
        bluePlayer = new Player("Blue Player", "Blue");
        greenPlayer = new Player("Green Player", "Green");
    }

    @Test
    void findSquare() {
        Square square = board.findSquare(5);
        assertNotNull(square);
        assertEquals(5, square.getPosition());
        assertTrue(square instanceof ShieldSquare);
    }

    @Test
    void getPlayerStartSquare() {
        ShieldSquare startSquare = board.getPlayerStartSquare(yellowPlayer);
        assertNotNull(startSquare);
        assertEquals(5, startSquare.getPosition());
        assertTrue(startSquare instanceof ShieldSquare);

        startSquare = board.getPlayerStartSquare(redPlayer);
        assertNotNull(startSquare);
        assertEquals(39, startSquare.getPosition());
        assertTrue(startSquare instanceof ShieldSquare);

        startSquare = board.getPlayerStartSquare(bluePlayer);
        assertNotNull(startSquare);
        assertEquals(22, startSquare.getPosition());
        assertTrue(startSquare instanceof ShieldSquare);

        startSquare = board.getPlayerStartSquare(greenPlayer);
        assertNotNull(startSquare);
        assertEquals(56, startSquare.getPosition());
        assertTrue(startSquare instanceof ShieldSquare);
    }

    @Test
    void getSquare() {
        Square square = board.getSquare(34);
        assertNotNull(square);
        assertEquals(34, square.getPosition());
        assertTrue(square instanceof ShieldSquare);

        square = board.getSquare(1);
        assertNotNull(square);
        assertEquals(1, square.getPosition());
        assertTrue(square instanceof RegularSquare);
    }

    @Test
    void testInvalidSquarePosition() {
        assertThrows(IllegalArgumentException.class, () -> board.findSquare(0));
        assertThrows(IllegalArgumentException.class, () -> board.findSquare(69));
    }

    @Test
    void testSetUpPlayerFinalPaths() {
        List<Square> finalPath = board.getPlayerFinalPath(yellowPlayer);
        assertNotNull(finalPath);
        assertEquals(8, finalPath.size());
        assertTrue(finalPath.get(0) instanceof FinalPathSquare);

        finalPath = board.getPlayerFinalPath(redPlayer);
        assertNotNull(finalPath);
        assertEquals(8, finalPath.size());
        assertTrue(finalPath.get(0) instanceof FinalPathSquare);

        finalPath = board.getPlayerFinalPath(bluePlayer);
        assertNotNull(finalPath);
        assertEquals(8, finalPath.size());
        assertTrue(finalPath.get(0) instanceof FinalPathSquare);

        finalPath = board.getPlayerFinalPath(greenPlayer);
        assertNotNull(finalPath);
        assertEquals(8, finalPath.size());
        assertTrue(finalPath.get(0) instanceof FinalPathSquare);
    }

    @Test
    void testShieldSquares() {
        int[] shieldPositions = {5, 12, 17, 22, 29, 34, 39, 46, 51, 56, 63, 68};
        for (int position : shieldPositions) {
            Square square = board.findSquare(position);
            assertTrue(square instanceof ShieldSquare, "Position " + position + " should be a ShieldSquare");
        }
    }

    @Test
    void testPlayerStartPositions() {
        assertEquals(5, board.getPlayerStartPosition(yellowPlayer));
        assertEquals(39, board.getPlayerStartPosition(redPlayer));
        assertEquals(22, board.getPlayerStartPosition(bluePlayer));
        assertEquals(56, board.getPlayerStartPosition(greenPlayer));
    }

    @Test
    void testMoveAcrossBoard() {
        Square startSquare = board.getPlayerStartSquare(yellowPlayer);
        assertNotNull(startSquare);
        List<Piece> pieces = yellowPlayer.getPieces();
        Piece piece = pieces.getFirst();

        piece.setPlaying(true);

        piece.moveForward(3);
        assertEquals(8, piece.getPosition());

        piece.moveForward(60);
        assertEquals(4, piece.getPosition());
    }

    @Test
    void testEnterFinalPath() {
        List<Piece> pieces = yellowPlayer.getPieces();
        Piece piece = pieces.getFirst();

        piece.enterFinalPath();
        assertEquals(68, piece.getPosition());
    }
}