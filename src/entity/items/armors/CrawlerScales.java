package entity.items.armors;

import entity.Armor;

/**
 * Armor of crawler-enemy.
 */
public class CrawlerScales extends Armor {
  /**
   * Sets the initial values.
   */
  public CrawlerScales() {
    name = "Crawler Scales";
    flatAbsorption = 2;
    magicAbsorption = 0.1;
    physicalAbsorption = 0.05;
  }
}