package test.model.square;

import main.model.Board;
import main.model.square.FinalPathSquare;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class FinalPathSquareTest {
  private FinalPathSquare finalPathSquare;
  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board();
    finalPathSquare = new FinalPathSquare(3, board);
  }
}