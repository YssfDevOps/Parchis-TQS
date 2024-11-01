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
    if (!isEmpty()) {
      sendPieceHome(piece);
    }
    return this;
  }

  @Override
  public void enter(Piece piece) {
    if (piece == null) {
      throw new IllegalArgumentException("Piece cannot be null");
    }
    this.piece = piece;
  }
}