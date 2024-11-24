package test.model;

import main.model.*;
import main.model.square.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

  // For testing purposes
  public void leaveAllPieces(List<Piece> pieces) {
    pieces.clear();
  }

  @Test
  void movePiece() {
    // Statement coverage (already covered all the statements with the tests already done).

    // Different cases:
    // - Moving a piece normally (no blockage, not finishing).
    // - Moving a piece that finishes the game.
    // - Moving a piece that is blocked and cannot move.
    // - Attempt to move a piece that is at home (should fail).
    // - Attempt to move a piece that has finished (should fail).
    // - Moves is zero or negative (should fail).

    Board board = new Board();
    Player player = new Player("Lucia", Color.RED, board);
    Piece piece = player.getPieces().get(0);

    // 1. Moving a piece normally
    ShieldSquare startSquare = board.getPlayerStartSquare(Color.RED);
    leaveAllPieces(startSquare.getPieces());
    piece.enterGame(board);
    player.movePiece(piece, 3, board); // Move 3 steps forward.

    // Check that the piece has moved
    assertNotNull(piece.getSquare());
    assertNotEquals(startSquare, piece.getSquare());

    // 2. Moving a piece that finishes the game
    // For simplicity, we'll move the piece to the last square
    Square s = new FinalPathSquare(7, Color.RED);
    s.landHere(piece); // Simulate near the end
    player.movePiece(piece, 1, board); // Move into the finish

    assertTrue(piece.hasFinished());
    assertNull(piece.getSquare());

    // 3. Moving a piece that is blocked and cannot move
    Piece piece2 = player.getPieces().get(1);
    piece2.enterGame(board);
    Square currentSquare = piece2.getSquare();

    // Create a blockage ahead
    Square blockageSquare = board.getNextSquare(currentSquare, piece2);
    leaveAllPieces(blockageSquare.getPieces());
    Piece blockingPiece1 = new Piece(Color.BLUE);
    Piece blockingPiece2 = new Piece(Color.BLUE);
    blockageSquare.landHere(blockingPiece1);
    blockageSquare.landHere(blockingPiece2); // Forms a blockage

    player.movePiece(piece2, 1, board);
    assertEquals(currentSquare, piece2.getSquare());

    // 4. Move a piece that is at home (should fail)
    Piece atHomePiece = player.getPieces().get(2);
    assertTrue(atHomePiece.isAtHome());
    assertThrows(AssertionError.class, () -> player.movePiece(atHomePiece, 1, board));

    // 5. Move a piece that has finished (should fail)
    assertThrows(AssertionError.class, () -> player.movePiece(piece, 1, board));

    // 6. Move a piece with zero or negative moves (should fail)
    Piece piece3 = player.getPieces().get(3);
    piece3.enterGame(board);
    assertThrows(AssertionError.class, () -> player.movePiece(piece3, 0, board));
    assertThrows(AssertionError.class, () -> player.movePiece(piece3, -1, board));

    // Equivalence Partitions for amount of moves:
    // - Invalid moves: moves < 1 (should fail)
    // - Valid moves: moves >= 1 && moves <= 6
    // - Invalid moves: moves > 6 (should fail)

    // Boundary Values for amount of moves:
    // - moves = -10 (invalid)
    // - moves = 0 (invalid lower boundary)
    // - moves = 2 (valid upper boundary)
    // - moves = 4 (in between)
    // - moves = 5 (valid lower boundary)
    // - moves = 6 (Boundary)
    // - moves = 7 (invalid upper boundary)
    // - moves = 10 (invalid)

    Board board2 = new Board();
    Player player4 = new Player("Lucia", Color.RED, board2);
    Piece piece4 = player4.getPieces().get(0);

    // Set up the piece on the board
    ShieldSquare startSquare3 = board2.getPlayerStartSquare(Color.RED);
    leaveAllPieces(startSquare3.getPieces());
    piece4.enterGame(board2);

    // 1. Move a piece with invalid negative moves (should fail)
    assertThrows(AssertionError.class, () -> player4.movePiece(piece4, -10, board2));
    assertThrows(AssertionError.class, () -> player4.movePiece(piece4, -1, board2));

    // 2. Move a piece with zero moves (should fail)
    assertThrows(AssertionError.class, () -> player4.movePiece(piece4, 0, board2));

    // 3. Moving a piece with moves = 2 (valid lower boundary)
    piece4.sendHome();
    piece4.enterGame(board2);
    Square initialSquare = piece4.getSquare();
    player4.movePiece(piece4, 2, board2);
    assertNotEquals(initialSquare, piece4.getSquare());
    assertNotNull(piece4.getSquare());

    // 4. Moving a piece with moves = 4 (in between valid range)
    piece4.sendHome();
    piece4.enterGame(board2);
    initialSquare = piece4.getSquare();
    player4.movePiece(piece4, 4, board2);
    assertNotEquals(initialSquare, piece4.getSquare());
    assertNotNull(piece4.getSquare());

    // 5. Moving a piece with moves = 5 (valid upper boundary)
    piece4.sendHome();
    piece4.enterGame(board2);
    initialSquare = piece4.getSquare();
    player4.movePiece(piece4, 5, board2);
    assertNotEquals(initialSquare, piece4.getSquare());
    assertNotNull(piece4.getSquare());

    // 6. Moving a piece with moves = 6 (boundary value)
    piece4.sendHome();
    piece4.enterGame(board2);
    initialSquare = piece4.getSquare();
    player4.movePiece(piece4, 6, board2);
    assertNotEquals(initialSquare, piece4.getSquare());
    assertNotNull(piece4.getSquare());

    // 7. Move a piece with moves greater than 6 (should fail)
    assertThrows(AssertionError.class, () -> player4.movePiece(piece4, 7, board2));
    assertThrows(AssertionError.class, () -> player4.movePiece(piece4, 10, board2));

    // 8. Move a piece that is at home (should fail)
    Piece atHomePiece3 = player4.getPieces().get(1);
    assertTrue(atHomePiece3.isAtHome());
    assertThrows(AssertionError.class, () -> player4.movePiece(atHomePiece3, 3, board2));

    // Decision coverage ---------------------------------------------------------------------

    // Create the board, piece, and required squares
    MockBoard board_dc = new MockBoard();
    Player player_dc = new Player("Lucia", Color.BLUE, board_dc);
    Piece piece_dc = player_dc.getPieces().get(0);
    Piece piece2_dc = player_dc.getPieces().get(1);

    MockSquare startSquare_dc = new MockSquare(1);
    MockSquare midSquare_dc = new MockSquare(2);
    MockSquare endSquare_dc = new MockSquare(3);
    MockSquare blockedSquare_dc = new MockSquare(2);
    blockedSquare_dc.setBlocked(true);
    MockSquare nullSquare_dc = null;

    // Case 1: nextSquare == null
    // Simulates that the piece is on a square and the next square is null (end of path)
    midSquare_dc.addPieceDirectly(piece_dc);
    board_dc.setNextSquare(nullSquare_dc, null); // The next square is null
    player_dc.movePiece(piece_dc, 1, board_dc);
    assertTrue(piece_dc.hasFinished(), "Case 1: The piece should be marked as finished");
    assertNull(piece_dc.getSquare(), "Case 1: The piece should have left the board");

    // Case 2: nextSquare.isBlocked(piece) == true
    // Simulates that the next square is blocked
    startSquare_dc.addPieceDirectly(piece2_dc);
    board_dc.setNextSquare(blockedSquare_dc, null); // The next square is the blocked one
    player_dc.movePiece(piece2_dc, 1, board_dc);
    assertTrue(startSquare_dc.getPieces().contains(piece2_dc), "Case 2: The piece should stay in the starting square");

    startSquare_dc.leave(piece2_dc);

    // Case 3: nextSquare.isBlocked(piece) == false
    // Simulates that the piece can move to an unblocked square
    startSquare_dc.addPieceDirectly(piece2_dc);
    board_dc.setNextSquare(midSquare_dc, null); // The next square is accessible
    player_dc.movePiece(piece2_dc, 1, board_dc);
    assertEquals(midSquare_dc, piece2_dc.getSquare(), "Case 3: The piece should move to the next square");

    // Case 4: lastAccessibleSquare != currentSquare
    // Simulates that the piece moves through multiple squares and ends on a different square
    startSquare_dc.addPieceDirectly(piece2_dc);
    board_dc.setNextSquare(endSquare_dc, midSquare_dc); // The piece can move through two squares
    player_dc.movePiece(piece2_dc, 2, board_dc);
    assertEquals(endSquare_dc, piece2_dc.getSquare(), "Case 4: The piece should end on a square different from the start");

    // Case 5: lastAccessibleSquare == currentSquare - TODO:REVISAR
    // Simulates that the piece cannot move due to blockages
    startSquare_dc.addPieceDirectly(piece2_dc);
    board_dc.setNextSquare(blockedSquare_dc, null); // The next square is blocked
    player_dc.movePiece(piece2_dc, 1, board_dc);
    assertEquals(startSquare_dc, piece2_dc.getSquare(), "Case 5: The piece should stay in the starting square");
  }

  @Test
  void enterPieceIntoGame() {
    // Statement coverage (already covered all the statements with the tests already done).

    // Equivalent Partitions:
    // - Entering a piece into the game when start square is unblocked.
    // - Attempt to enter a piece when start square is blocked.
    // - Attempt to enter a piece when all pieces are already in play or finished.

    Board board = new Board();
    Player player = new Player("Youssef", Color.BLUE, board);
    ShieldSquare startSquare = board.getPlayerStartSquare(Color.BLUE);
    leaveAllPieces(startSquare.getPieces());

    // 1. Enter a piece into the game when start square is unblocked
    boolean entered = player.enterPieceIntoGame();
    assertTrue(entered);
    long atHomeCount = player.getPieces().stream().filter(Piece::isAtHome).count();
    assertEquals(3, atHomeCount);

    // 2. Attempt to enter a piece when start square is blocked
    Piece blocker1 = new Piece(Color.BLUE);
    Piece blocker2 = new Piece(Color.BLUE);
    startSquare.landHere(blocker1);
    startSquare.landHere(blocker2); // Forms a blockage
    Piece piece = new Piece(Color.BLUE);
    assertTrue(startSquare.isBlocked(piece));

    // 3. Attempt to enter a piece when all pieces are already in play or finished
    leaveAllPieces(startSquare.getPieces()); // Clear blockage
    player.enterPieceIntoGame();
    player.enterPieceIntoGame();
    leaveAllPieces(startSquare.getPieces());
    player.enterPieceIntoGame();
    player.enterPieceIntoGame();
    assertFalse(player.hasPiecesAtHome());
  }

  @Test
  void isWinner() {
    // Statement coverage (already covered all the statements with the tests already done).

    // Equivalent Partitions:
    // - Player has not won (not all pieces have finished).
    // - Player has won (all pieces have finished).

    Board board = new Board();
    Player player = new Player("Lucia", Color.GREEN, board);

    // 1. Player has not won
    assertFalse(player.isWinner());

    // 2. Player has won when all pieces have finished
    for (Piece piece : player.getPieces()) {
      piece.enterGame(board);
      piece.setAtHome(false); // Simulate faster that the pieces left the first square
      piece.setHasFinished(true);
    }
    assertTrue(player.isWinner());
  }

  @Test
  void hasPiecesAtHome() {
    // Statement coverage (already covered all the statements with the tests already done).

    // Equivalent Partitions:
    // - Player has pieces at home.
    // - Player has no pieces at home.

    Board board = new Board();
    Player player = new Player("Youssef", Color.YELLOW, board);

    // 1. All pieces are at home initially
    assertTrue(player.hasPiecesAtHome());

    // 2. No pieces at home after entering all pieces into the game
    ShieldSquare startSquare = board.getPlayerStartSquare(Color.YELLOW);
    leaveAllPieces(startSquare.getPieces());
    int i = 0;
    while (player.hasPiecesAtHome()) {
      i++;
      player.enterPieceIntoGame();
      player.getPieces().get(i).setAtHome(false);
    }
    // Check if all pieces are no longer at home
    assertFalse(player.hasPiecesAtHome());
  }

  @Test
  void hasPiecesOnBoard() {
    // Statement coverage (already covered all the statements with the tests already done).

    // Equivalent Partitions:
    // - Player has no pieces on board.
    // - Player has pieces on board.
    // - Player has all pieces finished.

    Board board = new Board();
    Player player = new Player("Lucia", Color.RED, board);

    // 1. No pieces on board initially
    assertFalse(player.hasPiecesOnBoard());

    // 2. Pieces on board after entering a piece into the game
    ShieldSquare startSquare = board.getPlayerStartSquare(Color.RED);
    leaveAllPieces(startSquare.getPieces());
    player.enterPieceIntoGame();
    assertTrue(player.hasPiecesOnBoard());

    // 3. No pieces on board after all have finished
    for (Piece piece : player.getPieces()) {
      piece.setAtHome(false);// Simulate faster that the pieces left the first square
      piece.setHasFinished(true);
    }
    assertFalse(player.hasPiecesOnBoard());
  }

  @Test
  void getPieces() {
    // Statement coverage (already covered all the statements with the tests already done).

    // Equivalent Partitions:
    // - Player has exactly 4 pieces.
    // - All pieces belong to the player.

    Board board = new Board();
    Player player = new Player("Lucia", Color.BLUE, board);
    List<Piece> pieces = player.getPieces();
    assertEquals(4, pieces.size());

    // All pieces should have the player's color
    for (Piece piece : pieces) {
      assertEquals(player.getColor(), piece.getColor());
    }
  }

  @Test
  void getColor() {
    // Statement coverage (already covered all the statements with the tests already done).

    // Equivalent partition:
    // - Player's color is correctly assigned and gotten.

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
    // Statement coverage (already covered all the statements with the tests already done).

    // Equivalent Partitions:
    // - Player's name is correctly assigned and gotten.
    // - Player's name null.
    // - Player's name empty.

    Board board = new Board();
    Player player1 = new Player("Youssef", Color.YELLOW, board);
    assertEquals("Youssef", player1.getName());

    Player player2 = new Player("Lucia", Color.YELLOW, board);
    assertEquals("Lucia", player2.getName());

    // Attempt to create a player with null name (should fail)
    assertThrows(AssertionError.class, () -> new Player(null, Color.RED, board));

    // Attempt to create a player with empty name (should fail)
    assertThrows(AssertionError.class, () -> new Player("", Color.RED, board));
  }
}
