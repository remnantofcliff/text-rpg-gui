package entity.items.weapons;

import utilities.Utilities;

/**
 * Enum of damage-types.
 */
public enum DamageTypes {
  FIRE, MAGIC, PHYSICAL, POISON;

  @Override
  public String toString() {
    return Utilities.correctCapitalization(super.toString());
  }
}
