package main.model.square;

import main.model.Piece;
import java.util.ArrayList;
import java.util.List;

public abstract class Square {
  protected int position;
  protected List<Piece> pieces;

  public Square(int position) {
    this.position = position;
    this.pieces = new ArrayList<>();
  }

  public int getPosition() {
    return position;
  }

  public void landHere(Piece piece) {
    if (isOccupied()) {
      if (isShieldSquare()) {
        handleLandingOnShieldSquare(piece);
      } else {
        handleLandingOnRegularSquare(piece);
      }
    } else {
      // Square is empty
      pieces.add(piece);
    }
  }

  protected abstract void handleLandingOnShieldSquare(Piece piece);

  protected abstract void handleLandingOnRegularSquare(Piece piece);

  public void leave(Piece piece) {
    pieces.remove(piece);
  }

  public boolean isOccupied() {
    return !pieces.isEmpty();
  }

  public abstract boolean isBlocked(Piece piece);

  public abstract boolean isShieldSquare();
}
