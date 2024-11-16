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
  }

  @Test
  void isShieldSquare() {
    FinalPathSquare finalPathSquare = new FinalPathSquare(0, Color.BLUE);
    assertFalse(finalPathSquare.isShieldSquare());

    FinalPathSquare finalPathSquare2 = new FinalPathSquare(5, Color.RED);
    assertFalse(finalPathSquare2.isShieldSquare());

    FinalPathSquare finalPathSquare3 = new FinalPathSquare(2, Color.RED);
    assertFalse(finalPathSquare3.isShieldSquare());
  }
}