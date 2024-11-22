package main.model;

import java.util.List;

public class MockDie extends Die{
  private List<Integer> predefinedRolls;
  private int index;

  public MockDie(List<Integer> predefinedRolls) {
    this.predefinedRolls = predefinedRolls;
    this.index = 0;
  }

  @Override
  public int roll() {
    if (index < predefinedRolls.size()) {
      return predefinedRolls.get(index++);
    } else {
      return predefinedRolls.get(predefinedRolls.size() - 1);
    }
  }
}
