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
    // Començar partida
  }

  public void initializePlayers(int numPlayers) {
    // Asignar color a cada jugador
  }

  public void playGame() {
    // Bucle principal
  }

  public int playerRollDie(Player player) {
    // Llençar dau
    return 0;
  }
}
