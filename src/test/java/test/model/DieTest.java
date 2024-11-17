package test.model;

import main.model.Die;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class DieTest {

  @Test
  void roll() {
    Die die = new Die();
    int rolls = 1000; // Number of times to roll the die

    for (int i = 0; i < rolls; i++) {
      int result = die.roll();
      assertTrue(result >= 1 && result <= 6);
    }
  }
}
