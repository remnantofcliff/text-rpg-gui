package entity;

import java.math.BigDecimal;

/**
 * Abstract weapon class.
 */
public abstract class Weapon extends Entity {
  protected BigDecimal baseDamage;
  protected BigDecimal dexterityModifier;
  protected BigDecimal magicModifier;
  protected BigDecimal strengthModifier;
  protected BigDecimal range;

  /**
   * Calculates the damage that the weapon does given player stats.

   * @param dexterity (int)
   * @param magic (int)
   * @param strength (int)
   * @return (BigDecimal)
   */
  public BigDecimal calculateDamage(int dexterity, int magic, int strength) {
    return baseDamage
    .add(dexterityModifier.multiply(BigDecimal.valueOf(dexterity)))
    .add(magicModifier.multiply(BigDecimal.valueOf(magic)))
    .add(strengthModifier.multiply(BigDecimal.valueOf(strength)));
  }
}