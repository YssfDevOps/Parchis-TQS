package main.model;

public class Square {
  protected Player player;
  protected int position;
  protected Board board;
  protected Piece piece;

  public Square(int pos, Board b) {
    position = pos;
    board = b;
  }

  public int getPosition() {
    return position;
  }

  protected boolean isOccupied() {
    return player != null;
  }

  public Square moveAndLand(int moves) {
    return null;
  }

  public boolean isLastSquare() {
    return false;
  }

  public void enter(Player player) {
    this.player = player;
  }

  public void leave(Player p) {
    player = null;
  }

  protected Square landHereOrSendHome() {
    return null;
  }

  protected Square findFirstSquare() {
    return null;
  }

  protected Square findRelativeSquare(int moves) {
    return null;
  }
}
