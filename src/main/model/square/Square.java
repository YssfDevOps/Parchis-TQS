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
    if (piece == null) {
      throw new IllegalArgumentException("Piece cannot be null");
    }
    this.piece = piece;
  }

  public void leave(Piece piece) {
    if (piece == null || this.piece != piece) {
      throw new IllegalArgumentException("Invalid piece");
    }
    this.piece = null;
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
    if (piece == null || moves < 0) {
      throw new IllegalArgumentException("Invalid piece or moves");
    }
    // Logic to move piece across the board
    return null;
  }

  public Square landHereSendHome() {
    if (!isEmpty()) {
      Piece currentPiece = this.piece;
      sendPieceHome(currentPiece);
      return this;
    }
    return null;
  }

  public boolean isEmpty() {
    return piece == null;
  }

  public void sendPieceHome(Piece piece) {
    if (piece == null) {
      throw new IllegalArgumentException("Piece cannot be null");
    }
    piece.sendHome(); // Assuming Piece has a sendHome() method
  }

  public boolean isPlayerStartSquare() {
    return false;
  }
}
