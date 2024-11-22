package test.controller;

import main.controller.GameController;
import main.model.*;
import main.view.MockGameView;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

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
  }

  @Test
  void testPlayGameUntilYellowWins() throws Exception {
    List<String> playerNames = Arrays.asList("Lucia", "Youssef");
    MockGameView mockView = new MockGameView(2, playerNames);
    GameController gameController = new GameController();

    gameController.initializePlayers(2, playerNames);

    Player lucia = gameController.getPlayers().get(0);
    Player youssef = gameController.getPlayers().get(1);

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

    // Start the game (one iteration)
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
}