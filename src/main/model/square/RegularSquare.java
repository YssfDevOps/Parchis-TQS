package main.model.square;

import main.model.Board;

public class RegularSquare extends Square {
  public RegularSquare(int position, Board board) {
    super(position, board);
  }

  @Override
  public boolean isEmpty() {
    return piece == null;
  }

  @Override
  public boolean isPlayerStartSquare() {
    // To be implemented later
    return false;
  }
}
