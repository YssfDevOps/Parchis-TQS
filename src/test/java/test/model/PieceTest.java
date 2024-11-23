package test.model;

import main.model.Board;
import main.model.Color;
import main.model.Piece;
import main.model.square.FinalPathSquare;
import main.model.square.RegularSquare;
import main.model.square.ShieldSquare;
import main.model.square.Square;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

  // For testing purposes
  public void leaveAllPieces(List<Piece> pieces) {
    pieces.clear();
  }

  @Test
  void getId() {
    // Statement coverage (already covered all the statements with the tests already done).

    // Equivalent Partitions:
    // - IDs are positive integers and increment sequentially.

    // 1. Check if ID increments
    Piece piece1 = new Piece(Color.RED);
    int id1 = piece1.getId();
    assertTrue(id1 >= 1);

    // 2. Check IDs increment
    Piece piece2 = new Piece(Color.BLUE);
    int id2 = piece2.getId();
    assertEquals(id1 + 1, id2);

    // 3. Check IDs increment
    Piece piece3 = new Piece(Color.GREEN);
    int id3 = piece3.getId();
    assertEquals(id2 + 1, id3);

    // 4. Check IDs increment
    Piece piece4 = new Piece(Color.YELLOW);
    int id4 = piece4.getId();
    assertEquals(id3 + 1, id4);
  }

  @Test
  void isAtHome() {
    // Statement coverage (already covered all the statements with the tests already done).

    // Equivalent Partitions:
    // - Piece is at home.
    // - Piece is not at home.

    // 1. New piece should be at home (start of the game)
    Piece piece = new Piece(Color.RED);
    assertTrue(piece.isAtHome());

    // 2. Enter piece into game, piece should not be at home
    Board board = new Board();
    ShieldSquare startSquare = new ShieldSquare(0);
    board.setPlayerStartSquare(Color.RED, startSquare);
    leaveAllPieces(startSquare.getPieces()); // Custom method to clear pieces for testing

    assertFalse(startSquare.isBlocked(piece));

    piece.enterGame(board);
    assertFalse(piece.isAtHome());
    assertEquals(startSquare, piece.getSquare());

    // 3. Send piece home, piece should be at home again
    piece.sendHome();
    assertTrue(piece.isAtHome());
    assertNull(piece.getSquare());
  }

  @Test
  void sendHome() {
    // Statement coverage (already covered all the statements with the tests already done).

    // Equivalent Partitions:
    // - Sending home a piece that is on the board.
    // - Sending home a piece that is already at home.

    // 1. Send home when piece is on the board
    Piece piece = new Piece(Color.RED);
    Square square = new RegularSquare(10);
    square.landHere(piece);
    piece.setSquare(square);

    piece.sendHome();
    assertTrue(piece.isAtHome());
    assertNull(piece.getSquare());
    assertFalse(square.isOccupied());

    // 2. Send home when piece is already at home
    Piece pieceAtHome = new Piece(Color.BLUE);
    assertTrue(pieceAtHome.isAtHome());
    pieceAtHome.sendHome(); // Should not cause any issues
    assertTrue(pieceAtHome.isAtHome());
    assertNull(pieceAtHome.getSquare());
  }

  @Test
  void sendHome_Mockito() {
    // Testing to send piece back home after being in a square of globalPath:

    Square mockSquare = Mockito.mock(Square.class);
    Piece piece = new Piece(Color.RED);
    piece.setSquare(mockSquare);
    when(mockSquare.getPieces()).thenReturn(List.of(piece));
    doNothing().when(mockSquare).leave(piece);

    // Now sending home
    piece.sendHome();
    verify(mockSquare).leave(piece);

    // Look if it worked
    assertTrue(piece.isAtHome());
    assertNull(piece.getSquare());
  }

  @Test
  void enterGame() {
    // Statement coverage (already covered all the statements with the tests already done).

    // 1. Enter game when start square is NOT blocked
    Piece piece = new Piece(Color.RED);
    Board board = new Board();
    ShieldSquare startSquare = new ShieldSquare(0);
    board.setPlayerStartSquare(Color.RED, startSquare);

    leaveAllPieces(startSquare.getPieces()); // Custom method to clear pieces for testing
    assertFalse(startSquare.isBlocked(piece));
    piece.enterGame(board);
    assertFalse(piece.isAtHome());
    assertEquals(startSquare, piece.getSquare());

    // 2. Attempt to enter game when start square is blocked
    piece.sendHome();
    Piece blockingPiece1 = new Piece(Color.RED);
    Piece blockingPiece2 = new Piece(Color.RED);
    startSquare.landHere(blockingPiece1);
    startSquare.landHere(blockingPiece2); // Forms a blockage

    assertTrue(startSquare.isBlocked(piece));

    Piece piece2 = new Piece(Color.RED);
    piece2.enterGame(board);
    assertTrue(piece2.isAtHome());
    assertNull(piece2.getSquare());

    // Equivalent partitions:
    // - Entering game when start square is unblocked.
    // - Attempting to enter game when start square is blocked.

    Piece piece4 = new Piece(Color.RED);
    Board board2 = new Board();
    ShieldSquare startSquare2 = board2.getPlayerStartSquare(Color.RED);
    leaveAllPieces(startSquare2.getPieces()); // Clear start square

    // 1. Enter game when start square is not blocked
    piece4.enterGame(board2);
    assertFalse(piece4.isAtHome());
    assertEquals(startSquare2, piece4.getSquare());

    // 2. Attempt to enter game when start square is blocked
    piece4.sendHome();
    Piece blocker1 = new Piece(Color.RED);
    Piece blocker2 = new Piece(Color.RED);
    startSquare2.landHere(blocker1);
    startSquare2.landHere(blocker2); // Forms a blockage

    Piece piece5 = new Piece(Color.RED);
    piece5.enterGame(board2);
    assertTrue(piece5.isAtHome());
    assertNull(piece5.getSquare());
    piece5.sendHome();
  }

  @Test
  void enterGame_Mockito() {
    // Testing to put piece in his start square:

    // Create a mock Board and mock ShieldSquare
    Board mockBoard = Mockito.mock(Board.class);
    ShieldSquare mockStartSquare = Mockito.mock(ShieldSquare.class);
    when(mockBoard.getPlayerStartSquare(Color.RED)).thenReturn(mockStartSquare);

    Piece piece = new Piece(Color.RED);
    when(mockStartSquare.isBlocked(piece)).thenReturn(false);

    doNothing().when(mockStartSquare).landHere(piece);
    piece.enterGame(mockBoard);
    verify(mockStartSquare).landHere(piece);

    // Look if piece is not at home and square is done correctly
    assertFalse(piece.isAtHome());
    assertEquals(mockStartSquare, piece.getSquare());
  }

  @Test
  void getSquare() {
    // Statement coverage (already covered all the statements with the tests already done).

    // 1. New piece should have null square
    Piece piece = new Piece(Color.RED);
    assertNull(piece.getSquare());

    // 2. After entering the game, piece should have start square
    Board board = new Board();
    ShieldSquare startSquare = new ShieldSquare(0);
    board.setPlayerStartSquare(Color.RED, startSquare);

    leaveAllPieces(startSquare.getPieces()); // Custom method to clear pieces for testing

    piece.enterGame(board);
    assertEquals(startSquare, piece.getSquare());

    // 3. After moving, piece should have new square
    Square nextSquare = new RegularSquare(1);
    piece.getSquare().leave(piece);
    piece.setSquare(nextSquare);
    nextSquare.landHere(piece);
    assertEquals(nextSquare, piece.getSquare());

    // Equivalent partitions:
    // - Piece has no square (null).
    // - Piece has a square (not null).

    // 1. New piece should have null square
    Piece piece3 = new Piece(Color.RED);
    assertNull(piece3.getSquare());

    // 2. After entering the game, piece should have the start square
    Board board2 = new Board();
    ShieldSquare startSquare2 = board2.getPlayerStartSquare(Color.RED);
    leaveAllPieces(startSquare2.getPieces()); // Clear start square

    piece3.enterGame(board2);
    assertEquals(startSquare2, piece3.getSquare());

    // 3. After moving, piece should have a new square
    Square nextSquare2 = new RegularSquare(1);
    piece3.getSquare().leave(piece3);
    nextSquare2.landHere(piece3);
    piece3.setSquare(nextSquare2);
    assertEquals(nextSquare2, piece3.getSquare());
  }

  @Test
  void setSquare() {
    // Statement coverage (already covered all the statements with the tests already done).

    // 1. Set square to a square
    Piece piece = new Piece(Color.RED);
    Square square = new RegularSquare(10);
    piece.setSquare(square);
    assertEquals(square, piece.getSquare());

    // 2. Set square to null
    piece.setSquare(null);
    assertNull(piece.getSquare());

    // Equivalent partitions:
    // - Setting square to a valid square (empty or contains this piece).
    // - Setting square to null.
    // - Setting square to an occupied square that doesn't contain this piece (should fail).

    // 1. Set square to a valid empty square
    Piece piece2 = new Piece(Color.RED);
    Square square2 = new RegularSquare(10);
    piece2.setSquare(square2);
    assertEquals(square2, piece2.getSquare());

    // 2. Set square to null
    piece2.setSquare(null);
    assertNull(piece2.getSquare());

    // 3. Attempt to set square to an occupied square not containing this piece
    Piece otherPiece = new Piece(Color.BLUE);
    square2.landHere(otherPiece); // Now square is occupied by otherPiece
    assertTrue(square2.isOccupied());
    assertNull(piece2.getSquare());
  }

  @Test
  void setSquare_Mockito() {
    // Setting up piece to a square:
    Square mockSquare = Mockito.mock(Square.class);
    Piece piece = new Piece(Color.RED);
    piece.setSquare(mockSquare);
    assertEquals(mockSquare, piece.getSquare());
  }
  
  @Test
  void hasFinished() {
    // Statement coverage (already covered all the statements with the tests already done).

    // Equivalent partitions:
    // - Piece has not finished.
    // - Piece has finished.

    // 1. New piece should not have finished
    Piece piece2 = new Piece(Color.RED);
    assertFalse(piece2.hasFinished());

    // 2. After entering the game and setting hasFinished to true
    Board board = new Board();
    ShieldSquare startSquare = board.getPlayerStartSquare(Color.RED);
    leaveAllPieces(startSquare.getPieces()); // Clear start square

    piece2.enterGame(board);
    piece2.setHasFinished(true);
    assertTrue(piece2.hasFinished());
  }

  @Test
  void setHasFinished() {
    // Statement coverage (already covered all the statements with the tests already done).

    // Equivalent Partitions:
    // - Setting hasFinished to true when piece is not at home.
    // - Setting hasFinished to true when piece is at home (should fail).
    // - Setting hasFinished to false.

    Piece piece = new Piece(Color.RED);
    Board board = new Board();
    ShieldSquare startSquare = board.getPlayerStartSquare(Color.RED);
    leaveAllPieces(startSquare.getPieces()); // Clear start square

    // 1. Attempt to set hasFinished to true when piece is at home (should fail)
    assertThrows(AssertionError.class, () -> piece.setHasFinished(true));

    // 2. Enter the game and set hasFinished to true
    piece.enterGame(board);
    piece.setHasFinished(true);
    assertTrue(piece.hasFinished());

    // 3. Set hasFinished back to false
    piece.setHasFinished(false);
    assertFalse(piece.hasFinished());
  }

  @Test
  void getColor() {
    // Statement coverage (already covered all the statements with the tests already done).

    // Equivalent Partitions:
    // - Valid colors (RED, BLUE, GREEN, YELLOW).
    // - Invalid color (null, should fail at construction).

    // 1. Valid Color
    Piece piece = new Piece(Color.RED);
    assertEquals(Color.RED, piece.getColor());

    Piece piece2 = new Piece(Color.BLUE);
    assertEquals(Color.BLUE, piece2.getColor());

    Piece piece3 = new Piece(Color.YELLOW);
    assertEquals(Color.YELLOW, piece3.getColor());

    Piece piece4 = new Piece(Color.GREEN);
    assertEquals(Color.GREEN, piece4.getColor());

    // 2. Invalid color (null)
    assertThrows(AssertionError.class, () -> new Piece(null));
  }

  @Test
  void testToString() {
    // Statement coverage (already covered all the statements with the tests already done).

    // Equivalent Partitions:
    // - Piece at home.
    // - Piece has finished.
    // - Piece on global path.
    // - Piece on final path.
    Piece piece = new Piece(Color.RED);

    // Case 1: Piece at home
    String expected = "Piece " + piece.getId() + " is at home";
    assertEquals(expected, piece.toString());

    // Move the piece into the game
    Board board = new Board();
    ShieldSquare startSquare = board.getPlayerStartSquare(Color.RED);
    leaveAllPieces(startSquare.getPieces()); // Clear start square
    piece.enterGame(board);

    // Case 2: Piece has finished
    piece.setHasFinished(true);
    expected = "Piece " + piece.getId() + " is has finished";
    assertEquals(expected, piece.toString());

    // Case 3: Piece on global path
    piece.setHasFinished(false);
    Square square = new RegularSquare(10);
    piece.getSquare().leave(piece);
    square.landHere(piece);
    piece.setSquare(square);
    expected = "Piece " + piece.getId() + " is on global path at position " + (square.getPosition() + 1);
    assertEquals(expected, piece.toString());

    // Case 4: Piece on final path
    Square finalSquare = new FinalPathSquare(2, Color.RED);
    piece.getSquare().leave(piece);
    finalSquare.landHere(piece);
    piece.setSquare(finalSquare);
    expected = "Piece " + piece.getId() + " is on final path at position " + (((FinalPathSquare) finalSquare).getIndex() + 1);
    assertEquals(expected, piece.toString());
  }
}
