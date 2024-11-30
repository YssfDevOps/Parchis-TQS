package test.model.square;

import main.model.Color;
import main.model.Piece;
import main.model.square.MockSquare;
import main.model.square.RegularSquare;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    // Statement coverage -------------------------------------------------------------------------

    // 1. Empty pieces
    MockSquare testSquare = new MockSquare(10);
    Piece testPiece = new Piece(Color.RED);
    testSquare.landHere(testPiece);
    testSquare.setOccupied(true);
    assertTrue(testSquare.isOccupied());

    MockSquare testSquare2 = new MockSquare(10);
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

    // Decision coverage ------------------------------------------------------------------------------

    // Case 1: pieces.isEmpty() returns true
    RegularSquare square_dc = new RegularSquare(1);
    Piece piece_dc = new Piece(Color.RED);
    square_dc.landHere(piece_dc);
    assertTrue(square_dc.getPieces().contains(piece_dc), "Case 1: The piece must be in this square.");

    // Case 2 and 3: pieces.isEmpty() returns false and pieces.size() == 1
    Piece piece2_dc = new Piece(Color.RED);
    square_dc.landHere(piece2_dc);
    assertTrue(square_dc.getPieces().contains(piece2_dc), "Case 2-3: The piece must be in this square.");

    // Case 4: pieces.size() != 1
    Piece piece3_dc = new Piece(Color.RED);
    square_dc.landHere(piece3_dc);
    assertFalse(square_dc.getPieces().contains(piece3_dc), "Case 4: The square is full.");

    // Case 5: occupant.getColor().equals(piece.getColor()) returns true
    square_dc.leave(piece2_dc);
    square_dc.landHere(piece2_dc);
    assertTrue(square_dc.getPieces().contains(piece2_dc), "Case 5: The piece must be in this square.");

    // Case 6: occupant.getColor().equals(piece.getColor()) returns false
    square_dc.leave(piece2_dc);
    Piece piece4_dc = new Piece(Color.GREEN);
    square_dc.landHere(piece4_dc);
    assertTrue(square_dc.getPieces().contains(piece4_dc), "Case 6: The piece must be in this square.");
    assertFalse(square_dc.getPieces().contains(piece_dc), "Case 6: The piece must be died.");
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

    // Condition coverage ------------------------------------------------------------------
    // Setup: A square with no pieces
    RegularSquare square = new RegularSquare(0);
    assertFalse(square.isBlocked(new Piece(Color.RED)));

    // Case 1: One piece on the square
    Piece piece1_cc = new Piece(Color.RED);
    square.landHere(piece1_cc); // Add a single piece to the square
    assertFalse(square.isBlocked(new Piece(Color.RED)));
    assertFalse(square.isBlocked(new Piece(Color.BLUE)));

    // Reset the square
    square.leave(piece1_cc);

    // Case 2: Two pieces of the same color
    Piece piece2_cc = new Piece(Color.YELLOW);
    Piece piece3_cc = new Piece(Color.YELLOW);
    square.landHere(piece2_cc);
    square.landHere(piece3_cc);
    assertTrue(square.isBlocked(new Piece(Color.RED)));
    assertFalse(square.isBlocked(new Piece(Color.YELLOW)));

    // Reset the square
    square.leave(piece2_cc);
    square.leave(piece3_cc);

    // Case 3: Two pieces of different colors
    Piece piece4_cc = new Piece(Color.GREEN);
    Piece piece5_cc = new Piece(Color.BLUE);
    square.landHere(piece4_cc);
    square.landHere(piece5_cc);
    assertFalse(square.isBlocked(new Piece(Color.RED)));

    // Reset the square
    square.leave(piece5_cc);

    // Case 4: Empty square, null piece passed in
    assertThrows(AssertionError.class, () -> square.isBlocked(null));
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