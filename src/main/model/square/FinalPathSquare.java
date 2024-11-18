package main.model.square;

import main.model.Color;
import main.model.Piece;

public class FinalPathSquare extends Square {
  protected Color color;
  protected int index; // Position in the final path (0 to 7)

  public FinalPathSquare(int index, Color color) {
    super(index);

    // Precondition
    assert (index >= 0) : "Color must be null or negative index";
    assert (index < 8) : "Color must be null or negative index";

    assert (color != null) : "Color must be null or negative index";

    this.color = color;
    this.index = index;

    // Invariant check
    invariant();
  }

  public void invariant() {
    super.invariant();
    for (Piece p : pieces) {
      assert p.getColor() == color : "Piece color must match FinalPathSquare color";
    }
  }

  public Color getColor() {
    // Invariant check
    invariant();
    return color;
  }

  public int getIndex() {
    assert (index >= 0) : "Color must be null or negative index";
    // Invariant check
    invariant();
    return index;
  }

  @Override
  protected void handleLandingOnShieldSquare(Piece piece) {
    throw new UnsupportedOperationException("Shouldn't be called");
  }

  @Override
  protected void handleLandingOnRegularSquare(Piece piece) {
    throw new UnsupportedOperationException("Shouldn't be called");
  }

  @Override
  public boolean isBlocked(Piece piece) { // There is no blocks in the final path, so return false.
    // Precondition
    assert piece != null : "Piece must not be null";
    return false;
  }

  @Override
  public boolean isShieldSquare() {
    // Invariant check
    invariant();
    return false;
  }
}
