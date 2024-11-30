package main.model;

import main.controller.GameController;
import main.view.GameView;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Scanner;

public class UserInput {

  public static void setPlayerScanner(Player player, InputStream inputStream) {
    try {
      Field scannerField = Player.class.getDeclaredField("scanner");
      scannerField.setAccessible(true);
      Scanner scanner = new Scanner(inputStream);
      scannerField.set(player, scanner);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("Failed to set player scanner", e);
    }
  }

  public static void setGameControllerView(GameController gameController, GameView view) {
    try {
      Field viewField = GameController.class.getDeclaredField("view");
      viewField.setAccessible(true);
      viewField.set(gameController, view);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("Failed to set game controller view", e);
    }
  }

  public static void setGameControllerDie(GameController gameController, Die die) {
    try {
      Field dieField = GameController.class.getDeclaredField("die");
      dieField.setAccessible(true);
      dieField.set(gameController, die);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("Failed to set game controller die", e);
    }
  }

  public static void setGameControllerScanner(GameController gameController, InputStream inputStream) {
    try {
      Field scannerField = GameController.class.getDeclaredField("scanner");
      scannerField.setAccessible(true);
      Scanner scanner = new Scanner(inputStream);
      scannerField.set(gameController, scanner);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("Failed to set game controller scanner", e);
    }
  }
}
