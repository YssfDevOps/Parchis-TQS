package main.model.square;

import main.model.Board;
import main.model.Piece;

public class HomeSquare extends Square {
  public HomeSquare(int position, Board board) {
    super(position, board);
  }

  @Override
  public boolean isHomeSquare() {
    return true;
  }

  public ShieldSquare findShieldSquare() {
    // To be implemented later
    return null;
  }

  @Override
  public Square moveAndLand(Piece piece, int moves) {
    // To be implemented later
    return null;
  }
}
