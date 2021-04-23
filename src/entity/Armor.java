package entity;

import java.math.BigDecimal;

/**
 * Abstract armor class.
 */
public abstract class Armor extends Entity{
  protected int flatAbsorption;
  protected BigDecimal physicalAbsorption;
  protected BigDecimal magicAbsorption;

  /**
   * Returns damage after calculating negation by armor.

   * @param damage (BigDecimal)
   * @param magic true if magic, false if physical damage type (boolean)
   * @return (BigDecimal)
   */
  public BigDecimal absorb(BigDecimal damage, boolean magic) {
    if (magic) {
      return damage
      .add(BigDecimal.valueOf(-flatAbsorption))
      .multiply(BigDecimal.valueOf(1 - magicAbsorption.doubleValue()));
    }
    return damage
    .add(BigDecimal.valueOf(-flatAbsorption))
    .multiply(BigDecimal.valueOf(1 - physicalAbsorption.doubleValue()));
  }
}
