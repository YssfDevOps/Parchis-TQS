package main.model.square;

import main.model.Piece;
import java.util.ArrayList;
import java.util.List;

public abstract class Square {
  protected int position;
  protected List<Piece> pieces;

  public Square(int position) {
    // Precondition
    assert position >= 0 : "Position must positive";
    assert position < 68 : "Out of range";

    this.position = position;
    this.pieces = new ArrayList<>();

    // Postcondition
    assert this.position == position : "Position was not set correctly";
    assert this.pieces != null : "Piece not initialized";

    //Invariant check
    invariant();
  }

  protected void invariant() {
    assert position >= 0 : "Position must be positive";
    assert pieces != null : "Pieces list must not be null";
    for (Piece p : pieces) {
      assert p != null : "Pieces list must not contain null elements";
    }

  }

  public int getPosition() {
    return position;
  }

  public List<Piece> getPieces() {
    return pieces;
  }

  public void landHere(Piece piece) {
    // Precondition: piece is not null
    assert piece != null : "Piece cannot be null";

    if (isOccupied()) {
      if (isShieldSquare()) {
        handleLandingOnShieldSquare(piece);
      } else {
        handleLandingOnRegularSquare(piece);
      }
    } else {
      // Square is empty
      pieces.add(piece);
      piece.setSquare(this);
    }

    // Invariant check
    invariant();
  }

  protected abstract void handleLandingOnShieldSquare(Piece piece);

  protected abstract void handleLandingOnRegularSquare(Piece piece);

  public void leave(Piece piece) {
    // Precondition
    assert piece != null : "Piece cannot be null";
    if (!pieces.contains(piece)) {
      throw new IllegalStateException("Piece is not on this square");
    }

    pieces.remove(piece);

    // Post condition
    assert !pieces.contains(piece) : "Piece was not removed from the square";
    // Invariant check
    invariant();
  }

  public boolean isOccupied() {
    // Invariant check
    invariant();
    return !pieces.isEmpty();
  }

  public abstract boolean isBlocked(Piece piece);

  public abstract boolean isShieldSquare();
}
