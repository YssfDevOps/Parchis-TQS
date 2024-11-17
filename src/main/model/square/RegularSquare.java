package main.model.square;

import main.model.Piece;

public class RegularSquare extends Square {
  public RegularSquare(int position) {
    super(position);
    invariant();
  }

  protected void invariant() {
    super.invariant();
    assert pieces.size() != 2 || pieces.get(0).getColor().equals(pieces.get(1).getColor()) :
        "Blockage must consist of pieces of the same color";
  }

  @Override
  protected void handleLandingOnShieldSquare(Piece piece) {
    // Precondition
    assert piece != null : "Piece must not be null";
    throw new UnsupportedOperationException("Shouldn't be called");
  }

  @Override
  protected void handleLandingOnRegularSquare(Piece piece) {
    // Precondition
    assert piece != null : "Piece must not be null";

    if (pieces.isEmpty()) {
      // Square is empty
      pieces.add(piece);
    } else if (pieces.size() == 1) {
      Piece occupant = pieces.get(0);
      if (!occupant.getColor().equals(piece.getColor())) {
        // Capturing occurs
        occupant.sendHome();
        pieces.clear();
        pieces.add(piece);
      } else {
        // Form a blockage
        piece.setSquare(this);
        pieces.add(piece);
      }
    }

    // Invariant check
    invariant();
  }

  @Override
  public boolean isBlocked(Piece piece) {
    // Precondition
    assert piece != null : "Piece must not be null";

    // Invariant check
    invariant();

    if (pieces.size() == 2 && pieces.get(0).getColor().equals(pieces.get(1).getColor())) {
      // Blockage by two pieces of the same color
      return !pieces.get(0).getColor().equals(piece.getColor());
    }

    return false;
  }

  @Override
  public boolean isShieldSquare() {
    // Invariant check
    invariant();
    return false;
  }
}
