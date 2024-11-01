package test.model.square;

import main.model.Board;
import main.model.Piece;
import main.model.square.ShieldSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShieldSquareTest {

  private ShieldSquare shieldSquare;
  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board();
    shieldSquare = new ShieldSquare(2, board);
  }

  @Test
  void isShieldSquare() {
    assertTrue(shieldSquare.isShieldSquare());
  }

  @Test
  void isPlayerStartSquare() {
    assertFalse(shieldSquare.isPlayerStartSquare());
  }

  @Test
  void enter() {
    Piece piece = new Piece();
    shieldSquare.enter(piece);
    assertFalse(shieldSquare.isEmpty());
  }

  @Test
  void leave() {
    Piece piece = new Piece();
    shieldSquare.enter(piece);
    shieldSquare.leave(piece);
    assertTrue(shieldSquare.isEmpty());
  }

  @Test
  void landHereSendHome() {
    assertNull(shieldSquare.landHereSendHome());
  }
}