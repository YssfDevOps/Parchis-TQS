package main.model.square;

import main.model.Board;
import main.model.Piece;

public class ShieldSquare extends Square {
  public ShieldSquare(int position, Board board) {
    super(position, board);
  }

  @Override
  public boolean isShieldSquare() {
    return true;
  }

  @Override
  public boolean isPlayerStartSquare() {
    return position == board.getPlayerStartPosition(player);
  }

  @Override
  public void enter(Piece piece) {
    if (piece == null) {
      throw new IllegalArgumentException("Piece cannot be null");
    }
    this.piece = piece;
  }

  @Override
  public void leave(Piece piece) {
    if (piece == null || this.piece != piece) {
      throw new IllegalArgumentException("Invalid piece");
    }
    this.piece = null;
  }

  @Override
  public Square landHereSendHome() {
    if (!isEmpty()) {
      sendPieceHome(piece);
    }
    return this;
  }
}
