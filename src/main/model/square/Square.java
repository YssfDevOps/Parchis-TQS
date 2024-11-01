package main.model.square;

import main.model.Board;
import main.model.Piece;
import main.model.Player;

public abstract class Square {
  protected int position;
  protected Board board;
  protected Player player;
  protected Piece piece;

  public Square(int position, Board board) {
    this.position = position;
    this.board = board;
  }

  public void enter(Piece piece) {

  }

  public void leave(Piece piece) {

  }

  public boolean isHomeSquare() {
    return false;
  }

  public boolean isGoalSquare() {
    return false;
  }

  public boolean isShieldSquare() {
    return false;
  }

  public boolean isLastGlobalSquare() {
    return false;
  }

  public Square moveAndLand(Piece piece, int moves) {

    return null;
  }

  public Square landHereSendHome() {
    return null;
  }

  public boolean isEmpty() {
    return piece == null;
  }

  public void sendPieceHome(Piece piece) {

  }

  public boolean isPlayerStartSquare() {
    return false;
  }
}
