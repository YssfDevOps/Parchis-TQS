package main.controller;

import main.model.Player;

public class MockGameController extends GameController {
  private int maxTurns;
  public int actualTurns = 0;
  public int totalPlayerActions = 0;

  public MockGameController(int maxTurns) {
    super();
    this.maxTurns = maxTurns;
  }

  @Override
  public void playGame() {
    boolean gameWon = false;
    int turnCount = 0;

    while (turnCount < maxTurns) {
      for (Player player : getPlayers()) {
        totalPlayerActions++;
        // We won't simulate game logic (too complex).
      }
      turnCount++;
      actualTurns++;
    }
  }
}


