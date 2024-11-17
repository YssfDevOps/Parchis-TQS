package main.view;

import main.model.*;

import java.util.List;
import java.util.Scanner;

public class GameView {
  private Scanner scanner; // For inputs

  public GameView() {
    scanner = new Scanner(System.in);
  }

  public void showWelcomeMessage() {
    System.out.println("Welcome to a game of Parchis!");
  }

  public int getNumberOfPlayers() {

  }

  public String getPlayerName(int playerNumber) {

  }

  public void showBoard(List<Player> players) {

  }


  public void showPlayerTurn(Player player) {

  }

  public void showDieRoll(Player player, int roll) {
  }

  public void showNoMovablePieces(Player player) {

  }

  public void showWinner(Player player) {

  }

  public void promptRollDie(Player player) {

  }
}
