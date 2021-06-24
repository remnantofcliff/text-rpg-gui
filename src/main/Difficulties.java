package main;

/**
 * Enum of difficulties.
 */
public enum Difficulties {
  NORMAL, CRITICAL;

  @Override
  public String toString() {
    var temp = super.toString();
    return temp.substring(0, 1) + temp.substring(1, temp.length()).toLowerCase();
  }
}