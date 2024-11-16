package test.model;

import main.model.Board;
import main.model.Color;
import main.model.Piece;
import main.model.Player;
import main.model.square.Square;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

  @Test
  void movePiece() {
    // 1. Move a piece on the board (regular movement)
    Board board = new Board();
    Player player = new Player("Lucia", Color.RED, board);
    Piece piece = player.getPieces().get(0);
    piece.enterGame(board);
    player.movePiece(piece, 3, board); // Move 3 steps forward.
    assertNotNull(piece.getSquare());

    // 2. Form a blockage and try to pass it (you won't be able to :D)
    Square currentSquare = piece.getSquare();
    Square blockageSquare = board.getNextSquare(currentSquare, piece);
    Piece blockingPiece1 = new Piece(Color.BLUE);
    Piece blockingPiece2 = new Piece(Color.BLUE);
    blockageSquare.landHere(blockingPiece1);
    blockageSquare.landHere(blockingPiece2); // Forms a blockage

    player.movePiece(piece, 1, board);
    assertEquals(currentSquare, piece.getSquare());
  }

  @Test
  void enterPieceIntoGame() {
    // 1. Enter a piece into the game when pieces are at home
    Board board = new Board();
    Player player = new Player("Youssef", Color.BLUE, board);
    boolean entered = player.enterPieceIntoGame();
    assertTrue(entered);
    long atHomeCount = player.getPieces().stream().filter(Piece::isAtHome).count();
    assertEquals(3, atHomeCount);

    // 2. Attempt to enter a piece when all pieces are already in the game or finished
    while (player.hasPiecesAtHome()) {
      player.enterPieceIntoGame();
    }
    boolean cannotEnter = !player.enterPieceIntoGame();
    assertTrue(cannotEnter);
  }

  @Test
  void isWinner() {
    // 1. Player has not won when not all pieces have finished
    Board board = new Board();
    Player player = new Player("Lucia", Color.GREEN, board);
    assertFalse(player.isWinner());

    // 2. Player has won when all pieces have finished
    for (Piece piece : player.getPieces()) {
      piece.setHasFinished(true);
    }
    assertTrue(player.isWinner());
  }

  @Test
  void hasPiecesAtHome() {
    // 1. All pieces are at home at start
    Board board = new Board();
    Player player = new Player("Youssef", Color.YELLOW, board);
    assertTrue(player.hasPiecesAtHome());

    // 2. No pieces at home after entering all pieces into the game
    while (player.hasPiecesAtHome()) {
      player.enterPieceIntoGame();
    }
    assertFalse(player.hasPiecesAtHome());
  }

  @Test
  void hasPiecesOnBoard() {
    // 1. No pieces on board at start
    Board board = new Board();
    Player player = new Player("Lucia", Color.RED, board);
    assertFalse(player.hasPiecesOnBoard());

    // 2. Pieces on board after entering a piece into the game
    player.enterPieceIntoGame();
    assertTrue(player.hasPiecesOnBoard());

    // 3. No pieces on board after all have finished
    for (Piece piece : player.getPieces()) {
      piece.setHasFinished(true);
    }
    assertFalse(player.hasPiecesOnBoard());
  }

  @Test
  void getPieces() {
    // 1. Player should have 4 pieces
    Board board = new Board();
    Player player = new Player("Frank", Color.BLUE, board);
    List<Piece> pieces = player.getPieces();
    assertEquals(4, pieces.size());

    // 2. Pieces should have the player's color
    for (Piece piece : pieces) {
      assertEquals(player.getColor(), piece.getColor());
    }
  }

  @Test
  void getColor() {
    // 1. Get player's color
    Board board = new Board();
    Player player = new Player("Youssef", Color.GREEN, board);
    assertEquals(Color.GREEN, player.getColor());

    Player player2 = new Player("Lucia", Color.BLUE, board);
    assertEquals(Color.BLUE, player2.getColor());

    Player player3 = new Player("Youssef-2", Color.RED, board);
    assertEquals(Color.RED, player3.getColor());

    Player player4 = new Player("Lucia-2", Color.YELLOW, board);
    assertEquals(Color.YELLOW, player4.getColor());
  }

  @Test
  void getName() {
    // Case 1: Get player's name
    Board board = new Board();
    Player player = new Player("Youssef", Color.YELLOW, board);
    assertEquals("Youssef", player.getName());

    Player player2 = new Player("Lucia", Color.YELLOW, board);
    assertEquals("Lucia", player2.getName());
  }
}