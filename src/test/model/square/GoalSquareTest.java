package test.model.square;

import main.model.Board;
import main.model.Piece;
import main.model.square.GoalSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoalSquareTest {
  private GoalSquare goalSquare;
  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board();
    goalSquare = new GoalSquare(6, board);
  }

  @Test
  void isGoalSquare() {
    assertTrue(goalSquare.isGoalSquare());
  }

  @Test
  void landHereSendHome() {
    assertNull(goalSquare.landHereSendHome());
  }

  @Test
  void enter() {
    Piece piece = new Piece();
    goalSquare.enter(piece);
    assertFalse(goalSquare.isEmpty());
  }
}