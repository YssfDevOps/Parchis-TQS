package main.controller;

import main.model.*;
import main.view.GameView;

import java.util.*;

public class GameController {
  private Board board;
  private List<Player> players;
  private Die die;
  private GameView view;
  private Scanner scanner;

  public List<Player> getPlayers() {
    return players;
  }

  public Die getDie() {
    return die;
  }

  public Board getBoard() {
    return board;
  }

  public GameController() {
    board = new Board();
    players = new ArrayList<>();
    die = new Die();
    view = new GameView();
    scanner = new Scanner(System.in);
  }

  public void startGame() {
    view.showWelcomeMessage();
    int numPlayers = view.getNumberOfPlayers();
    initializePlayers(numPlayers);
    playGame();
  }

  // For testing
  public void initializePlayers(int numPlayers, List<String> playerNames) {
    assert numPlayers > 0 && numPlayers <= Color.values().length : "Invalid number of players";

    Color[] colors = Color.values();
    for (int i = 0; i < numPlayers; i++) {
      String playerName = playerNames.get(i);
      Player player = new Player(playerName, colors[i], board);
      players.add(player);
    }

    // Postcondition
    assert players.size() == numPlayers : "Players not initialized correctly";
  }


  public void initializePlayers(int numPlayers) {
    assert numPlayers > 0 && numPlayers <= Color.values().length : "Invalid number of players";

    Color[] colors = Color.values();
    for (int i = 0; i < numPlayers; i++) {
      String playerName = view.getPlayerName(i + 1);
      Player player = new Player(playerName, colors[i], board);
      players.add(player);
    }

    // Postcondition
    assert players.size() == numPlayers : "Players not initialized correctly";
  }

  public void playGame() {
    boolean gameWon = false;
    while (!gameWon) {
      for (Player player : players) {
        view.showBoard(players);
        view.showPlayerTurn(player);

        int roll = playerRollDie(player);
        view.showDieRoll(player, roll);

        boolean hasMoved = false;

        if (roll == 5) {
          if (player.hasPiecesAtHome()) {
            boolean enterPiece = player.chooseToEnterPiece();
            if (enterPiece) {
              player.enterPieceIntoGame();
              hasMoved = true;
            }
          }
          if (!hasMoved && player.hasPiecesOnBoard()) {
            Piece pieceToMove = player.choosePiece();
            player.movePiece(pieceToMove, roll, board);
            hasMoved = true;
          }
        } else {
          if (player.hasPiecesOnBoard()) {
            Piece pieceToMove = player.choosePiece();
            player.movePiece(pieceToMove, roll, board);
            hasMoved = true;
          } else {
            view.showNoMovablePieces(player);
          }
        }
        if (player.isWinner()) {
          view.showWinner(player);
          gameWon = true;
          break;
        }
      }
    }
  }

  public int playerRollDie(Player player) {
    assert player != null : "player is null";

    view.promptRollDie(player);
    scanner.nextLine();
    return die.roll();
  }
}
