package main.model.square;

import main.model.Piece;

public class ShieldSquare extends Square {
  public ShieldSquare(int position) {
    super(position);
    invariant();
  }

  protected void invariant() {
    super.invariant();
    assert pieces.size() <= 2 : "ShieldSquare cannot have more than 2 pieces";
  }

  @Override
  protected void handleLandingOnShieldSquare(Piece piece) {
    // Precondition
    assert piece != null : "Piece must not be null";

    if (pieces.size() < 2) { // Can land if less than two pieces
      pieces.add(piece);
    }

    // Invariant check
    invariant();
  }

  @Override
  protected void handleLandingOnRegularSquare(Piece piece) {
    throw new UnsupportedOperationException("Shouldn't be called");
  }

  @Override
  public boolean isBlocked(Piece piece) {
    // Precondition
    assert piece != null : "Piece must not be null";

    // Invariant check
    invariant();

    return pieces.size() == 2;
  }

  @Override
  public boolean isShieldSquare() {
    // Invariant check
    invariant();
    return true;
  }
}
