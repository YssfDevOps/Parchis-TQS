package test.model.square;

import main.model.Board;
import main.model.square.RegularSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegularSquareTest {
  private RegularSquare regularSquare;
  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board();
    regularSquare = new RegularSquare(4, board);
  }

  @Test
  void isEmpty() {
    assertTrue(regularSquare.isEmpty());
  }

  @Test
  void isPlayerStartSquare() {
    assertFalse(regularSquare.isPlayerStartSquare());
  }
}