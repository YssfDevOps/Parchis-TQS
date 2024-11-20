package test.controller;

import main.controller.GameController;
import main.model.Color;
import main.model.Player;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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
  void playGame() {
    // This method involves user input/output and will be tested with Mockito.
  }

  @Test
  void playerRollDie() {
    // 1. Simulate rolling the die multiple times to test the die roll range
    GameController game = new GameController();
    Player player = game.getPlayers().get(0);
    for (int i = 0; i < 100; i++) {
      int roll = game.getDie().roll();
      assertTrue(roll >= 1 && roll <= 6);
    }
  }
}