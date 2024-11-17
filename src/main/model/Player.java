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

  // Methods for selecting Pieces and also to enter Pieces in board (for GameController).
  // This methods will not be tested in the Unit Testing of Player, depends on GameController.

  // Choose a piece to move
  public Piece choosePiece() {
    List<Piece> movablePieces = new ArrayList<>();
    for (Piece piece : pieces) {
      if (!piece.isAtHome() && !piece.hasFinished()) {
        movablePieces.add(piece);
      }
    }

    if (movablePieces.isEmpty()) { // All pieces at home (or blocked).
      return null;
    }

    int choice = 1; // Falta input per escollir
    for (Piece piece : movablePieces) {
      if (piece.getId() == choice) {
        return piece;
      }
    }

    // Invalid input
    return choosePiece();
  }

  // Choose to bring a piece into play or move a piece in the global path
  public boolean chooseToEnterPiece() {
    String choice = "yes"; // Falta input
    return choice.equalsIgnoreCase("yes");
  }
}
