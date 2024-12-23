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

    //Invariant check
    invariant();
  }

  public void invariant() {
    assert position >= 0 : "Position must be positive";
    assert pieces != null : "Pieces list must not be null";
    for (Piece p : pieces) {
      assert p != null : "Pieces list must not contain null elements";
    }
  }

  public void setPosition(int position) {
    this.position = position;
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Square ").append(position);
    if (isShieldSquare()) {
      sb.append(" (Shield Square)");
    }
    if (!pieces.isEmpty()) {
      sb.append(" - Pieces: ");
      for (Piece piece : pieces) {
        sb.append(piece.getColor()).append(" ").append(piece.getId()).append(", ");
      }
      sb.setLength(sb.length() - 2); // Remove last comma and space
    }
    return sb.toString();
  }
}
