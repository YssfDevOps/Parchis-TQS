package main.model.square;

import main.model.Board;
import main.model.Piece;

public class GoalSquare extends Square {
  public GoalSquare(int position, Board board) {
    super(position, board);
  }

  @Override
  public boolean isGoalSquare() {
    return true;
  }

  @Override
  public Square landHereSendHome() {
    // To be implemented later
    return null;
  }

  @Override
  public void enter(Piece piece) {
    // To be implemented later
  }
}
