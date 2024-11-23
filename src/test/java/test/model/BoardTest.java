package test.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import main.model.Board;
import main.model.Color;
import main.model.Piece;
import main.model.square.FinalPathSquare;
import main.model.square.ShieldSquare;
import main.model.square.Square;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

class BoardTest {

  @Test
  void getGlobalSquare() {
    // Statement coverage (already covered all the statements with the tests already done).

    // Equivalence Partitions:
    // - Invalid positions: position < 0 (should fail)
    // - Valid positions: position >= 0 && position < 68
    // - Invalid positions: position > 68 (should fail)

    // Boundary Values:
    // - position = 0 (valid(
    // - position = NUM_SQUARES - 1 (67) (valid)
    // - position = -1 (invalid)
    // - position = NUM_SQUARES (68), (invalid)
    // - position = 1 (valid)
    // - position = 66 (valid)
    // - position = -10 (invalid)
    // - position = 100 (invalid)

    Board board = new Board();
    // 1. Get square at position within bounds

    // Boundary (Valid)
    Square square0 = board.getGlobalSquare(0);
    assertNotNull(square0);
    assertEquals(0, square0.getPosition());

    // In between (valid)
    Square square20 = board.getGlobalSquare(20);
    assertNotNull(square20);
    assertEquals(20, square20.getPosition());

    // Boundary (Valid)
    Square square67 = board.getGlobalSquare(67);
    assertNotNull(square67);
    assertEquals(67, square67.getPosition());

    // Boundary Upper (Valid)
    Square square1 = board.getGlobalSquare(1);
    assertNotNull(square1);
    assertEquals(1, square1.getPosition());

    // Boundary Lower (Valid)
    Square square66 = board.getGlobalSquare(66);
    assertNotNull(square66);
    assertEquals(66, square66.getPosition());

    // 2. Attempt to get square at negative position (should fail)
    assertThrows(AssertionError.class, () -> board.getGlobalSquare(-10));
    assertThrows(AssertionError.class, () -> board.getGlobalSquare(-1));

    // 3. Get square at position beyond NUM_SQUARES - 1, (should fail)
    assertThrows(AssertionError.class, () -> board.getGlobalSquare(68));
    assertThrows(AssertionError.class, () -> board.getGlobalSquare(100));
  }

  @Test
  void getGlobalSquare_withMockito() {
    // In this case we are mocking Square:
    List<Square> mockGlobalPath = Mockito.mock(List.class);
    Board board = new Board();

    // Need to do try and catch to make this work.
    try {
      Field globalPathField = Board.class.getDeclaredField("globalPath");
      globalPathField.setAccessible(true);
      globalPathField.set(board, mockGlobalPath);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Failed to set up mock");
    }

    Square mockSquare = Mockito.mock(Square.class);
    when(mockGlobalPath.get(anyInt())).thenReturn(mockSquare);

    // 1. Valid position
    Square result = board.getGlobalSquare(0);
    assertNotNull(result);
    verify(mockGlobalPath).get(0);

    // 2. Another valid position
    result = board.getGlobalSquare(10);
    assertNotNull(result);
    verify(mockGlobalPath).get(10);

    // 3. Invalid positions (should fail)
    assertThrows(AssertionError.class, () -> board.getGlobalSquare(-1));
    assertThrows(AssertionError.class, () -> board.getGlobalSquare(68));
  }

