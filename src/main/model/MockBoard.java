package main.model;

import main.model.square.MockSquare;
import main.model.square.RegularSquare;
import main.model.square.Square;

public class MockBoard extends Board {
  MockSquare nextSquare;
  MockSquare prevSquare = null;
  boolean nextNext = false;

  public MockBoard() {
    super();
  }

  @Override
  public Square getNextSquare(Square currentSquare, Piece piece) {
    if (nextNext) {
      nextNext = false;
      return (RegularSquare) prevSquare;
    } else {
      return (RegularSquare) nextSquare;
    }
  }

  public void setNextSquare(MockSquare nextSquare, MockSquare prevSquare) {
    this.nextSquare = nextSquare;

    if (prevSquare != null) {
      this.nextNext = true;
      this.prevSquare = prevSquare;
    }
  }
}