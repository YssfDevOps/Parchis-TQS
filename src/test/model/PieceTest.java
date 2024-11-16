package test.model;

import main.model.Board;
import main.model.Color;
import main.model.Piece;
import main.model.square.RegularSquare;
import main.model.square.Square;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

  @Test
  void getId() {
    // 1. Check if ID increments
    Piece piece1 = new Piece(Color.RED);
    int id1 = piece1.getId();
    assertTrue(id1 >= 1);

    // 2. Check IDs increment
    Piece piece2 = new Piece(Color.BLUE);
    int id2 = piece2.getId();
    assertEquals(id1 + 1, id2);

    // 3. Check IDs increment
    Piece piece3 = new Piece(Color.GREEN);
    int id3 = piece3.getId();
    assertEquals(id2 + 1, id3);
  }

  @Test
  void isAtHome() {
    // New piece should be at home (start of the game)
    Piece piece = new Piece(Color.RED);
    assertTrue(piece.isAtHome());

    // 2. Enter piece at game, piece should not be at home
    Board board = new Board();
    Square startSquare = new RegularSquare(0);
    board.setPlayerStartSquare(Color.RED, startSquare);

    piece.enterGame(board);
    assertFalse(piece.isAtHome());

    // 3. Send piece home, piece should be at home again
    piece.sendHome();
    assertTrue(piece.isAtHome());
  }

  @Test
  void sendHome() {
    // 1. Send home when piece is on the board
    Piece piece = new Piece(Color.RED);
    Square square = new RegularSquare(10);
    square.landHere(piece);
    piece.setSquare(square);
    assertFalse(piece.isAtHome());
    assertEquals(square, piece.getSquare());

    piece.sendHome();
    assertTrue(piece.isAtHome());
    assertNull(piece.getSquare());
    assertFalse(square.isOccupied());
  }

  @Test
  void enterGame() {
    // 1. Enter game when start square is NOT blocked (already 2 in the square)
    Piece piece = new Piece(Color.RED);
    Board board = new Board();
    Square startSquare = new RegularSquare(0);
    board.setPlayerStartSquare(Color.RED, startSquare);
    assertFalse(startSquare.isBlocked(piece));
    piece.enterGame(board);
    assertFalse(piece.isAtHome());
    assertEquals(startSquare, piece.getSquare());

    // 2. Enter game when start square is blocked (already 2 in the square)
    Piece blockingPiece1 = new Piece(Color.RED);
    Piece blockingPiece2 = new Piece(Color.RED);
    startSquare.landHere(blockingPiece1);
    startSquare.landHere(blockingPiece2); // Forms a blockage
    Piece piece2 = new Piece(Color.RED);
    piece2.enterGame(board);
    assertTrue(piece2.isAtHome());
    assertNull(piece2.getSquare());
  }

  @Test
  void getSquare() {
    // 1. New piece should have null square
    Piece piece = new Piece(Color.RED);
    assertNull(piece.getSquare());

    // 2. After entering the game, piece should have start square
    Board board = new Board();
    Square startSquare = new RegularSquare(0);
    board.setPlayerStartSquare(Color.RED, startSquare);
    piece.enterGame(board);
    assertEquals(startSquare, piece.getSquare());

    // 3. After moving, piece should have new square
    Square nextSquare = new RegularSquare(1);
    piece.setSquare(nextSquare);
    assertEquals(nextSquare, piece.getSquare());
  }

  @Test
  void setSquare() {
    // 1. Set square to a square
    Piece piece = new Piece(Color.RED);
    Square square = new RegularSquare(10);
    piece.setSquare(square);
    assertEquals(square, piece.getSquare());

    // 2. Set square to null
    piece.setSquare(null);
    assertNull(piece.getSquare());
  }

  @Test
  void hasFinished() {
    // 1. New piece should not have finished
    Piece piece = new Piece(Color.RED);
    assertFalse(piece.hasFinished());

    // 2. After setting hasFinished to true
    piece.setHasFinished(true);
    assertTrue(piece.hasFinished());
  }

  @Test
  void setHasFinished() {
    // 1. Set hasFinished to true
    Piece piece = new Piece(Color.RED);
    piece.setHasFinished(true);
    assertTrue(piece.hasFinished());

    // 2. Set hasFinished back to false
    piece.setHasFinished(false);
    assertFalse(piece.hasFinished());
  }

  @Test
  void getColor() {
    Piece piece = new Piece(Color.RED);
    assertEquals(Color.RED, piece.getColor());

    Piece piece2 = new Piece(Color.BLUE);
    assertEquals(Color.BLUE, piece2.getColor());

    Piece piece3 = new Piece(Color.YELLOW);
    assertEquals(Color.YELLOW, piece3.getColor());

    Piece piece4 = new Piece(Color.GREEN);
    assertEquals(Color.GREEN, piece4.getColor());
  }
}