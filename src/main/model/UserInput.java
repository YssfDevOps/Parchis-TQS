package main.model;
import main.controller.GameController;
import main.view.GameView;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Scanner;

// Control inputs of each player
public class UserInput {
  public static void setPlayerScanner(Player player, InputStream inputStream) throws Exception {
    Field scannerField = Player.class.getDeclaredField("scanner");
    scannerField.setAccessible(true);
    Scanner scanner = new Scanner(inputStream);
    scannerField.set(player, scanner);
  }

  public static void setGameControllerView(GameController gameController, GameView view) throws Exception {
    Field viewField = GameController.class.getDeclaredField("view");
    viewField.setAccessible(true);
    viewField.set(gameController, view);
  }

  public static void setGameControllerDie(GameController gameController, Die die) throws Exception {
    Field dieField = GameController.class.getDeclaredField("die");
    dieField.setAccessible(true);
    dieField.set(gameController, die);
  }

  public static void setGameControllerScanner(GameController gameController, InputStream inputStream) throws Exception {
    Field scannerField = GameController.class.getDeclaredField("scanner");
    scannerField.setAccessible(true);
    Scanner scanner = new Scanner(inputStream);
    scannerField.set(gameController, scanner);
  }
}
