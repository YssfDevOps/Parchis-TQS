package main.model;

import main.model.square.FinalPathSquare;
import main.model.square.Square;
import java.util.ArrayList;
import java.util.List;

public class Player {
  private String name;
  private List<Piece> pieces; // 4 pieces per player
  private Color color; // unique color for player
  private boolean winner;
  private Board board;

  public Player(String name, Color color, Board board) {
    this.name = name;
    this.color = color;
    this.winner = false;
    this.board = board; // Set the Board instance
    this.pieces = new ArrayList<>();

    // Initialize the player's four pieces
    for (int i = 0; i < 4; i++) {
      pieces.add(new Piece(color));
    }
  }

  // Move a piece by a certain number of moves
  public void movePiece(Piece piece, int moves, Board board) {

  }

  public boolean enterPieceIntoGame() {
    for (Piece piece : pieces) {
      if (piece.isAtHome()) {
        piece.enterGame(board);
        return true;
      }
    }
    return false;
  }

  // Check if all pieces have finished
  public boolean isWinner() {
    for (Piece piece : pieces) {
      if (!piece.hasFinished()) {
        return false;
      }
    }
    winner = true;
    return true;
  }

  // Check if the player has any pieces at home
  public boolean hasPiecesAtHome() {
    for (Piece piece : pieces) {
      if (piece.isAtHome()) {
        return true;
      }
    }
    return false;
  }

  // Check if the player has any pieces on the board
  public boolean hasPiecesOnBoard() {
    for (Piece piece : pieces) {
      if (!piece.isAtHome() && !piece.hasFinished()) {
        return true;
      }
    }
    return false;
  }

  public List<Piece> getPieces() {
    return pieces;
  }

  public Color getColor() {
    return color;
  }

  public String getName() {
    return name;
  }
}
