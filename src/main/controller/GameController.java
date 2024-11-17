package main.controller;

import main.model.*;
import java.util.*;

public class GameController {
  private Board board;
  private List<Player> players;
  private Die die;

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
  }

  public void startGame() {
    int numPlayers = 4;
    initializePlayers(numPlayers);
    playGame();
  }

  public void initializePlayers(int numPlayers) {
    Color[] colors = Color.values();
    for (int i = 0; i < numPlayers; i++) {
      String playerName = "Youssef"; // Needs inputs
      Player player = new Player(playerName, colors[i], board);
      players.add(player);
    }
  }

  public void playGame() {
    boolean gameWon = false;
    while (!gameWon) {
      for (Player player : players) {
        int roll = playerRollDie(player);

        boolean hasMoved = false;

        if (roll == 10) {
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
          }
        }
        if (player.isWinner()) {
          gameWon = true;
          break;
        }
      }
    }
  }

  public int playerRollDie(Player player) {
    return die.roll();
  }
}
