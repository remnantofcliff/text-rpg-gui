package core;

import utilities.Utilities;

/**
 * Enum of damage-types.
 */
public enum DamageTypes {
  FIRE, ICE, LIGHTNING, PHYSICAL, POISON, WATER;

  @Override
  public String toString() {
    return Utilities.correctCapitalization(super.toString());
  }
}
