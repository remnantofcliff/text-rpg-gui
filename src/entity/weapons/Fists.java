package entity.weapons;

import entity.Weapon;
import java.math.BigDecimal;

/**
 * Fists weapon.
 */
public class Fists extends Weapon {
  
  /**
   * Fists.
   */
  public Fists() {
    name = "Fists";
    baseDamage = BigDecimal.valueOf(5);
    dexterityModifier = BigDecimal.valueOf(2);
    magicModifier = BigDecimal.valueOf(0);
    strengthModifier = BigDecimal.valueOf(1);
    range = BigDecimal.valueOf(0.5);
  }
}
