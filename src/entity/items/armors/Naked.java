package entity.items.armors;

import static core.DamageTypes.FIRE;
import static core.DamageTypes.ICE;
import static core.DamageTypes.LIGHTNING;
import static core.DamageTypes.PHYSICAL;
import static core.DamageTypes.POISON;
import static core.DamageTypes.WATER;

import entity.Armor;
import java.util.Map;

/**
 * Armor for when nothing is worn.
 */
public class Naked extends Armor {
  private static Naked singleInstance;

  /**
   * Sets the initial values.
   */
  private Naked() {
    name = "Naked";
    flatAbsorption = 0;
    absorptionMap.putAll(Map.of(
        FIRE, 0f,
        ICE, 0f,
        LIGHTNING, 0f,
        PHYSICAL, 0f,
        POISON, 0f,
        WATER, 0f
    ));
  }

  /**
   * Return the single instance of this class.

   * @return (Naked)
   */
  public static Naked getInstance() {
    if (singleInstance == null) {
      singleInstance = new Naked();
    }
    return singleInstance;
  }
}
