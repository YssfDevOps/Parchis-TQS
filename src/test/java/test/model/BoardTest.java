package test.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import main.model.Board;
import main.model.Color;
import main.model.Piece;
import main.model.square.FinalPathSquare;
import main.model.square.ShieldSquare;
import main.model.square.Square;

import java.util.List;

class BoardTest {

  @Test
  void getGlobalSquare() {
    // Statement coverage (already covered all the statements with the tests already done).

    // Equivalence Partitions:
    // - Invalid positions: position < 0 (should fail)
    // - Valid positions: position >= 0 && position < 68
    // - Invalid positions: position > 68 (should fail)

    // Boundary Values:
    // - position = 0
    // - position = NUM_SQUARES - 1 (67)
    // - position = -1 (invalid)
    // - position = NUM_SQUARES (68), (invalid)
    // - position = 1 (valid)
    // - position = 66
    // - position = -10 (invalid)
    // - position = 100 (invalid)

    Board board = new Board();
    // 1. Get square at position within bounds

    // Boundary (Valid)
    Square square0 = board.getGlobalSquare(0);
    assertNotNull(square0);
    assertEquals(0, square0.getPosition());

    // In between (valid)
    Square square20 = board.getGlobalSquare(20);
    assertNotNull(square20);
    assertEquals(20, square20.getPosition());

    // Boundary (Valid)
    Square square67 = board.getGlobalSquare(67);
    assertNotNull(square67);
    assertEquals(67, square67.getPosition());

    // Boundary Upper (Valid)
    Square square1 = board.getGlobalSquare(1);
    assertNotNull(square1);
    assertEquals(1, square1.getPosition());

    // Boundary Lower (Valid)
    Square square66 = board.getGlobalSquare(66);
    assertNotNull(square66);
    assertEquals(66, square66.getPosition());

    // 2. Attempt to get square at negative position (should fail)
    assertThrows(AssertionError.class, () -> board.getGlobalSquare(-10));
    assertThrows(AssertionError.class, () -> board.getGlobalSquare(-1));

    // 3. Get square at position beyond NUM_SQUARES - 1, (should fail)
    assertThrows(AssertionError.class, () -> board.getGlobalSquare(68));
    assertThrows(AssertionError.class, () -> board.getGlobalSquare(100));
  }

  @Test
  void getPlayerStartSquare() {
    // Statement coverage (already covered all the statements with the tests already done).

    Board board = new Board();
    // Equivalence Partitions:
    // - Valid colors: RED, BLUE, GREEN, YELLOW
    // - Invalid color: null (should fail)

    // 1. Get start square for RED player
    ShieldSquare redStartSquare = board.getPlayerStartSquare(Color.RED);
    assertNotNull(redStartSquare);
    assertEquals(38, redStartSquare.getPosition());
    assertTrue(redStartSquare.isShieldSquare());

    // 2. Get start square for BLUE player
    ShieldSquare blueStartSquare = board.getPlayerStartSquare(Color.BLUE);
    assertNotNull(blueStartSquare);
    assertEquals(21, blueStartSquare.getPosition());
    assertTrue(blueStartSquare.isShieldSquare());

    // 3. Get start square for YELLOW player
    ShieldSquare yellowStartSquare = board.getPlayerStartSquare(Color.YELLOW);
    assertNotNull(yellowStartSquare);
    assertEquals(4, yellowStartSquare.getPosition());
    assertTrue(yellowStartSquare.isShieldSquare());

    // 4. Get start square for GREEN player
    ShieldSquare greenStartSquare = board.getPlayerStartSquare(Color.GREEN);
    assertNotNull(greenStartSquare);
    assertEquals(55, greenStartSquare.getPosition());
    assertTrue(greenStartSquare.isShieldSquare());

    // 5. Attempt to get start square with null color (should fail)
    assertThrows(AssertionError.class, () -> board.getPlayerStartSquare(null));
  }

  @Test
  void setPlayerStartSquare() {
    // Statement coverage (already covered all the statements with the tests already done).

    Board board = new Board();
    // Equivalence Partitions:
    // - Valid color and square
    // - Null color (should fail)
    // - Null square (should fail)

    // 1. Set valid start square
    ShieldSquare newStartSquare = new ShieldSquare(10);
    board.setPlayerStartSquare(Color.RED, newStartSquare);
    assertEquals(10, board.getPlayerStartSquare(Color.RED).getPosition());

    // 2. Attempt to set start square with null color (should fail)
    assertThrows(AssertionError.class, () -> board.setPlayerStartSquare(null, newStartSquare));

    // 3. Attempt to set start square with null square (should fail)
    assertThrows(AssertionError.class, () -> board.setPlayerStartSquare(Color.RED, null));
  }

  @Test
  void getPlayerFinalPath() {
    // Statement coverage (already covered all the statements with the tests already done).

    Board board = new Board();
    // Equivalence Partitions:
    // - Valid colors: RED, BLUE, GREEN, YELLOW
    // - Invalid color: null (should fail)

    // 1. Get final path for GREEN player
    List<FinalPathSquare> greenFinalPath = board.getPlayerFinalPath(Color.GREEN);
    assertNotNull(greenFinalPath);
    assertEquals(8, greenFinalPath.size());
    for (FinalPathSquare square : greenFinalPath) {
      assertEquals(Color.GREEN, square.getColor());
    }

    // 2. Attempt to get final path with null color (should fail)
    assertThrows(AssertionError.class, () -> board.getPlayerFinalPath(null));
  }

  @Test
  void getNextSquare() {
    // Statement coverage (already covered all the statements with the tests already done).

    Board board = new Board();
    // Equivalence Partitions:
    // - currentSquare on global path
    // - currentSquare transitioning
    // - currentSquare in final path
    // - reach end of final path
    // - currentSquare is null (should fail)
    // - piece is null (should fail)

    // Case 1: Get next square on global path
    Square currentSquare = board.getGlobalSquare(10);
    Piece redPiece = new Piece(Color.RED);
    Square nextSquare = board.getNextSquare(currentSquare, redPiece);
    assertNotNull(nextSquare);
    assertEquals(11, nextSquare.getPosition());

    // Case 2: Get next square when transitioning to final path
    // For RED player, final entry position is at (38 - 6 + 68) % 68 = 32
    currentSquare = board.getGlobalSquare(32);
    nextSquare = board.getNextSquare(currentSquare, redPiece);
    assertInstanceOf(FinalPathSquare.class, nextSquare);

    // Case 3: Move within final path
    currentSquare = nextSquare; // Now on first square of final path
    nextSquare = board.getNextSquare(currentSquare, redPiece);
    assertInstanceOf(FinalPathSquare.class, nextSquare);
    assertEquals(1, ((FinalPathSquare) nextSquare).getIndex());

    // Case 4: Reaching the end of the final path
    currentSquare = board.getPlayerFinalPath(Color.RED).get(7); // Last square in final path
    nextSquare = board.getNextSquare(currentSquare, redPiece);
    assertNull(nextSquare);

    // 5. Attempt to get next square with null currentSquare (should fail)
    assertThrows(AssertionError.class, () -> board.getNextSquare(null, redPiece));

    // 6. Attempt to get next square with null piece (should fail)
    currentSquare = board.getGlobalSquare(10);
    Square finalCurrentSquare = currentSquare;
    assertThrows(AssertionError.class, () -> board.getNextSquare(finalCurrentSquare, null));
  }
}