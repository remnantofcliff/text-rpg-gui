package entity.items.armors;

import entity.Armor;

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
    physicalAbsorption = 0;
    magicAbsorption = 0;
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
