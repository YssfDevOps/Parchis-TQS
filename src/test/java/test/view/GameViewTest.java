package test.view;

import main.model.Board;
import main.model.Color;
import main.model.Player;
import main.view.GameView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameViewTest {

  @Test
  void showWelcomeMessage() {
    GameView view = new GameView();
    assertDoesNotThrow(view::showWelcomeMessage);
  }
  
  void showBoard() {
    GameView view = new GameView();
    // 1. Passing null as players list
    List<Player> players = null;
    List<Player> finalPlayers = players;
    assertThrows(NullPointerException.class, () -> view.showBoard(finalPlayers));

    // 2. Passing empty players list should not throw an exception
    players = new ArrayList<>();
    List<Player> finalPlayers1 = players;
    assertDoesNotThrow(() -> view.showBoard(finalPlayers1));

    // 3. Passing a valid players list
    Player player = new Player("TestLucia", Color.RED, new Board());
    players.add(player);
    List<Player> finalPlayers2 = players;
    assertDoesNotThrow(() -> view.showBoard(finalPlayers2));
  }

  @Test
  void showPlayerTurn() {
    GameView view = new GameView();

    // Case 1: Passing null as player
    Player player = null;
    Player finalPlayer = player;
    assertThrows(NullPointerException.class, () -> view.showPlayerTurn(finalPlayer));

    // Case 2: Passing a valid player
    player = new Player("TestLucia", Color.RED, new Board());
    Player finalPlayer1 = player;
    assertDoesNotThrow(() -> view.showPlayerTurn(finalPlayer1));
  }

  @Test
  void showDieRoll() {
    GameView view = new GameView();

    // Case 1: Passing null as player
    Player player = null;
    int roll = 3;
    Player finalPlayer = player;
    assertThrows(NullPointerException.class, () -> view.showDieRoll(finalPlayer, roll));

    // Case 2: Passing a valid player and valid roll
    player = new Player("TestLucia", Color.RED, new Board());
    Player finalPlayer1 = player;
    assertDoesNotThrow(() -> view.showDieRoll(finalPlayer1, roll));
  }

  @Test
  void showNoMovablePieces() {
    GameView view = new GameView();

    // Case 1: Passing null as player
    Player player = null;
    Player finalPlayer = player;
    assertThrows(NullPointerException.class, () -> view.showNoMovablePieces(finalPlayer));

    // Case 2: Passing a valid player
    player = new Player("TestLucia", Color.RED, new Board());
    Player finalPlayer1 = player;
    assertDoesNotThrow(() -> view.showNoMovablePieces(finalPlayer1));
  }

  @Test
  void showWinner() {
    GameView view = new GameView();

    // Case 1: Passing null as player
    Player player = null;
    Player finalPlayer = player;
    assertThrows(NullPointerException.class, () -> view.showWinner(finalPlayer));

    // Case 2: Passing a valid player
    player = new Player("TestLucia", Color.RED, new Board());
    Player finalPlayer1 = player;
    assertDoesNotThrow(() -> view.showWinner(finalPlayer1));
  }

  @Test
  void promptRollDie() {
    GameView view = new GameView();

    // 1. Passing null as player
    Player player = null;
    Player finalPlayer = player;
    assertThrows(NullPointerException.class, () -> view.promptRollDie(finalPlayer));

    // 2. Passing a valid player
    player = new Player("TestLucia", Color.RED, new Board());
    Player finalPlayer1 = player;
    assertDoesNotThrow(() -> view.promptRollDie(finalPlayer1));
  }
}