  @Test
  void getPlayerStartSquare() {
    // Statement coverage (already covered all the statements with the tests already done).

    Board board = new Board();
    // Equivalence Partitions:
    // - Valid colors: RED, BLUE, GREEN, YELLOW
    // - Invalid color: null (should fail)

    // 1. Get start square for RED player
    ShieldSquare redStartSquare = board.getPlayerStartSquare(Color.RED);
    assertNotNull(redStartSquare);
    assertEquals(38, redStartSquare.getPosition());
    assertTrue(redStartSquare.isShieldSquare());

    // 2. Get start square for BLUE player
    ShieldSquare blueStartSquare = board.getPlayerStartSquare(Color.BLUE);
    assertNotNull(blueStartSquare);
    assertEquals(21, blueStartSquare.getPosition());
    assertTrue(blueStartSquare.isShieldSquare());

    // 3. Get start square for YELLOW player
    ShieldSquare yellowStartSquare = board.getPlayerStartSquare(Color.YELLOW);
    assertNotNull(yellowStartSquare);
    assertEquals(4, yellowStartSquare.getPosition());
    assertTrue(yellowStartSquare.isShieldSquare());

    // 4. Get start square for GREEN player
    ShieldSquare greenStartSquare = board.getPlayerStartSquare(Color.GREEN);
    assertNotNull(greenStartSquare);
    assertEquals(55, greenStartSquare.getPosition());
    assertTrue(greenStartSquare.isShieldSquare());

    // 5. Attempt to get start square with null color (should fail)
    assertThrows(AssertionError.class, () -> board.getPlayerStartSquare(null));
  }

  @Test
  void getPlayerStartSquare_withMockito() {
    // In this case we are mocking Shield Square:
    Map<Color, Integer> mockStartPositions = Mockito.mock(Map.class);
    List<Square> mockGlobalPath = Mockito.mock(List.class);
    Board board = new Board();

    // Need to do try and catch to make this work.
    try {
      Field startPositionsField = Board.class.getDeclaredField("startPositions");
      startPositionsField.setAccessible(true);
      startPositionsField.set(board, mockStartPositions);

      Field globalPathField = Board.class.getDeclaredField("globalPath");
      globalPathField.setAccessible(true);
      globalPathField.set(board, mockGlobalPath);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Failed to set up mocks");
    }

    when(mockStartPositions.containsKey(Color.RED)).thenReturn(true);
    when(mockStartPositions.get(Color.RED)).thenReturn(10);
    ShieldSquare mockShieldSquare = Mockito.mock(ShieldSquare.class);
    when(mockGlobalPath.get(10)).thenReturn(mockShieldSquare);

    // Call the method and verify it works
    ShieldSquare result = board.getPlayerStartSquare(Color.RED);
    assertNotNull(result);
    verify(mockStartPositions).get(Color.RED);
    verify(mockGlobalPath).get(10);

    // 1. Test with a color not in startPositions
    when(mockStartPositions.containsKey(Color.GREEN)).thenReturn(false);
    assertThrows(AssertionError.class, () -> board.getPlayerStartSquare(Color.GREEN));

    // 2. Test with null color (should fail)
    assertThrows(AssertionError.class, () -> board.getPlayerStartSquare(null));
  }

  @Test
  void setPlayerStartSquare() {
    // Statement coverage (already covered all the statements with the tests already done).

    Board board = new Board();
    // Equivalence Partitions:
    // - Valid color and square
    // - Null color (should fail)
    // - Null square (should fail)

    // 1. Set valid start square
    ShieldSquare newStartSquare = new ShieldSquare(10);
    board.setPlayerStartSquare(Color.RED, newStartSquare);
    assertEquals(10, board.getPlayerStartSquare(Color.RED).getPosition());

    // 2. Attempt to set start square with null color (should fail)
    assertThrows(AssertionError.class, () -> board.setPlayerStartSquare(null, newStartSquare));

    // 3. Attempt to set start square with null square (should fail)
    assertThrows(AssertionError.class, () -> board.setPlayerStartSquare(Color.RED, null));
  }

  @Test
  void setPlayerStartSquare_withMockito() {
    // In this case we are mocking Shield Square:
    Map<Color, Integer> mockStartPositions = Mockito.mock(Map.class);
    List<Square> mockGlobalPath = Mockito.mock(List.class);
    Board board = new Board();

    // Need to do try and catch to make this work.
    try {
      Field startPositionsField = Board.class.getDeclaredField("startPositions");
      startPositionsField.setAccessible(true);
      startPositionsField.set(board, mockStartPositions);

      Field globalPathField = Board.class.getDeclaredField("globalPath");
      globalPathField.setAccessible(true);
      globalPathField.set(board, mockGlobalPath);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Failed to set up mocks");
    }

    ShieldSquare mockShieldSquare = Mockito.mock(ShieldSquare.class);
    when(mockShieldSquare.getPosition()).thenReturn(10);

    // Store the position in the map
    doAnswer(invocation -> {
      Object[] args = invocation.getArguments();
      Color colorArg = (Color) args[0];
      Integer positionArg = (Integer) args[1];
      when(mockStartPositions.get(colorArg)).thenReturn(positionArg);
      return null;
    }).when(mockStartPositions).put(any(Color.class), anyInt());

    // Call the method and verify
    board.setPlayerStartSquare(Color.RED, mockShieldSquare);
    verify(mockStartPositions).put(Color.RED, 10);
    verify(mockGlobalPath).set(10, mockShieldSquare);

    // 1. Test with null color
    assertThrows(AssertionError.class, () -> board.setPlayerStartSquare(null, mockShieldSquare));

    // 2. Test with null square
    assertThrows(AssertionError.class, () -> board.setPlayerStartSquare(Color.RED, null));
  }


