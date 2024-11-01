package main.model.square;

import main.model.Piece;

public interface ISquare {
  public void enter(Piece piece);
  public void leave(Piece piece);
  public boolean isHomeSquare();
  public boolean isGoalSquare();
  public boolean isShieldSquare();
  public boolean isLastGlobalSquare();
  public Square moveAndLand(Piece piece, int moves);
  public Square landHereSendHome();
  public boolean isEmpty();
  void sendPieceHome(Piece piece);
  boolean isPlayerStartSquare();
}
