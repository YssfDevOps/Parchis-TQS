package main.view;
import java.util.List;

public class MockGameView extends GameView {
  private int numberOfPlayers;
  private List<String> playerNames;

  public MockGameView(int numberOfPlayers, List<String> playerNames) {
    this.numberOfPlayers = numberOfPlayers;
    this.playerNames = playerNames;
  }

  @Override
  public int getNumberOfPlayers() {
    return numberOfPlayers;
  }

  @Override
  public String getPlayerName(int playerNumber) {
    return playerNames.get(playerNumber - 1);
  }
}