  @Test
  void getPlayerFinalPath() {
    // Statement coverage (already covered all the statements with the tests already done).

    Board board = new Board();
    // Equivalence Partitions:
    // - Valid colors: RED, BLUE, GREEN, YELLOW
    // - Invalid color: null (should fail)

    // 1. Get final path for GREEN player
    List<FinalPathSquare> greenFinalPath = board.getPlayerFinalPath(Color.GREEN);
    assertNotNull(greenFinalPath);
    assertEquals(8, greenFinalPath.size());
    for (FinalPathSquare square : greenFinalPath) {
      assertEquals(Color.GREEN, square.getColor());
    }

    // 2. Attempt to get final path with null color (should fail)
    assertThrows(AssertionError.class, () -> board.getPlayerFinalPath(null));
  }

  @Test
  void getPlayerFinalPath_withMockito() {
    // In this case we are mocking FinalPath Square:
    Map<Color, List<FinalPathSquare>> mockPlayerFinalPaths = Mockito.mock(Map.class);
    Board board = new Board();

    // Need to do try and catch to make this work.
    try {
      Field playerFinalPathsField = Board.class.getDeclaredField("playerFinalPaths");
      playerFinalPathsField.setAccessible(true);
      playerFinalPathsField.set(board, mockPlayerFinalPaths);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Failed to set up mock");
    }

    List<FinalPathSquare> mockFinalPath = Mockito.mock(List.class);
    when(mockPlayerFinalPaths.containsKey(Color.GREEN)).thenReturn(true);
    when(mockPlayerFinalPaths.get(Color.GREEN)).thenReturn(mockFinalPath);

    // Call the method and verify
    List<FinalPathSquare> result = board.getPlayerFinalPath(Color.GREEN);
    assertNotNull(result);
    verify(mockPlayerFinalPaths).get(Color.GREEN);

    // 1. Test with a color not in playerFinalPaths
    when(mockPlayerFinalPaths.containsKey(Color.BLUE)).thenReturn(false);
    assertThrows(AssertionError.class, () -> board.getPlayerFinalPath(Color.BLUE));

    // 2. Test with null color
    assertThrows(AssertionError.class, () -> board.getPlayerFinalPath(null));
  }

  @Test
  void getNextSquare() {
    // Statement coverage (already covered all the statements with the tests already done).

    Board board = new Board();
    // Equivalence Partitions:
    // - currentSquare on global path
    // - currentSquare transitioning
    // - currentSquare in final path
    // - reach end of final path
    // - currentSquare is null (should fail)
    // - piece is null (should fail)

    // Case 1: Get next square on global path
    Square currentSquare = board.getGlobalSquare(10);
    Piece redPiece = new Piece(Color.RED);
    Square nextSquare = board.getNextSquare(currentSquare, redPiece);
    assertNotNull(nextSquare);
    assertEquals(11, nextSquare.getPosition());

    // Case 2: Get next square when transitioning to final path
    // For RED player, final entry position is at (38 - 6 + 68) % 68 = 32
    currentSquare = board.getGlobalSquare(32);
    nextSquare = board.getNextSquare(currentSquare, redPiece);
    assertInstanceOf(FinalPathSquare.class, nextSquare);

    // Case 3: Move within final path
    currentSquare = nextSquare; // Now on first square of final path
    nextSquare = board.getNextSquare(currentSquare, redPiece);
    assertInstanceOf(FinalPathSquare.class, nextSquare);
    assertEquals(1, ((FinalPathSquare) nextSquare).getIndex());

    // Case 4: Reaching the end of the final path
    currentSquare = board.getPlayerFinalPath(Color.RED).get(7); // Last square in final path
    nextSquare = board.getNextSquare(currentSquare, redPiece);
    assertNull(nextSquare);

    // 5. Attempt to get next square with null currentSquare (should fail)
    assertThrows(AssertionError.class, () -> board.getNextSquare(null, redPiece));

    // 6. Attempt to get next square with null piece (should fail)
    currentSquare = board.getGlobalSquare(10);
    Square finalCurrentSquare = currentSquare;
    assertThrows(AssertionError.class, () -> board.getNextSquare(finalCurrentSquare, null));
  }

