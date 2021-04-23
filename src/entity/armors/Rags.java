package entity.armors;

import entity.Armor;
import java.math.BigDecimal;

/**
 * Rags-armor.
 */
public class Rags extends Armor {
  
  /**
   * Rags.
   */
  public Rags() {
    name = "Rags";
    flatAbsorption = 0;
    physicalAbsorption = BigDecimal.valueOf(0);
    magicAbsorption = BigDecimal.valueOf(0);
  }
}
