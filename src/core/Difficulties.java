package core;

import utilities.Utilities;

/**
 * Enum of difficulties.
 */
public enum Difficulties {
  NORMAL, CRITICAL;

  @Override
  public String toString() {
    return Utilities.correctCapitalization(super.toString());
  }
}