package entity.items.weapons;

import entity.Weapon;

/**
 * Crawler enemy's weapon.
 */
public class CrawlerBite extends Weapon {
  /**
   * Sets the initial values.
   */
  public CrawlerBite() {
    name = "Bite";
    baseDamage = 1;
    dexterityModifier = 1;
    magicModifier = 0;
    strengthModifier = 1;
    range = 0.5;
  }
}
