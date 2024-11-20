package main.model;

import main.model.square.FinalPathSquare;
import main.model.square.Square;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
  private String name;
  private List<Piece> pieces; // 4 pieces per player
  private Color color; // unique color for player
  private boolean winner;
  private Board board;
  private Scanner scanner = new Scanner(System.in);

  public Player(String name, Color color, Board board) {
    // Preconditions
    assert name != null && !name.isEmpty() : "Name cannot be null or empty";
    assert color != null : "Color cannot be null";
    assert board != null : "Board cannot be null";

    this.name = name;
    this.color = color;
    this.winner = false;
    this.board = board; // Set the Board instance
    this.pieces = new ArrayList<>();

    // Initialize the player's four pieces
    for (int i = 0; i < 4; i++) {
      pieces.add(new Piece(color));
    }

    // Postcondition
    assert pieces.size() == 4 : "Player must have 4 pieces initialized";

    // Invariant
    invariant();
  }

  private void invariant() {
    assert pieces.size() == 4 : "Player must always have exactly 4 pieces";
    assert name != null && !name.isEmpty() :
            "Player name must not be null or empty";
  }


  // Move a piece by a certain number of moves
  public void movePiece(Piece piece, int moves, Board board) {
    // Preconditions
    assert piece != null : "Piece cannot be null";
    assert moves > 0 : "Moves must be positive";
    assert moves <= 6 : "Moves must be less than 6";
    assert board != null : "Board cannot be null";
    assert pieces.contains(piece) : "Piece must belong to the player";

    Square currentSquare = piece.getSquare();
    Square lastAccessibleSquare = currentSquare;

    for (int i = 0; i < moves; i++) {
      Square nextSquare = board.getNextSquare(lastAccessibleSquare, piece);

      if (nextSquare == null) {
        // Reached the end of final path
        piece.setHasFinished(true);
        currentSquare.leave(piece);
        piece.setSquare(null);
        return;
      }

      if (nextSquare.isBlocked(piece)) {
        // Cannot pass through blockage, stop to square behind
        break;
      }

      // Update the last accessible square
      lastAccessibleSquare = nextSquare;
    }

    if (lastAccessibleSquare != currentSquare) {
      // Move the piece to the last accessible square
      currentSquare.leave(piece);
      lastAccessibleSquare.landHere(piece);
      piece.setSquare(lastAccessibleSquare);
    } else {
      // Could not move due to blockage; stay on the same square
      System.out.println(piece.getColor() + " Piece " + piece.getId() + " is blocked.");
    }

    // Invariant
    invariant();
  }

  public boolean enterPieceIntoGame() {
    for (Piece piece : pieces) {
      if (piece.isAtHome()) {
        piece.enterGame(board);
        // Postcondition: Piece might not enter game if start square is blocked
        assert piece.isAtHome() || piece.getSquare() != null :
            "Piece must be on a square if not at home";
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

    // Postcondition
    assert winner : "Winner must be true when all pieces are finished";
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
    // Precondition: List of pieces must contain exactly 4 elements
    assert pieces.size() == 4 : "Player must always have 4 pieces";
    return pieces;
  }

  public Color getColor() {
    // Precondition: Returned color must not be null
    assert color != null : "Player color must not be null";
    return color;
  }

  public String getName() {
    // Precondition: Returned name must not be null or empty
    assert name != null && !name.isEmpty() : "Player name must not be null or empty";
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

    if (movablePieces.isEmpty()) {
      System.out.println("No movable pieces available.");
      return null;
    }

    System.out.println("Choose a piece to move:");
    for (Piece piece : movablePieces) {
      System.out.println("[" + piece.getId() + "] " + piece.toString());
    }

    int choice = scanner.nextInt();
    for (Piece piece : movablePieces) {
      if (piece.getId() == choice) {
        return piece;
      }
    }

    System.out.println("Invalid choice. Please try again.");
    return choosePiece();
  }

  // Choose to bring a piece into play or move a piece in the global path
  public boolean chooseToEnterPiece() {
    System.out.println("You rolled a 5. Do you want to bring a piece into play? (yes/no)");
    String choice = scanner.next();
    return choice.equalsIgnoreCase("yes");
  }

  public void displayPieces() {
    System.out.println("Pieces for " + name + " (" + color + "):");
    for (Piece piece : pieces) {
      String location;
      if (piece.isAtHome()) {
        location = "at home";
      } else if (piece.hasFinished()) {
        location = "has finished";
      } else {
        Square square = piece.getSquare();
        String squareType = square.isShieldSquare() ? "Shield Square" : "Regular Square";
        if (square instanceof FinalPathSquare) {
          location = "on final path at position " + (((FinalPathSquare) square).getIndex() + 1) + " (" + squareType + ")";
        } else {
          location = "on global path at position " + (square.getPosition() + 1) + " (" + squareType + ")";
        }
      }
      System.out.println(" - Piece " + piece.getId() + " is " + location);
    }
  }
}