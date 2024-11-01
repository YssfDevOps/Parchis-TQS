package test.model.square;

import main.model.Board;
import main.model.Piece;
import main.model.square.HomeSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HomeSquareTest {
  private HomeSquare homeSquare;
  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board();
    homeSquare = new HomeSquare(5, board);
  }

  @Test
  void isHomeSquare() {
    assertTrue(homeSquare.isHomeSquare());
  }

  @Test
  void findShieldSquare() {
    assertNull(homeSquare.findShieldSquare());
  }

  @Test
  void moveAndLand() {
    Piece piece = new Piece();
    assertNull(homeSquare.moveAndLand(piece, 2));
  }
}