package test.model.square;

import main.model.Color;
import main.model.Piece;
import main.model.square.ShieldSquare;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShieldSquareTest {

  @Test
  void handleLandingOnShieldSquare() {
    // 1. Landing on a shield square with one piece
    ShieldSquare shieldSquare = new ShieldSquare(5);
    Piece piece = new Piece(Color.RED);
    shieldSquare.landHere(piece);
    assertTrue(shieldSquare.isOccupied());
    assertFalse(shieldSquare.isBlocked(piece));

    // 2. Landing on a shield square with two pieces of different colors
    ShieldSquare shieldSquare1 = new ShieldSquare(5);
    Piece piece1 = new Piece(Color.RED);
    Piece piece2 = new Piece(Color.BLUE);
    shieldSquare1.landHere(piece1);
    shieldSquare1.landHere(piece2);
    assertTrue(shieldSquare1.isOccupied());
    assertTrue(shieldSquare1.isBlocked(piece1));
    assertTrue(shieldSquare1.isBlocked(piece2));
    // Square should now be blocked, no one can pass.
    Piece piece3 = new Piece(Color.GREEN);
    assertTrue(shieldSquare1.isBlocked(piece3));

    // 3. Land on a shield square
    ShieldSquare shieldSquare2 = new ShieldSquare(5);
    Piece piece4 = new Piece(Color.RED);
    Piece piece5 = new Piece(Color.BLUE);
    Piece piece6 = new Piece(Color.GREEN);
    shieldSquare2.landHere(piece4);
    shieldSquare2.landHere(piece5);
    shieldSquare2.landHere(piece6);
    // Piece3 should not be able to land
    assertTrue(shieldSquare2.isBlocked(piece6));

    // Equivalence Partitioning and Boundary Value Analysis for ShieldSquare landing
    // - Landing when square has 0 pieces (can land)
    // - Landing when square has 1 piece (can land)
    // - Landing when square has 2 pieces (cannot land)
    // - Landing when square is blocked (full capacity)

    // Boundary Values:
    // - Number of pieces: 0, 1, 2 (maximum capacity)
    // 1. Landing on an empty ShieldSquare
    ShieldSquare shieldSquareEmpty = new ShieldSquare(5);
    Piece redPiece = new Piece(Color.RED);
    shieldSquareEmpty.landHere(redPiece);
    assertFalse(shieldSquareEmpty.isBlocked(redPiece));
    assertEquals(shieldSquareEmpty, redPiece.getSquare());

    // 2. Landing on ShieldSquare with one piece
    Piece bluePiece = new Piece(Color.BLUE);
    shieldSquareEmpty.landHere(bluePiece);
    assertTrue(shieldSquareEmpty.isBlocked(bluePiece));
    assertEquals(shieldSquareEmpty, bluePiece.getSquare());

    // 3. Landing on ShieldSquare when it has two pieces (full capacity)
    Piece greenPiece = new Piece(Color.GREEN);
    assertTrue(shieldSquareEmpty.isBlocked(greenPiece));
    shieldSquareEmpty.landHere(greenPiece);
    assertNull(greenPiece.getSquare());

    // Statement coverage
    // 1. When piece is null
    ShieldSquare shieldSquare7 = new ShieldSquare(5);
    assertThrows(AssertionError.class, () -> shieldSquare7.landHere(null));

    // 2. When pieces size > 2
    // Subclass to access and manipulate pieces
    class TestShieldSquare extends ShieldSquare {
      public TestShieldSquare(int position) {
        super(position);
      }

      public void addPieceDirectly(Piece piece) {
        this.pieces.add(piece);
      }
    }

    TestShieldSquare testShieldSquare = new TestShieldSquare(5);
    Piece piece8 = new Piece(Color.RED);
    Piece piece9 = new Piece(Color.BLUE);
    Piece piece10 = new Piece(Color.GREEN);

    // Land two pieces normally
    testShieldSquare.landHere(piece8);
    testShieldSquare.landHere(piece9);
    testShieldSquare.addPieceDirectly(piece10);
    assertThrows(AssertionError.class, testShieldSquare::invariant);

    // Subclass that overrides isShieldSquare to return false
    class TestShieldSquare2 extends ShieldSquare {
      public TestShieldSquare2(int position) {
        super(position);
      }

      @Override
      public boolean isShieldSquare() {
        return false; // Force isShieldSquare to return false
      }
    }

    TestShieldSquare2 testShieldSquare2 = new TestShieldSquare2(5);
    Piece testPiece = new Piece(Color.RED);

    testShieldSquare2.landHere(testPiece);
    Piece testPiece2 = new Piece(Color.BLUE);
    assertThrows(UnsupportedOperationException.class, () -> testShieldSquare2.landHere(testPiece2));
  }

  @Test
  void isBlocked() {
    // 1. Empty, not blocked
    ShieldSquare shieldSquare = new ShieldSquare(5);
    Piece piece = new Piece(Color.RED);
    assertFalse(shieldSquare.isBlocked(piece));

    // 2. Shield square is not blocked when it has one piece
    ShieldSquare shieldSquare2 = new ShieldSquare(5);
    Piece piece1 = new Piece(Color.RED);
    Piece piece2 = new Piece(Color.BLUE);
    shieldSquare2.landHere(piece1);
    assertFalse(shieldSquare2.isBlocked(piece2));

    // 3. Shield square is blocked when it has two pieces
    ShieldSquare shieldSquare3 = new ShieldSquare(5);
    Piece piece4 = new Piece(Color.RED);
    Piece piece5 = new Piece(Color.BLUE);
    Piece piece6 = new Piece(Color.GREEN);
    shieldSquare3.landHere(piece4);
    shieldSquare3.landHere(piece5);
    assertTrue(shieldSquare3.isBlocked(piece6));

    // Equivalence Classes:
    // - ShieldSquare with 0 or 1 piece (not blocked)
    // - ShieldSquare with 2 pieces (blocked)
    // 1. ShieldSquare with no pieces (not blocked)
    ShieldSquare shieldSquareEmpty = new ShieldSquare(8);
    Piece redPiece = new Piece(Color.RED);
    assertFalse(shieldSquareEmpty.isBlocked(redPiece));

    // 2. ShieldSquare with one piece (not blocked)
    shieldSquareEmpty.landHere(redPiece);
    Piece bluePiece = new Piece(Color.BLUE);
    assertFalse(shieldSquareEmpty.isBlocked(bluePiece));

    // 3. ShieldSquare with two pieces (blocked)
    shieldSquareEmpty.landHere(bluePiece); // Now has two pieces
    Piece greenPiece = new Piece(Color.GREEN);
    assertTrue(shieldSquareEmpty.isBlocked(greenPiece));

    // Statement coverage
    // 1. When piece is null
    ShieldSquare shieldSquare5 = new ShieldSquare(5);
    assertThrows(AssertionError.class, () -> shieldSquare5.isBlocked(null));
  }

  @Test
  void isShieldSquare() {
    // Equivalence Classes:
    // - ShieldSquare instances (should return true)
    // - Other square types (should return false)

    // 1. Check if each Shield Square are in fact Shield Square.
    ShieldSquare shieldSquare = new ShieldSquare(5);
    assertTrue(shieldSquare.isShieldSquare());

    ShieldSquare shieldSquare2 = new ShieldSquare(10);
    assertTrue(shieldSquare2.isShieldSquare());

    ShieldSquare shieldSquare3 = new ShieldSquare(12);
    assertTrue(shieldSquare3.isShieldSquare());
  }
}