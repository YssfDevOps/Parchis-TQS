package main.model;

import java.util.Random;

public class Die {
  private static final int MIN_VALUE = 1;
  private static final int MAX_VALUE = 6;
  private Random random;

  public Die() {
    random = new Random();
  }

  public int roll() {
    return random.nextInt(MAX_VALUE - MIN_VALUE + 1) + MIN_VALUE;
  }
}
