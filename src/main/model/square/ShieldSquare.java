package main.model.square;

import main.model.Board;
import main.model.Piece;

public class ShieldSquare extends Square {
  public ShieldSquare(int position, Board board) {
    super(position, board);
  }

  @Override
  public boolean isShieldSquare() {
    return true;
  }

  @Override
  public boolean isPlayerStartSquare() {
    // To be implemented later
    return false;
  }

  @Override
  public void enter(Piece piece) {
    // To be implemented later
  }

  @Override
  public void leave(Piece piece) {
    // To be implemented later
  }

  @Override
  public Square landHereSendHome() {
    // To be implemented later
    return null;
  }
}
