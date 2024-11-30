package test.controller;

import main.controller.GameController;
import main.controller.MockGameController;
import main.model.*;
import main.view.GameView;
import main.view.MockGameView;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GameControllerTest {

  @Test
  void testGameConstructor() {
    // 1. Test that the Game object initializes correctly
    GameController game = new GameController();
    assertNotNull(game);
    assertNotNull(game.getBoard());
    assertNotNull(game.getPlayers());
    assertNotNull(game.getDie());
  }

  @Test
  void initializePlayers() {
    // 1. Check initialization of players
    GameController game = new GameController();

    List<String> playerNames = Arrays.asList("Youssef", "Lucia");
    int numPlayers = playerNames.size();

    game.initializePlayers(numPlayers, playerNames);

    List<Player> players = game.getPlayers();
    assertEquals(numPlayers, players.size());

    // Check that players have correct names and colors
    assertEquals("Youssef", players.get(0).getName());
    assertEquals(Color.YELLOW, players.get(0).getColor());

    assertEquals("Lucia", players.get(1).getName());
    assertEquals(Color.BLUE, players.get(1).getColor());

    // 2. For over 4 players (should fail)
    GameController game2 = new GameController();
    List<String> playerNames2 = Arrays.asList("Youssef", "Lucia", "Lucia", "Lucia-2", "Youssef");
    int numPlayers2 = playerNames2.size();
    assertThrows(AssertionError.class, () -> game2.initializePlayers(numPlayers2, playerNames2));
  }

  @Test
  void initializePlayers_loopTesting() {
    // Testing of loop simple testing
    // MAX_PLAYERS = 4 (because of amount of colours).
    // Simple loop: 0, 1, 2, MAX_PLAYERS - 1, MAX_PLAYERS

    GameController game = new GameController();

    // 1. Loop zero times
    List<String> playerNames = List.of();
    int numPlayers = playerNames.size();
    assertThrows(AssertionError.class, () -> game.initializePlayers(numPlayers, playerNames));

    // 2. One time
    GameController game2 = new GameController();
    List<String> playerNames2 = List.of("Youssef");
    int numPlayers2 = playerNames2.size();
    game2.initializePlayers(numPlayers2, playerNames2);
    List<Player> players = game2.getPlayers();
    assertEquals(numPlayers2, players.size());
    assertEquals("Youssef", players.get(0).getName());
    assertEquals(Color.YELLOW, players.get(0).getColor());

    // 3. Two times
    GameController game3 = new GameController();
    List<String> playerNames3 = Arrays.asList("Youssef", "Lucia");
    int numPlayers3 = playerNames3.size();
    game3.initializePlayers(numPlayers3, playerNames3);
    List<Player> players3 = game3.getPlayers();
    assertEquals(numPlayers3, players3.size());

    // 4. MAX_PLAYERS - 1
    GameController game4 = new GameController();
    List<String> playerNames4 = Arrays.asList("Youssef", "Lucia", "Lucia-2");
    int numPlayers4 = playerNames4.size();
    game4.initializePlayers(numPlayers4, playerNames4);
    List<Player> players4 = game4.getPlayers();
    assertEquals(numPlayers4, players4.size());

    // 5. MAX_PLAYERS
    GameController game5 = new GameController();
    List<String> playerNames5 = Arrays.asList("Youssef", "Lucia", "Youssef-2", "Lucia-2");
    int numPlayers5 = playerNames5.size();
    game5.initializePlayers(numPlayers5, playerNames5);
    List<Player> players5 = game5.getPlayers();
    assertEquals(numPlayers5, players5.size());
  }


  @Test
  void testPlayGameUntilYellowWins() throws Exception {
    List<String> playerNames = Arrays.asList("Lucia", "Youssef");
    MockGameView mockView = new MockGameView(2, playerNames);
    GameController gameController = new GameController();

    gameController.initializePlayers(2, playerNames);

    Player lucia = gameController.getPlayers().get(0);
    Player youssef = gameController.getPlayers().get(1);
    int i = 1;
    for (Piece piece : lucia.getPieces()) {
      piece.setId(i);
      i++;
    }

    UserInput.setGameControllerView(gameController, mockView);

    List<Integer> dieRolls = new ArrayList<>();
    List<String> luciaInputsList = new ArrayList<>();
    List<String> youssefInputsList = new ArrayList<>();

    int[] luciaPieceIds = {1, 2, 3, 4};

    Map<Integer, Integer> luciaPieceSteps = new HashMap<>();
    for (int pieceId : luciaPieceIds) {
      luciaPieceSteps.put(pieceId, 0);
    }

    int totalStepsNeeded = 72; // 64 global path + 8 final path
    int currentPieceIndex = 0; // Start with the first piece
    int currentPieceId = luciaPieceIds[currentPieceIndex];
    boolean pieceAtHome = true;

    int maxTurns = 300; // In case of infinite loops
    int turn = 0;

    while (currentPieceIndex < luciaPieceIds.length && turn < maxTurns) {
      // Lucia (YELLOW) turn
      if (pieceAtHome) {
        dieRolls.add(5);
        luciaInputsList.add("yes"); // chooseToEnterPiece()
        pieceAtHome = false;
      } else {
        dieRolls.add(6);
        luciaInputsList.add(String.valueOf(currentPieceId)); // choosePiece()

        int steps = luciaPieceSteps.get(currentPieceId);
        steps += 6;
        luciaPieceSteps.put(currentPieceId, steps);

        if (steps >= totalStepsNeeded) {
          // Piece has finished
          currentPieceIndex++;
          if (currentPieceIndex < luciaPieceIds.length) {
            currentPieceId = luciaPieceIds[currentPieceIndex];
            pieceAtHome = true;
          }
        }
      }

      // Youssef turn
      dieRolls.add(1);
      youssefInputsList.add("no");
      youssefInputsList.add("1");

      turn++;
    }

    // Prepare die rolls
    Die mockDie = new MockDie(dieRolls);
    UserInput.setGameControllerDie(gameController, mockDie);

    // Inputs of Lucia
    String luciaInputs = String.join("\n", luciaInputsList) + "\n";
    InputStream luciaInputStream = new ByteArrayInputStream(luciaInputs.getBytes());
    UserInput.setPlayerScanner(lucia, luciaInputStream);

    // Inputs of Youssef
    String youssefInputs = String.join("\n", youssefInputsList) + "\n";
    InputStream youssefInputStream = new ByteArrayInputStream(youssefInputs.getBytes());
    UserInput.setPlayerScanner(youssef, youssefInputStream);

    // Set up scanner
    String gameControllerInputs = "\n".repeat(dieRolls.size());
    InputStream gameControllerInputStream = new ByteArrayInputStream(gameControllerInputs.getBytes());
    UserInput.setGameControllerScanner(gameController, gameControllerInputStream);

    // Start the game
    gameController.playGame();

    // Assertions to verify the game state
    assertTrue(lucia.isWinner());
    assertFalse(youssef.isWinner());
  }

  @Test
  void testPlayerHasNoMovablePieces() throws Exception {
    // Set up game with one player
    List<String> playerNames = Arrays.asList("Lucia");
    GameController gameController = new GameController();
    gameController.initializePlayers(1, playerNames);
    Player player = gameController.getPlayers().get(0);

    // Simulate all pieces finished
    for (Piece piece : player.getPieces()) {
      piece.setAtHome(false);
      piece.setHasFinished(true);
      piece.setSquare(null);
    }

    Die mockDie = new MockDie(Arrays.asList(3));
    UserInput.setGameControllerDie(gameController, mockDie);

    // No inputs this time.
    String playerInputs = "";
    InputStream playerInputStream = new ByteArrayInputStream(playerInputs.getBytes());
    UserInput.setPlayerScanner(player, playerInputStream);

    // Simulate pressing enter.
    String gameControllerInputs = "\n";
    InputStream gameControllerInputStream = new ByteArrayInputStream(gameControllerInputs.getBytes());
    UserInput.setGameControllerScanner(gameController, gameControllerInputStream);

    gameController.playGame();
  }

  @Test
  void playerRollDie() {
    // 1. Simulate rolling the die multiple times to test the die roll range
    GameController game = new GameController();
    for (int i = 0; i < 100; i++) {
      int roll = game.getDie().roll();
      assertTrue(roll >= 1 && roll <= 6);
    }
  }

  @Test
  void testPlayGame_loopTesting() throws Exception {
    // Nested loop testing but in this case we using mock because of the
    // complexity of the code.

    // MAX_TURNS = 1000 (for n)
    // MAX_PLAYERS = 4 (for m)
    // (n,m) = (1,0) (1,1) (1,2) (1,MAX_PLAYERS-1) (1,MAX_PLAYERS)
    // (0,m<MAX_PLAYERS) (1,m<MAX_PLAYERS) (2,m<MAX_PLAYERS) (n<MAX_TURNS-1,m<MAX_PLAYERS)
    // (MAX_TURNS-1,m<MAX_PLAYERS) (MAX_TURNS,m<MAX_PLAYERS)

    int MAX_TURNS = 1000;
    int MAX_PLAYERS = 4;

    List<int[]> testCases = Arrays.asList(
        new int[]{1, 0},      // n=1, m=0
        new int[]{1, 1},      // n=1, m=1
        new int[]{1, 2},      // n=1, m=2
        new int[]{1, MAX_PLAYERS - 1}, // n=1, m=3
        new int[]{1, MAX_PLAYERS},     // n=1, m=4
        new int[]{0, 3},      // n=0, m=3
        new int[]{1, 3},      // n=1, m=3
        new int[]{2, 3},      // n=2, m=3
        new int[]{MAX_TURNS - 1, 3},    // n=999, m=3
        new int[]{MAX_TURNS, 3}    // n=1000, m=3
    );

    for (int[] testCase : testCases) {
      int n = testCase[0]; // Number of turns
      int m = testCase[1]; // Number of players

      if (n == 0 || m == 0) {
        continue; // Skip invalid cases
      }

      List<String> playerNames = new ArrayList<>();
      for (int i = 0; i < m; i++) {
        playerNames.add("Player" + (i + 1));
      }

      MockGameController mockController = new MockGameController(n);
      mockController.initializePlayers(m, playerNames);

      Die mockDie = Mockito.mock(Die.class);
      when(mockDie.roll()).thenReturn(1);
      UserInput.setGameControllerDie(mockController, mockDie);

      GameView mockView = Mockito.mock(GameView.class);
      UserInput.setGameControllerView(mockController, mockView);

      mockController.playGame();

      // Verify
      assertEquals(n, mockController.actualTurns);
      assertEquals(n * m, mockController.totalPlayerActions);
    }
  }
}