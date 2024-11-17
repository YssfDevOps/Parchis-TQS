package test.model.square;

import main.model.Color;
import main.model.Piece;
import main.model.square.RegularSquare;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegularSquareTest {

  @Test
  void handleLandingOnRegularSquare() {
    // 1. Empty Square
    RegularSquare regularSquare = new RegularSquare(10);
    Piece piece = new Piece(Color.RED);
    regularSquare.landHere(piece);
    assertTrue(regularSquare.isOccupied());
    assertFalse(regularSquare.isBlocked(piece));

    // 2. Two piece same color
    RegularSquare regularSquare2 = new RegularSquare(10);
    Piece piece1 = new Piece(Color.RED);
    Piece piece2 = new Piece(Color.RED);
    regularSquare2.landHere(piece1);
    regularSquare2.landHere(piece2);
    // Now forms a blockage
    assertTrue(regularSquare2.isOccupied());
    assertFalse(regularSquare2.isBlocked(piece1));

    // 3. Two pieces different color
    RegularSquare regularSquare3 = new RegularSquare(10);
    Piece redPiece = new Piece(Color.RED);
    Piece bluePiece = new Piece(Color.BLUE);
    regularSquare3.landHere(redPiece);
    assertTrue(regularSquare3.isOccupied());
    regularSquare3.landHere(bluePiece);
    // Red piece should be sent home
    assertTrue(redPiece.isAtHome());
    assertFalse(regularSquare3.isBlocked(bluePiece));
    assertTrue(regularSquare3.isOccupied());
  }

  @Test
  void isBlocked() {
    // Land in blockage
    RegularSquare regularSquare = new RegularSquare(10);
    Piece redPiece1 = new Piece(Color.RED);
    Piece redPiece2 = new Piece(Color.RED);
    Piece bluePiece = new Piece(Color.BLUE);
    regularSquare.landHere(redPiece1);
    regularSquare.landHere(redPiece2);
    // Square is now blocked for bluePiece
    assertTrue(regularSquare.isBlocked(bluePiece));
    regularSquare.landHere(bluePiece);
    // Blue cant land no more
    assertTrue(regularSquare.isBlocked(bluePiece));

    // No pieces
    RegularSquare regularSquare2 = new RegularSquare(10);
    Piece piece = new Piece(Color.RED);
    assertFalse(regularSquare2.isBlocked(piece));

    // One piece same color
    RegularSquare regularSquare3 = new RegularSquare(10);
    Piece piece2 = new Piece(Color.RED);
    regularSquare3.landHere(piece2);
    assertFalse(regularSquare3.isBlocked(piece2));

    // One piece different color
    RegularSquare regularSquare4 = new RegularSquare(10);
    Piece redPiece = new Piece(Color.RED);
    Piece bluePiece2 = new Piece(Color.BLUE);
    regularSquare4.landHere(redPiece);
    // Not blocked, bluePiece can land and capture
    assertFalse(regularSquare4.isBlocked(bluePiece2));
  }

  @Test
  void isShieldSquare() {
    RegularSquare regularSquare = new RegularSquare(10);
    assertFalse(regularSquare.isShieldSquare());

    RegularSquare regularSquare2 = new RegularSquare(1);
    assertFalse(regularSquare2.isShieldSquare());

    RegularSquare regularSquare3 = new RegularSquare(20);
    assertFalse(regularSquare3.isShieldSquare());
  }
}