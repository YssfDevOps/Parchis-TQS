package test.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import main.model.Board;
import main.model.Color;
import main.model.Piece;
import main.model.square.FinalPathSquare;
import main.model.square.ShieldSquare;
import main.model.square.Square;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

  @Test
  void getGlobalSquare() {
    Board board = new Board();
    // 1. Get square at position within bounds
    Square square10 = board.getGlobalSquare(10);
    assertNotNull(square10);
    assertEquals(10, square10.getPosition());

    // 2. Get square at negative position, not error position, it just wrap around
    Square squareNegative1 = board.getGlobalSquare(-1);
    assertNotNull(squareNegative1);
    assertEquals(67, squareNegative1.getPosition());

    // 3. Position exceeding board size, not error position, it just wrap around
    Square square68 = board.getGlobalSquare(68);
    assertNotNull(square68);
    assertEquals(0, square68.getPosition());
  }

  @Test
  void getPlayerStartSquare() {
    Board board = new Board();
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

    // 3. Get start square for GREEN player
    ShieldSquare greenStartSquare = board.getPlayerStartSquare(Color.GREEN);
    assertNotNull(greenStartSquare);
    assertEquals(55, greenStartSquare.getPosition());
    assertTrue(greenStartSquare.isShieldSquare());
  }

  @Test
  void getPlayerFinalPath() {
    Board board = new Board();

    // Case 1: Get final path for GREEN player
    List<FinalPathSquare> greenFinalPath = board.getPlayerFinalPath(Color.GREEN);
    assertNotNull(greenFinalPath);
    assertEquals(8, greenFinalPath.size());

    // Case 2: Check that all squares in the final path have the correct color
    for (FinalPathSquare square : greenFinalPath) {
      assertEquals(Color.GREEN, square.getColor());
    }
  }

  @Test
  void getNextSquare() {
    Board board = new Board();

    // Case 1: Get next square on global path
    Square currentSquare = board.getGlobalSquare(10);
    Piece redPiece = new Piece(Color.RED);
    Square nextSquare = board.getNextSquare(currentSquare, redPiece);
    assertNotNull(nextSquare);
    assertEquals(11, nextSquare.getPosition());

    // Case 2: Get next square when transitioning to final path
    // For RED player, final entry position is at (38 - 5 + 68) % 68 = 33
    Square square33 = board.getGlobalSquare(32);
    currentSquare = square33;
    nextSquare = board.getNextSquare(currentSquare, redPiece);
    assertTrue(nextSquare instanceof FinalPathSquare);

    // Case 3: Move within final path
    currentSquare = nextSquare; // Now on first square of final path
    nextSquare = board.getNextSquare(currentSquare, redPiece);
    assertTrue(nextSquare instanceof FinalPathSquare);
    assertEquals(1, ((FinalPathSquare) nextSquare).getIndex());

    // Case 4: Reaching the end of the final path
    currentSquare = board.getPlayerFinalPath(Color.RED).get(7); // Last square in final path
    nextSquare = board.getNextSquare(currentSquare, redPiece);
    assertNull(nextSquare);
  }
}