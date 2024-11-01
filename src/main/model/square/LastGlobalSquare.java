package main.model.square;

import main.model.Board;

public class LastGlobalSquare extends Square {
  public LastGlobalSquare(int position, Board board) {
    super(position, board);
  }

  @Override
  public boolean isLastGlobalSquare() {
    return true;
  }
}
