package test.model.square;

import main.model.Board;
import main.model.square.LastGlobalSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LastGlobalSquareTest {
  private LastGlobalSquare lastGlobalSquare;
  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board();
    lastGlobalSquare = new LastGlobalSquare(7, board);
  }

  @Test
  void isLastGlobalSquare() {
    assertTrue(lastGlobalSquare.isLastGlobalSquare());
  }
}