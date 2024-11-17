package main.model.square;

import main.model.Color;
import main.model.Piece;

public class FinalPathSquare extends Square {
  private Color color;
  private int index; // Position in the final path (0 to 7)

  public FinalPathSquare(int index, Color color) {
    super(index);

    // Precondition
    assert (color != null || index >= 0) : "Color must be null or negative index";

    this.color = color;
    this.index = index;

    // Postcondition
    assert this.color == color : "Color not set correctly";
    assert this.index == index : "Index not set correctly";

    // Invariant check
    invariant();
  }

  protected void invariant() {
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
    // Invariant check
    invariant();
    return index;
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
