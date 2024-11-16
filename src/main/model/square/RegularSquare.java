package main.model.square;

import main.model.Piece;

public class RegularSquare extends Square {
  public RegularSquare(int position) {
    super(position);
  }

  @Override
  protected void handleLandingOnShieldSquare(Piece piece) {
    throw new UnsupportedOperationException("Shouldn't be called");
  }

  @Override
  protected void handleLandingOnRegularSquare(Piece piece) {
    if (pieces.size() == 0) {
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
        pieces.add(piece);
      }
    }
  }

  @Override
  public boolean isBlocked(Piece piece) {
    if (pieces.size() == 2 && pieces.get(0).getColor().equals(pieces.get(1).getColor())) {
      // Blockage by two pieces of the same color
      return !pieces.get(0).getColor().equals(piece.getColor());
    }
    return false;
  }

  @Override
  public boolean isShieldSquare() {
    return false;
  }
}
