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
  }

  @Test
  void isShieldSquare() {
    // 1. Check if each Shield Square are in fact Shield Square.
    ShieldSquare shieldSquare = new ShieldSquare(5);
    assertTrue(shieldSquare.isShieldSquare());

    ShieldSquare shieldSquare2 = new ShieldSquare(10);
    assertTrue(shieldSquare2.isShieldSquare());

    ShieldSquare shieldSquare3 = new ShieldSquare(12);
    assertTrue(shieldSquare3.isShieldSquare());
  }
}