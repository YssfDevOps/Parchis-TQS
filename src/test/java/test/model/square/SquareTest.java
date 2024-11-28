package test.model.square;

import main.model.Board;
import main.model.Color;
import main.model.Piece;
import main.model.Player;
import main.model.square.MockSquare;
import main.model.square.RegularSquare;
import main.model.square.ShieldSquare;
import main.model.square.Square;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    // Statement coverage
    // Subclass of Square with methods to manipulate piece
    class TestSquare extends Square {
      public TestSquare(int position) {
        super(position);
      }

      @Override
      protected void handleLandingOnShieldSquare(Piece piece) {}

      @Override
      protected void handleLandingOnRegularSquare(Piece piece) {}

      @Override
      public boolean isBlocked(Piece piece) {
        return false;
      }

      @Override
      public boolean isShieldSquare() {
        return false;
      }

      // Method for invariant
      public void setPiecesToNull() {
        this.pieces = null;
      }

      // Method for invariant
      public void addNullToPieces() {
        this.pieces.add(null);
      }

      public void newListOfPieces() {
        this.pieces = new ArrayList<>();
      }
    }

    TestSquare testSquare = new TestSquare(10);
    testSquare.setPosition(-1);
    assertThrows(AssertionError.class, testSquare::invariant);

    testSquare.setPosition(10);

    testSquare.setPiecesToNull();
    assertThrows(AssertionError.class, testSquare::invariant);

    // Initialize pieces list again to avoid NullPointerException
    testSquare.newListOfPieces();

    // Test when pieces contain null elements
    testSquare.addNullToPieces();
    assertThrows(AssertionError.class, testSquare::invariant);
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
    List<Piece> pieces = squareWithOwnPiece.getPieces();
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

    // Statement coverage
    // Test assertion failure when piece is null
    Square square5 = new RegularSquare(10);
    assertThrows(AssertionError.class, () -> square5.landHere(null));

    // Test landHere on ShieldSquare when isOccupied == true and isShieldSquare == true
    ShieldSquare shieldSquare = new ShieldSquare(15);
    Piece piece6 = new Piece(Color.RED);
    shieldSquare.landHere(piece6); // Now shieldSquare is occupied

    Piece piece7 = new Piece(Color.BLUE);
    shieldSquare.landHere(piece7); // Should invoke handleLandingOnShieldSquare(piece2)

    assertTrue(shieldSquare.isOccupied());
    assertEquals(shieldSquare, piece7.getSquare());


    // Decision coverage ----------------------------------------------------------------

    MockSquare square_dc = new MockSquare(1);
    Piece piece_dc = new Piece(Color.GREEN);
    Piece piece2_dc = new Piece(Color.BLUE);
    Piece piece3_dc = new Piece(Color.BLUE);

    // Case 1: isOccupied() returns true
    // Simulates that the square is occupied (piece 2 eat piece 1)
    square_dc.landHere(piece_dc);
    assertTrue(square_dc.getPieces().contains(piece_dc), "Case 1: The piece 1 must land in this square.");
    square_dc.setOccupied(true); // The square is occupied
    square_dc.landHere(piece2_dc);
    assertFalse(square_dc.getPieces().contains(piece_dc), "Case 1: The piece 1 must be dead.");
    assertTrue(square_dc.getPieces().contains(piece2_dc), "Case 1: The piece 2 must land in this square.");

    // Leave the piece
    square_dc.leave(piece2_dc);

    // Case 2: isOccupied() returns false
    // Simulates that the square is not occupied (can land here)
    square_dc.setOccupied(false); // The square is not occupied
    square_dc.landHere(piece_dc);
    assertTrue(square_dc.getPieces().contains(piece_dc), "Case 2: The piece must be in this square.");

    // Leave the piece
    square_dc.leave(piece_dc);

    // Case 3: isShieldSquare() returns false
    // Simulates that the square is a ShieldSquare, and it is occupied by a piece from a differents color
    square_dc.setShieldSquare(false);
    square_dc.setOccupied(true);
    square_dc.landHere(piece_dc);
    square_dc.landHere(piece2_dc);
    assertFalse(square_dc.getPieces().contains(piece_dc), "Case 3: The piece 1 must be dead.");
    assertTrue(square_dc.getPieces().contains(piece2_dc), "Case 3: The piece 2 must land in this square.");

    // Leave the piece
    square_dc.leave(piece2_dc);

    // Case 4: isShieldSquare() returns true
    // Simulates that the square is a ShieldSquare, and it is occupied by a piece from a differents color
    square_dc.setShieldSquare(true);
    square_dc.landHere(piece2_dc);
    square_dc.landHere(piece3_dc);
    assertTrue(square_dc.getPieces().contains(piece2_dc), "Case 4: The piece 2 must be in this square.");
    assertTrue(square_dc.getPieces().contains(piece3_dc), "Case 4: The piece 3 must be in this square.");


    // Path coverage ------------------------------------------------------------------------------------------

    // Set up
    Board board_pc = mock(Board.class);
    Piece piece1_pc = mock(Piece.class);
    Piece piece2_pc = mock(Piece.class);
    Piece piece3_pc = mock(Piece.class);
    Piece piece4_pc = mock(Piece.class);
    Player player_pc = new Player("Player1", Color.RED, board_pc);
    player_pc.setPieces(List.of(piece1_pc, piece2_pc, piece3_pc, piece4_pc)); // with test method
    Square square_pc = mock(Square.class);

    // Case 1: Square is empty (piece lands here)
    when(square_pc.isOccupied()).thenReturn(false);

    square_pc.landHere(piece1_pc);

    // Verify that the piece is added to the square and its position is updated
    verify(piece1_pc).setSquare(square_pc);

    // Case 2: Square is occupied and is a shield square
    when(square_pc.isOccupied()).thenReturn(true);
    when(square_pc.isShieldSquare()).thenReturn(true);

    square_pc.landHere(piece2_pc);

    // Verify that the piece is added to the square and its position is updated
    verify(piece2_pc).setSquare(square_pc);


    // Case 3: Square is occupied and is a regular square
    when(square_pc.isOccupied()).thenReturn(true);
    when(square_pc.isShieldSquare()).thenReturn(false);

    square.landHere(piece3_pc);

    // Verify that the piece is added to the square and its position is updated
    verify(piece3_pc).setSquare(square_pc);

    // Case 4: Null piece
    assertThrows(AssertionError.class, () -> square_pc.landHere(null));
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

    // Statement coverage
    // Test assertion failure when piece is null
    Square square5 = new RegularSquare(10);
    assertThrows(AssertionError.class, () -> square5.leave(null));
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

    // Statement coverage

    // Test toString for ShieldSquare with no pieces
    Square shieldSquare = new ShieldSquare(15);
    String expectedShieldSquare = "Square 15 (Shield Square)";
    assertEquals(expectedShieldSquare, shieldSquare.toString());

    // Test toString for ShieldSquare with pieces
    Piece piece1 = new Piece(Color.BLUE);
    shieldSquare.landHere(piece1);
    String expectedShieldSquareWithPiece = "Square 15 (Shield Square) - Pieces: BLUE " + piece1.getId();
    assertEquals(expectedShieldSquareWithPiece, shieldSquare.toString());

    // Test toString for RegularSquare with no pieces
    Square regularSquare = new RegularSquare(20);
    String expectedRegularSquare = "Square 20";
    assertEquals(expectedRegularSquare, regularSquare.toString());
  }

  @Test
  void landHere_pairwiseTesting() {
    // Pairwise Testing for landHere method

    // 1. RegularSquare, Empty
    // Square Type (S): RegularSquare (RS)
    // Occupancy Status (O): Empty (E)

    RegularSquare square1 = new RegularSquare(10);
    Piece piece1 = new Piece(Color.RED);
    assertFalse(square1.isOccupied());
    square1.landHere(piece1);

    assertTrue(square1.isOccupied());
    assertEquals(1, square1.getPieces().size());
    assertEquals(piece1, square1.getPieces().get(0));
    assertEquals(square1, piece1.getSquare());

    // 2. RegularSquare, Occupied by Own Piece
    // Square Type (S): RegularSquare (RS)
    // Occupancy Status (O): Occupied by Own Piece (Own)

    RegularSquare square2 = new RegularSquare(11);
    Piece piece2_1 = new Piece(Color.RED);
    Piece piece2_2 = new Piece(Color.RED);

    square2.landHere(piece2_1);

    assertTrue(square2.isOccupied());
    assertEquals(1, square2.getPieces().size());

    square2.landHere(piece2_2);

    assertEquals(2, square2.getPieces().size());
    assertTrue(square2.getPieces().contains(piece2_1));
    assertTrue(square2.getPieces().contains(piece2_2));

    // 3. RegularSquare, Occupied by Opponent's Piece
    // Square Type (S): RegularSquare (RS)
    // Occupancy Status (O): Occupied by Opponent (Opponent)

    RegularSquare square3 = new RegularSquare(12);
    Piece opponentPiece3 = new Piece(Color.BLUE);
    Piece movingPiece3 = new Piece(Color.RED);

    square3.landHere(opponentPiece3);
    assertTrue(square3.isOccupied());
    assertEquals(1, square3.getPieces().size());
    square3.landHere(movingPiece3);

    assertTrue(opponentPiece3.isAtHome());
    assertNull(opponentPiece3.getSquare());
    assertEquals(1, square3.getPieces().size());
    assertEquals(movingPiece3, square3.getPieces().get(0));

    // 4. RegularSquare, Blockage
    // Square Type (S): RegularSquare (RS)
    // Occupancy Status (O): Blockage (Own Pieces)

    RegularSquare square4 = new RegularSquare(13);
    Piece piece4_1 = new Piece(Color.RED);
    Piece piece4_2 = new Piece(Color.RED);
    Piece movingPiece4 = new Piece(Color.RED);

    square4.landHere(piece4_1);
    square4.landHere(piece4_2);
    assertFalse(square4.isBlocked(movingPiece4));

    square4.landHere(movingPiece4);
    assertEquals(2, square4.getPieces().size());
    assertFalse(square4.getPieces().contains(movingPiece4));

    // Test Case 5: ShieldSquare, Empty
    // Square Type (S): ShieldSquare (SS)
    // Occupancy Status (O): Empty (E)
    ShieldSquare square5 = new ShieldSquare(14);
    Piece piece5 = new Piece(Color.RED);
    assertFalse(square5.isOccupied());
    square5.landHere(piece5);
    assertTrue(square5.isOccupied());

    assertEquals(1, square5.getPieces().size());
    assertEquals(piece5, square5.getPieces().get(0));
    assertEquals(square5, piece5.getSquare());

    // Test Case 6: ShieldSquare, Occupied by Own Piece
    // Square Type (S): ShieldSquare (SS)
    // Occupancy Status (O): Occupied by Own Piece (Own)

    ShieldSquare square6 = new ShieldSquare(15);
    Piece piece6_1 = new Piece(Color.RED);
    Piece piece6_2 = new Piece(Color.RED);
    square6.landHere(piece6_1);

    assertTrue(square6.isOccupied());
    assertEquals(1, square6.getPieces().size());

    square6.landHere(piece6_2);

    assertEquals(2, square6.getPieces().size());
    assertTrue(square6.getPieces().contains(piece6_1));
    assertTrue(square6.getPieces().contains(piece6_2));

    // Test Case 7: ShieldSquare, Occupied by Opponent's Piece
    // Square Type (S): ShieldSquare (SS)
    // Occupancy Status (O): Occupied by Opponent (Opponent)

    ShieldSquare square7 = new ShieldSquare(16);
    Piece opponentPiece7 = new Piece(Color.BLUE);
    Piece movingPiece7 = new Piece(Color.RED);
    square7.landHere(opponentPiece7);

    assertTrue(square7.isOccupied());
    assertEquals(1, square7.getPieces().size());

    square7.landHere(movingPiece7);

    assertEquals(2, square7.getPieces().size());
    assertTrue(square7.getPieces().contains(opponentPiece7));
    assertTrue(square7.getPieces().contains(movingPiece7));

    // Test Case 8: ShieldSquare, Blockage
    // Square Type (S): ShieldSquare (SS)
    // Occupancy Status (O): Blockage (Own Pieces)

    ShieldSquare square8 = new ShieldSquare(17);
    Piece piece8_1 = new Piece(Color.RED);
    Piece piece8_2 = new Piece(Color.RED);
    Piece movingPiece8 = new Piece(Color.RED);

    square8.landHere(piece8_1);
    square8.landHere(piece8_2);
    assertTrue(square8.isBlocked(movingPiece8));
    square8.landHere(movingPiece8);

    assertEquals(2, square8.getPieces().size());
    assertFalse(square8.getPieces().contains(movingPiece8));
  }
}