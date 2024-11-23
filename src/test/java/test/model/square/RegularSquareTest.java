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

    // Equivalence Partitioning and Boundary Value Analysis for RegularSquare landing

    // Equivalence Classes:
    // - Empty square (can land)
    // - Square with own piece same color (can land, form blockage)
    // - Square with opponent's piece (can capture)
    // - Square blocked by opponent's blockage (cannot land)

    // Boundary Conditions:
    // - Number of pieces: 0, 1, 2 (maximum capacity)
    // 1. Landing on empty RegularSquare
    RegularSquare emptySquare = new RegularSquare(10);
    Piece redPiece4 = new Piece(Color.RED);
    emptySquare.landHere(redPiece4);
    assertTrue(emptySquare.isOccupied());
    assertEquals(emptySquare, redPiece4.getSquare());

    // 2. Landing on RegularSquare with own piece (forming blockage)
    RegularSquare squareWithOwnPiece = new RegularSquare(11);
    Piece redPiece1 = new Piece(Color.RED);
    squareWithOwnPiece.landHere(redPiece1);
    Piece redPiece2 = new Piece(Color.RED);
    squareWithOwnPiece.landHere(redPiece2);
    assertTrue(squareWithOwnPiece.isBlocked(new Piece(Color.BLUE)));
    assertFalse(squareWithOwnPiece.isBlocked(redPiece1));

    // 3. Attempting to land third own piece on own blockage (cannot land)
    Piece redPiece3 = new Piece(Color.RED);
    squareWithOwnPiece.landHere(redPiece3);
    assertNull(redPiece3.getSquare());

    // 4. Attempting to land on RegularSquare blocked by opponent's blockage (canot land)
    RegularSquare opponentBlockedSquare = new RegularSquare(13);
    Piece bluePiece1 = new Piece(Color.BLUE);
    Piece bluePiece2 = new Piece(Color.BLUE);
    opponentBlockedSquare.landHere(bluePiece1);
    opponentBlockedSquare.landHere(bluePiece2); // Opponent's blockage
    redPiece4 = new Piece(Color.RED);
    assertTrue(opponentBlockedSquare.isBlocked(redPiece4));
    opponentBlockedSquare.landHere(redPiece4);
    assertNull(redPiece4.getSquare());

    // Statement coverage
    // Subclass to override isOccupied() function to return empty
    class TestRegularSquare extends RegularSquare {
      public TestRegularSquare(int position) {
        super(position);
      }

      @Override
      public boolean isOccupied() {
        return true; // Force to return true
      }

      // Expose the protected method for testing
      @Override
      public void handleLandingOnRegularSquare(Piece piece) {
        super.handleLandingOnRegularSquare(piece);
      }

    }

    // 1. Empty pieces
    TestRegularSquare testSquare = new TestRegularSquare(10);
    Piece testPiece = new Piece(Color.RED);
    testSquare.landHere(testPiece);
    assertTrue(testSquare.isOccupied());

    // Subclass to access and manipulate pieces
    class TestRegularSquare2 extends RegularSquare {
      public TestRegularSquare2(int position) {
        super(position);
      }

      public void addPieceDirectly(Piece piece) {
        this.pieces.add(piece);
      }
    }

    TestRegularSquare2 testSquare2 = new TestRegularSquare2(10);
    Piece piece7 = new Piece(Color.RED);
    Piece piece8 = new Piece(Color.BLUE);
    Piece piece9 = new Piece(Color.GREEN);

    // Add three pieces directly to exceed capacity
    testSquare2.addPieceDirectly(piece7);
    testSquare2.addPieceDirectly(piece8);
    testSquare2.addPieceDirectly(piece9);
    assertThrows(AssertionError.class, testSquare2::invariant);

    // 3. Cover UnsupportedOperationException
    class TestRegularSquare3 extends RegularSquare {
      public TestRegularSquare3(int position) {
        super(position);
      }

      @Override
      public boolean isShieldSquare() {
        return true; // Force to return true
      }
    }

    TestRegularSquare3 testSquare3 = new TestRegularSquare3(10);
    Piece testPiece2 = new Piece(Color.BLUE);
    testSquare3.landHere(new Piece(Color.RED));

    assertThrows(UnsupportedOperationException.class, () -> testSquare3.landHere(testPiece2));
  }

  @Test
  void isBlocked() {
    // 1. Land in blockage
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

    // 2. No pieces
    RegularSquare regularSquare2 = new RegularSquare(10);
    Piece piece = new Piece(Color.RED);
    assertFalse(regularSquare2.isBlocked(piece));

    // 3. One piece same color
    RegularSquare regularSquare3 = new RegularSquare(10);
    Piece piece2 = new Piece(Color.RED);
    regularSquare3.landHere(piece2);
    assertFalse(regularSquare3.isBlocked(piece2));

    // 4. One piece different color
    RegularSquare regularSquare4 = new RegularSquare(10);
    Piece redPiece = new Piece(Color.RED);
    Piece bluePiece2 = new Piece(Color.BLUE);
    regularSquare4.landHere(redPiece);
    // Not blocked, bluePiece can land and capture
    assertFalse(regularSquare4.isBlocked(bluePiece2));

    // Equivalence Classes:
    // - Square is empty or has one piece (not blocked)
    // - Square has own blockage (blocked for opponents)
    // - Square has opponent's blockage (blocked for own pieces)

    // 1. Empty square (not blocked)
    RegularSquare emptySquare = new RegularSquare(14);
    Piece redPiece3 = new Piece(Color.RED);
    assertFalse(emptySquare.isBlocked(redPiece3));

    // 2. Square with one own piece (not blocked)
    emptySquare.landHere(redPiece3);
    assertFalse(emptySquare.isBlocked(redPiece3));

    // 3. Square with own blockage (blocked for opponents)
    Piece redPiece4 = new Piece(Color.RED);
    emptySquare.landHere(redPiece4); // Forms blockage
    Piece bluePiece3 = new Piece(Color.BLUE);
    assertTrue(emptySquare.isBlocked(bluePiece3));

    // 4. Square with opponent's blockage (blocked for own pieces)
    RegularSquare opponentBlockedSquare = new RegularSquare(15);
    Piece bluePiece1 = new Piece(Color.BLUE);
    Piece bluePiece4 = new Piece(Color.BLUE);
    opponentBlockedSquare.landHere(bluePiece1);
    opponentBlockedSquare.landHere(bluePiece4); // Opponent's blockage
    assertTrue(opponentBlockedSquare.isBlocked(redPiece3));

    // Statement coverage
    // 1. Cover assertion
    RegularSquare regularSquare6 = new RegularSquare(10);
    assertThrows(AssertionError.class, () -> regularSquare6.isBlocked(null));

    // 2. We need to create a blockage with two pieces of the same color
    Piece redPiece6 = new Piece(Color.RED);
    Piece redPiece8 = new Piece(Color.RED);
    regularSquare6.landHere(redPiece6);
    regularSquare6.landHere(redPiece8);

    // 3. Now 2 pieces and both pieces have the same color
    Piece bluePiece5 = new Piece(Color.BLUE);
    assertTrue(regularSquare6.isBlocked(bluePiece5));

    // Test with a piece of the same color
    Piece redPiece5 = new Piece(Color.RED);
    assertFalse(regularSquare6.isBlocked(redPiece5));

    // Subclass to manipulate the pieces field directly
    class TestRegularSquare extends RegularSquare {
      public TestRegularSquare(int position) {
        super(position);
      }

      public void addPieceDirectly(Piece piece) {
        this.pieces.add(piece);
      }
    }

    // 4. Case of being at the same time 2 pieces of different color.
    TestRegularSquare testSquare = new TestRegularSquare(10);
    Piece redPiece7 = new Piece(Color.RED);
    Piece bluePiece7 = new Piece(Color.BLUE);

    testSquare.addPieceDirectly(redPiece7);
    testSquare.addPieceDirectly(bluePiece7);

    Piece greenPiece = new Piece(Color.GREEN);
    assertFalse(testSquare.isBlocked(greenPiece));
    assertFalse(testSquare.isBlocked(redPiece7));
    assertFalse(testSquare.isBlocked(bluePiece7));
  }

  @Test
  void isShieldSquare() {
    // Equivalence Classes:
    // - RegularSquare instances (should return false)
    RegularSquare regularSquare = new RegularSquare(10);
    assertFalse(regularSquare.isShieldSquare());

    RegularSquare regularSquare2 = new RegularSquare(1);
    assertFalse(regularSquare2.isShieldSquare());

    RegularSquare regularSquare3 = new RegularSquare(20);
    assertFalse(regularSquare3.isShieldSquare());
  }
}