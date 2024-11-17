package test.model.square;

import main.model.Color;
import main.model.Piece;
import main.model.square.RegularSquare;
import main.model.square.ShieldSquare;
import main.model.square.Square;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

  @Test
  void getPosition() {
    // Test with various positions
    Square square1 = new RegularSquare(0);
    Square square2 = new RegularSquare(10);
    Square square3 = new ShieldSquare(25);
    Square square4 = new ShieldSquare(67);

    assertEquals(0, square1.getPosition());
    assertEquals(10, square2.getPosition());
    assertEquals(25, square3.getPosition());
    assertEquals(67, square4.getPosition());
  }

  @Test
  void landHere() {
    // 1. Empty Square
    Square square = new RegularSquare(10);
    Piece piece = new Piece(Color.RED);
    square.landHere(piece);
    assertTrue(square.isOccupied());
    assertFalse(square.isBlocked(piece));

    // 2. Landing on a square occupied by own piece
    Square square1 = new RegularSquare(10);
    Piece piece1 = new Piece(Color.RED);
    Piece piece2 = new Piece(Color.RED);

    square1.landHere(piece1);
    square1.landHere(piece2);

    assertTrue(square1.isOccupied());
    assertFalse(square1.isBlocked(piece1));
    assertFalse(square1.isBlocked(piece2));

    // 3. Landing on a square occupied by opponent's piece
    Square square2 = new RegularSquare(10);
    Piece redPiece = new Piece(Color.RED);
    Piece bluePiece = new Piece(Color.BLUE);

    square2.landHere(redPiece);
    assertTrue(square2.isOccupied());
    square2.landHere(bluePiece);

    // Red piece should be sent home
    assertTrue(redPiece.isAtHome());
    assertFalse(square2.isBlocked(bluePiece));
    assertTrue(square2.isOccupied());

    // Land in blocked square
    Square square3 = new RegularSquare(10);
    Piece redPiece1 = new Piece(Color.RED);
    Piece redPiece2 = new Piece(Color.RED);
    Piece bluePiece2 = new Piece(Color.BLUE);

    square3.landHere(redPiece1);
    square3.landHere(redPiece2);

    // Square is now blocked
    assertTrue(square3.isBlocked(bluePiece2));
    square3.landHere(bluePiece2);
    // Blue piece should not be able to land remains at its position
    assertTrue(square3.isBlocked(bluePiece2));
  }

  @Test
  void leave() {
    Square square = new RegularSquare(10);
    Piece piece = new Piece(Color.RED);

    square.landHere(piece);
    assertTrue(square.isOccupied());

    square.leave(piece);
    assertFalse(square.isOccupied());
  }

  @Test
  void isOccupied() {
    Square square = new RegularSquare(10);
    assertFalse(square.isOccupied());

    Piece piece = new Piece(Color.RED);
    square.landHere(piece);
    assertTrue(square.isOccupied());

    square.leave(piece);
    assertFalse(square.isOccupied());
  }

  @Test
  void isBlocked() {
    // Square has no pieces
    Square square = new RegularSquare(10);
    Piece piece = new Piece(Color.RED);
    assertFalse(square.isBlocked(piece));

    // Piece of same color
    Square square1 = new RegularSquare(10);
    Piece piece1 = new Piece(Color.RED);
    square1.landHere(piece1);
    assertFalse(square1.isBlocked(piece1));

    // Square has one opponent piece
    Square square2 = new RegularSquare(10);
    Piece redPiece = new Piece(Color.RED);
    Piece bluePiece = new Piece(Color.BLUE);
    square2.landHere(redPiece);
    assertFalse(square2.isBlocked(bluePiece)); // Can capture

    // When square has a blockage
    Square square3 = new RegularSquare(10);
    Piece redPiece1 = new Piece(Color.RED);
    Piece redPiece2 = new Piece(Color.RED);
    Piece bluePiece1 = new Piece(Color.BLUE);
    square3.landHere(redPiece1);
    square3.landHere(redPiece2);
    assertTrue(square3.isBlocked(bluePiece1));
    assertFalse(square3.isBlocked(redPiece1));
  }

  @Test
  void isShieldSquare() {
    // Test isShieldSquare method
    Square regularSquare = new RegularSquare(10);
    Square shieldSquare = new ShieldSquare(15);

    assertFalse(regularSquare.isShieldSquare());
    assertTrue(shieldSquare.isShieldSquare());
  }
}