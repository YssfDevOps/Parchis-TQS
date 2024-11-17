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
    System.out.println("Welcome to the best Parchis Game!");
  }

  public int getNumberOfPlayers() {
    System.out.println("Enter number of players (2-4):");
    int numPlayers = scanner.nextInt();
    while (numPlayers < 2 || numPlayers > 4) {
      System.out.println("Invalid number of players. Please enter a number between 2 and 4:");
      numPlayers = scanner.nextInt();
    }
    scanner.nextLine();
    return numPlayers;
  }

  public String getPlayerName(int playerNumber) {
    System.out.println("Enter name for player " + playerNumber + ":");
    return scanner.nextLine();
  }

  public void showBoard(List<Player> players) {
    System.out.println("\nCurrent Game Status:");
    for (Player player : players) {
      System.out.println(player.getName() + " (" + player.getColor() + "):");
      player.displayPieces();
    }
  }

  public void showPlayerTurn(Player player) {
    System.out.println("\nIt is " + player.getName() + "'s turn (" + player.getColor() + ").");
    player.displayPieces();
  }

  public void promptRollDie(Player player) {
    System.out.println(player.getName() + ", press Enter to roll the die.");
  }

  public void showDieRoll(Player player, int roll) {
    System.out.println(player.getName() + " rolled a " + roll + ".");
  }

  public void showNoMovablePieces(Player player) {
    System.out.println(player.getName() + " has no movable pieces.");
  }

  public void showWinner(Player player) {
    System.out.println("Congratulations " + player.getName() + "! YOU ARE THE WINNER!");
  }
}
