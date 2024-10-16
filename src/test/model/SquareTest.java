package test.model;

import main.model.Board;
import main.model.Square;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

  @org.junit.jupiter.api.BeforeEach
  void setUp() {
  }

  @org.junit.jupiter.api.Test
  void getPosition() {
    Board board = new Board();
    Square square = new Square(4, board);
    assertEquals(4, square.getPosition());

    Square square2 = new Square(6, board);
    assertEquals(6, square2.getPosition());
  }

  @org.junit.jupiter.api.Test
  void isOccupied() {

  }

  @org.junit.jupiter.api.Test
  void moveAndLand() {
  }

  @org.junit.jupiter.api.Test
  void isLastSquare() {
  }

  @org.junit.jupiter.api.Test
  void enter() {
  }

  @org.junit.jupiter.api.Test
  void leave() {
  }

  @org.junit.jupiter.api.Test
  void landHereOrSendHome() {
  }

  @org.junit.jupiter.api.Test
  void findFirstSquare() {
  }

  @org.junit.jupiter.api.Test
  void findRelativeSquare() {
  }
}