package test.controller;

import main.controller.GameController;
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
    List<String> playerNames = Arrays.asList("Youssef", "Lucia", "Carlos", "Carla");
    int numPlayers = 4;
    game.initializePlayers(numPlayers);
    List<Player> players = game.getPlayers();
    assertEquals(numPlayers, players.size());
  }

  @Test
  void playGame() {
    // This method involves user input/output and will be tested with Mockito.
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