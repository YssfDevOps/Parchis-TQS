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

    // Equivalence Partitioning and Boundary Value Analysis for position
    // - Valid positions: positions between 0 and 67
    // - Invalid positions: positions less than 0, positions greater than 67
    // 1. Valid range:
    Square squareAtZero = new RegularSquare(0);
    assertEquals(0, squareAtZero.getPosition());
    Square squareMid = new RegularSquare(34);
    assertEquals(34, squareMid.getPosition());
    Square squareAtMax = new ShieldSquare(67);
    assertEquals(67, squareAtMax.getPosition());

    // Boundary Values:
    assertThrows(AssertionError.class, () -> new RegularSquare(-10));
    assertThrows(AssertionError.class, () -> new RegularSquare(-1));

    Square squareAtOne = new RegularSquare(1);
    assertEquals(1, squareAtOne.getPosition());

    Square squareAtMaxMin = new ShieldSquare(66);
    assertEquals(66, squareAtMaxMin.getPosition());

    assertThrows(AssertionError.class, () -> new ShieldSquare(68));

    assertThrows(AssertionError.class, () -> new ShieldSquare(70));
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

    // Equivalence Partitioning and Boundary Value
    // - Landing on an empty square
    // - Landing on a square occupied by own piece (not blocked)
    // - Landing on a square occupied by own pieces forming a blockage
    // - Landing on a square occupied by opponent's piece (can capture)
    // - Landing on a square blocked by opponent's blockage (cannot land)
    // 1. Landing on an empty square
    Square emptySquare = new RegularSquare(10);
    Piece redPiece3 = new Piece(Color.RED);
    emptySquare.landHere(redPiece3);
    assertTrue(emptySquare.isOccupied());
    assertEquals(emptySquare, redPiece3.getSquare());

    // 2. Landing on a square with one own piece (forming blockage)
    Square squareWithOwnPiece = new RegularSquare(11);
    Piece redPiece4 = new Piece(Color.RED);
    squareWithOwnPiece.landHere(redPiece4);
    Piece redPiece5 = new Piece(Color.RED);
    squareWithOwnPiece.landHere(redPiece5);
    assertTrue(squareWithOwnPiece.isBlocked(new Piece(Color.BLUE)));
    assertFalse(squareWithOwnPiece.isBlocked(redPiece4));

    // 3. Landing on a square occupied by opponent's piece (capture)
    Square squareWithOpponentPiece = new RegularSquare(12);
    Piece bluePiece3 = new Piece(Color.BLUE);
    squareWithOpponentPiece.landHere(bluePiece3);
    redPiece3 = new Piece(Color.RED);
    squareWithOpponentPiece.landHere(redPiece3);
    assertTrue(bluePiece3.isAtHome());
    assertFalse(squareWithOpponentPiece.isBlocked(redPiece3));

    // 5. Landing on a square blocked by opponent's blockage (cannot land)
    Square blockedSquare = new RegularSquare(13);
    Piece bluePiece1 = new Piece(Color.BLUE);
    Piece bluePiece4 = new Piece(Color.BLUE);
    blockedSquare.landHere(bluePiece1);
    blockedSquare.landHere(bluePiece4); // Forms blockage
    redPiece3 = new Piece(Color.RED);
    assertTrue(blockedSquare.isBlocked(redPiece3));
    blockedSquare.landHere(redPiece3);
    assertNull(redPiece3.getSquare());
  }

  @Test
  void leave() {
    Square square = new RegularSquare(10);
    Piece piece = new Piece(Color.RED);
    square.landHere(piece);
    assertTrue(square.isOccupied());
    square.leave(piece);
    assertFalse(square.isOccupied());

    // Equivalence Partitioning:
    // - Leaving a square when the piece is on the square
    // - Leaving a square when the piece is not on the square (invalid operation)

    // 1. Leaving a square when the piece is on the square
    Square square2 = new RegularSquare(10);
    Piece piece2 = new Piece(Color.RED);
    square2.landHere(piece2);
    assertTrue(square2.isOccupied());
    square2.leave(piece2);
    assertFalse(square2.isOccupied());

    // 2. Leaving a square when the piece is not on the square
    Square anotherSquare = new RegularSquare(11);
    assertThrows(IllegalStateException.class, () -> anotherSquare.leave(piece2));
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

    // Equivalence Partitioning:
    // - Square is empty (not occupied)
    // - Square has one or more pieces (occupied)

    // 1. Square is empty
    Square square2 = new RegularSquare(10);
    assertFalse(square2.isOccupied());

    // 2. Square has one piece
    Piece piece2 = new Piece(Color.RED);
    square2.landHere(piece2);
    assertTrue(square2.isOccupied());

    // 3. Square after piece leaves
    square2.leave(piece2);
    assertFalse(square2.isOccupied());
  }

  @Test
  void isBlocked() {
    // Equivalence Partitioning:
    // - Square is empty or has one piece (not blocked)
    // - Square has two pieces of same color (blocked for opponents)
    // - Square has two pieces of opponent's color (blocked for own pieces)

    // 1. Square has no pieces
    Square square = new RegularSquare(10);
    Piece piece = new Piece(Color.RED);
    assertFalse(square.isBlocked(piece));

    // 2. Piece of same color
    Square square1 = new RegularSquare(10);
    Piece piece1 = new Piece(Color.RED);
    square1.landHere(piece1);
    assertFalse(square1.isBlocked(piece1));

    // 3. Square has one opponent piece
    Square square2 = new RegularSquare(10);
    Piece redPiece = new Piece(Color.RED);
    Piece bluePiece = new Piece(Color.BLUE);
    square2.landHere(redPiece);
    assertFalse(square2.isBlocked(bluePiece)); // Can capture

    // 4. When square has a blockage
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
    // Equivalence Partitioning:
    // - Square is a ShieldSquare (returns true)
    // - Square is not a ShieldSquare (returns false)

    Square regularSquare = new RegularSquare(10);
    Square shieldSquare = new ShieldSquare(15);

    assertFalse(regularSquare.isShieldSquare());
    assertTrue(shieldSquare.isShieldSquare());
  }

  @Test
  void testToString() {
    // Simple test for toString method
    Square square = new RegularSquare(10);
    Piece piece = new Piece(Color.RED);
    square.landHere(piece);

    String expected = "Square 10 - Pieces: RED " + piece.getId();
    assertEquals(expected, square.toString());
  }
}