package main.model.square;

import main.model.Piece;

public class ShieldSquare extends Square {
  public ShieldSquare(int position) {
    super(position);
  }

  @Override
  protected void handleLandingOnShieldSquare(Piece piece) {
    if (pieces.size() < 2) { // Can land if less than two pieces
      pieces.add(piece);
    }
  }

  @Override
  protected void handleLandingOnRegularSquare(Piece piece) {
    throw new UnsupportedOperationException("Shouldn't be called");
  }

  @Override
  public boolean isBlocked(Piece piece) {
    if (pieces.size() == 2) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isShieldSquare() {
    return true;
  }
}