  @Test
  void getNextSquare_withMockito() {
    // Mocking different classes RegularSquare, ShieldSquare and FinalPathSquare:

    Board board = new Board();
    Piece mockPiece = Mockito.mock(Piece.class);
    when(mockPiece.getColor()).thenReturn(Color.RED);

    // Mock startPositions
    Map<Color, Integer> mockStartPositions = Mockito.mock(Map.class);
    try {
      Field startPositionsField = Board.class.getDeclaredField("startPositions");
      startPositionsField.setAccessible(true);
      startPositionsField.set(board, mockStartPositions);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Failed to set up mock");
    }
    when(mockStartPositions.get(Color.RED)).thenReturn(38);

    // Mock globalPath
    List<Square> mockGlobalPath = Mockito.mock(List.class);
    try {
      Field globalPathField = Board.class.getDeclaredField("globalPath");
      globalPathField.setAccessible(true);
      globalPathField.set(board, mockGlobalPath);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Failed to set up mocks");
    }

    Square mockNextSquare = Mockito.mock(Square.class);
    when(mockNextSquare.getPosition()).thenReturn(33);
    when(mockGlobalPath.get(anyInt())).thenReturn(mockNextSquare);

    // Mock playerFinalPaths
    Map<Color, List<FinalPathSquare>> mockPlayerFinalPaths = Mockito.mock(Map.class);
    try {
      Field playerFinalPathsField = Board.class.getDeclaredField("playerFinalPaths");
      playerFinalPathsField.setAccessible(true);
      playerFinalPathsField.set(board, mockPlayerFinalPaths);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Failed to set up mock");
    }

    // Defining behaviours
    List<FinalPathSquare> mockFinalPath = Mockito.mock(List.class);
    FinalPathSquare mockFinalPathSquare0 = new FinalPathSquare(0, Color.RED);
    FinalPathSquare mockFinalPathSquare1 = new FinalPathSquare(1, Color.RED);
    when(mockFinalPath.get(0)).thenReturn(mockFinalPathSquare0);
    when(mockFinalPath.get(1)).thenReturn(mockFinalPathSquare1);
    when(mockPlayerFinalPaths.get(Color.RED)).thenReturn(mockFinalPath);

    // 1. currentSquare is not a FinalPathSquare
    Square mockCurrentSquare = Mockito.mock(Square.class);
    when(mockCurrentSquare.getPosition()).thenReturn(32);

    // 2. currentSquare is a FinalPathSquare
    Square result = board.getNextSquare(mockCurrentSquare, mockPiece);
    assertNotNull(result);
    FinalPathSquare realCurrentFinalSquare = new FinalPathSquare(0, Color.RED);

    // 3. At the end of the final path
    result = board.getNextSquare(realCurrentFinalSquare, mockPiece);
    assertNotNull(result);
    FinalPathSquare realFinalSquareEnd = new FinalPathSquare(7, Color.RED);
    result = board.getNextSquare(realFinalSquareEnd, mockPiece);
    assertNull(result);

    // 4. Test with null currentSquare
    assertThrows(AssertionError.class, () -> board.getNextSquare(null, mockPiece));

    // 5. Test with null piece
    assertThrows(AssertionError.class, () -> board.getNextSquare(mockCurrentSquare, null));
  }
}