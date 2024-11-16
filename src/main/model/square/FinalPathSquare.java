package main.model.square;

import main.model.Color;
import main.model.Piece;

public class FinalPathSquare extends Square {
  private Color color;
  private int index; // Position in the final path (0 to 7)

  public FinalPathSquare(int index, Color color) {
    super(index);
    this.color = color;
    this.index = index;
  }

  public Color getColor() {
    return color;
  }

  public int getIndex() {
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
  public boolean isBlocked(Piece piece) {
    return false;
  }

  @Override
  public boolean isShieldSquare() {
    return false;
  }
}
