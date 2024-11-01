package test.model.square;

import main.model.Board;
import main.model.Piece;
import main.model.square.RegularSquare;
import main.model.square.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {
  private Board board;
  private Square square;
  private Piece piece;

  @BeforeEach
  void setUp() {
    board = new Board();
    piece = new Piece();
    square = new RegularSquare(1, board);
  }

  @Test
  void enter() {
    square.enter(piece);
    assertFalse(square.isEmpty());
  }

  @Test
  void leave() {
    square.enter(piece);
    square.leave(piece);
    assertTrue(square.isEmpty());
  }

  @Test
  void isHomeSquare() {
    assertFalse(square.isHomeSquare());
  }

  @Test
  void isGoalSquare() {
    assertFalse(square.isGoalSquare());
  }

  @Test
  void isShieldSquare() {
    assertFalse(square.isShieldSquare());
  }

  @Test
  void isLastGlobalSquare() {
    assertFalse(square.isLastGlobalSquare());
  }

  @Test
  void moveAndLand() {
    assertNull(square.moveAndLand(piece, 3));
  }

  @Test
  void landHereSendHome() {
    assertNull(square.landHereSendHome());
  }

  @Test
  void isEmpty() {
    assertTrue(square.isEmpty());
    square.enter(piece);
    assertFalse(square.isEmpty());
  }

  @Test
  void sendPieceHome() {
    // To be implemented when logic is added
  }

  @Test
  void isPlayerStartSquare() {
    assertFalse(square.isPlayerStartSquare());
  }
}