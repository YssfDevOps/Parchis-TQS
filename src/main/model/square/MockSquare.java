package main.model.square;

import main.model.Piece;

/*
* MOCK OBJECT CLASS : SQUARE
*/

public class MockSquare extends RegularSquare {
  boolean blocked = false;
  boolean occupied = false;
  boolean shield = false;

  public MockSquare(int position) {
    super(position);
  }

  @Override
  public boolean isBlocked(Piece piece) {
    return blocked;
  }

  public void setBlocked(boolean blocked) {
    this.blocked = blocked;
  }

  @Override
  public boolean isOccupied() {
    return occupied;
  }

  public void setOccupied(boolean occupied) {
    this.occupied = occupied;
  }

  @Override
  public boolean isShieldSquare() {
    return shield;
  }

  public void setShieldSquare(boolean shield) {
    this.shield = shield;
  }

  @Override
  protected void handleLandingOnShieldSquare(Piece piece) {
    // No pre-condition because already in landHere

    if (pieces.size() < 2) { // Can land if less than two pieces
      pieces.add(piece);
      piece.setSquare(this);
    }

    // Invariant check
    invariant();
  }

  public void addPieceDirectly(Piece piece) {
    this.pieces.add(piece);
  }
}

