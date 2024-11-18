package test.model.square;

import main.model.Color;
import main.model.Piece;
import main.model.square.FinalPathSquare;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FinalPathSquareTest {
  @Test
  void getColor() {
    // Test getters for some regular cases
    Color color = Color.GREEN;
    int index = 2;
    FinalPathSquare finalPathSquare = new FinalPathSquare(index, color);
    assertEquals(color, finalPathSquare.getColor());

    Color color2 = Color.BLUE;
    int index2 = 5;
    FinalPathSquare finalPathSquare2 = new FinalPathSquare(index2, color2);
    assertEquals(color2, finalPathSquare2.getColor());

    // Equivalence Classes:
    // - Valid colors (RED, BLUE, GREEN, YELLOW)
    // - Invalid colors (null, if allowed)

    // 1. Valid Color Red
    FinalPathSquare redFinalSquare = new FinalPathSquare(0, Color.RED);
    assertEquals(Color.RED, redFinalSquare.getColor());

    // 2. Valid color BLUE
    FinalPathSquare blueFinalSquare = new FinalPathSquare(1, Color.BLUE);
    assertEquals(Color.BLUE, blueFinalSquare.getColor());

    // 3. Invalid color (null)
    assertThrows(AssertionError.class, () -> new FinalPathSquare(2, null));


    // Statement coverage
    // 1. Cover invariant
    // Create a subclass to access and manipulate pieces
    class TestFinalPathSquare extends FinalPathSquare {
      public TestFinalPathSquare(int index, Color color) {
        super(index, color);
      }

      public void addPieceDirectly(Piece piece) {
        this.pieces.add(piece);
      }

      public void setColor(Color newColor) {
        this.color = newColor;
      }
    }
    TestFinalPathSquare testSquare = new TestFinalPathSquare(0, Color.RED);
    Piece bluePiece = new Piece(Color.BLUE);
    testSquare.addPieceDirectly(bluePiece);
    assertThrows(AssertionError.class, testSquare::invariant);
  }

  @Test
  void getIndex() {
    // Test getters for some regular cases
    Color color = Color.GREEN;
    int index = 2;
    FinalPathSquare finalPathSquare = new FinalPathSquare(index, color);
    assertEquals(index, finalPathSquare.getIndex());

    Color color2 = Color.BLUE;
    int index2 = 5;
    FinalPathSquare finalPathSquare2 = new FinalPathSquare(index2, color2);
    assertEquals(color2, finalPathSquare2.getColor());

    // Equivalence Classes:
    // - Valid indices: 0 to 7 inclusive
    // - Invalid indices: less than 0, greater than 7

    // Boundary Values:
    // - Lower boundary: -1 (invalid), 0 (valid), 1 (valid)
    // - Upper boundary: 6 (valid), 7 (valid), 8 (invalid)

    // 1. Index at lower boundary (0)
    FinalPathSquare squareAtZero = new FinalPathSquare(0, Color.GREEN);
    assertEquals(0, squareAtZero.getIndex());

    // 2. Index within valid range (e.g., 4)
    FinalPathSquare squareMid = new FinalPathSquare(4, Color.YELLOW);
    assertEquals(4, squareMid.getIndex());

    // 3. Index at upper boundary (7)
    FinalPathSquare squareAtMax = new FinalPathSquare(7, Color.RED);
    assertEquals(7, squareAtMax.getIndex());

    // 4. Index below lower and upper boundary
    assertThrows(AssertionError.class, () -> new FinalPathSquare(-1, Color.BLUE));

    FinalPathSquare squareUpperFront = new FinalPathSquare(1, Color.YELLOW);
    assertEquals(1, squareUpperFront.getIndex());

    // 5. Index above lower and upper boundary
    assertThrows(AssertionError.class, () -> new FinalPathSquare(8, Color.GREEN));

    FinalPathSquare squareLow = new FinalPathSquare(6, Color.YELLOW);
    assertEquals(6, squareLow.getIndex());

    // 6. Out of range really far
    assertThrows(AssertionError.class, () -> new FinalPathSquare(-10, Color.GREEN));
    assertThrows(AssertionError.class, () -> new FinalPathSquare(10, Color.GREEN));

    // Statement coverage
    // Create a subclass to manipulate index
    class TestFinalPathSquare extends FinalPathSquare {
      public TestFinalPathSquare(int index, Color color) {
        super(index, color);
      }

      public void setIndex(int newIndex) {
        this.index = newIndex;
      }
    }

    TestFinalPathSquare testSquare = new TestFinalPathSquare(0, Color.RED);
    testSquare.setIndex(-1);
    assertThrows(AssertionError.class, testSquare::getIndex);
  }

  @Test
  void handleLandingOnFinalPathSquare() {
    FinalPathSquare finalPathSquare = new FinalPathSquare(0, Color.RED);
    Piece redPiece = new Piece(Color.RED);
    finalPathSquare.landHere(redPiece);
    assertTrue(finalPathSquare.isOccupied());
    assertFalse(finalPathSquare.isBlocked(redPiece));

    FinalPathSquare finalPathSquare2 = new FinalPathSquare(0, Color.BLUE);
    Piece bluePiece = new Piece(Color.BLUE);
    finalPathSquare2.landHere(bluePiece);
    // Blue piece should not be allowed to land; depending on implementation, it might be sent home
    assertTrue(finalPathSquare.isOccupied());
    assertFalse(finalPathSquare.isBlocked(bluePiece));

    // Equivalence Classes:
    // - Landing with matching color piece (allowed)
    // - Landing with non-matching color piece (not allowed)

    // 1. Landing with matching color piece
    FinalPathSquare redFinalSquare = new FinalPathSquare(0, Color.RED);
    Piece redPiece4 = new Piece(Color.RED);
    redFinalSquare.landHere(redPiece4);
    assertEquals(redFinalSquare, redPiece4.getSquare());

    // 2. Landing with non-matching color piece
    Piece bluePiece2 = new Piece(Color.BLUE);
    assertThrows(UnsupportedOperationException.class, () -> redFinalSquare.landHere(bluePiece2));

    // Statement coverage
    // Subclass to override isShieldSquare
    class TestFinalPathSquare extends FinalPathSquare {
      public TestFinalPathSquare(int index, Color color) {
        super(index, color);
      }

      @Override
      public boolean isShieldSquare() {
        return true; // Force to return true
      }
    }

    TestFinalPathSquare testSquare = new TestFinalPathSquare(0, Color.RED);
    Piece redPiece5 = new Piece(Color.RED);

    // Subclass to override isOccupied
    class TestFinalPathSquare2 extends TestFinalPathSquare {
      public TestFinalPathSquare2(int index, Color color) {
        super(index, color);
      }

      @Override
      public boolean isOccupied() {
        return true; // Force to return true
      }
    }

    TestFinalPathSquare2 testSquare2 = new TestFinalPathSquare2(0, Color.RED);

    assertThrows(UnsupportedOperationException.class, () -> testSquare2.landHere(redPiece5));


    // Subclass that overrides isOccupied to return true
    class TestFinalPathSquare3 extends FinalPathSquare {
      public TestFinalPathSquare3(int index, Color color) {
        super(index, color);
      }

      @Override
      public boolean isOccupied() {
        return true; // Force to return true
      }
    }

    TestFinalPathSquare3 testSquare3 = new TestFinalPathSquare3(0, Color.RED);
    Piece redPiece2 = new Piece(Color.RED);
    assertThrows(UnsupportedOperationException.class, () -> testSquare3.landHere(redPiece2));

  }

  @Test
  void isBlocked() {
    // Statement coverage
    FinalPathSquare finalPathSquare = new FinalPathSquare(0, Color.RED);
    assertThrows(AssertionError.class, () -> finalPathSquare.isBlocked(null));
  }

  @Test
  void isShieldSquare() {
    // Equivalence Classes:
    // - FinalPathSquare instances (should return false)

    FinalPathSquare finalPathSquare = new FinalPathSquare(0, Color.BLUE);
    assertFalse(finalPathSquare.isShieldSquare());

    FinalPathSquare finalPathSquare2 = new FinalPathSquare(5, Color.RED);
    assertFalse(finalPathSquare2.isShieldSquare());

    FinalPathSquare finalPathSquare3 = new FinalPathSquare(2, Color.RED);
    assertFalse(finalPathSquare3.isShieldSquare());
  }
}