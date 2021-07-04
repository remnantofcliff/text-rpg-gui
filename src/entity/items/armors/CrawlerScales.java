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
 * Armor of crawler-enemy.
 */
public class CrawlerScales extends Armor {
  /**
   * Sets the initial values.
   */
  public CrawlerScales() {
    name = "Crawler Scales";
    flatAbsorption = 1;
    absorptionMap.putAll(Map.of(
        FIRE, 0.1f,
        ICE, 0f,
        LIGHTNING, 0f,
        PHYSICAL, 0.15f,
        POISON, 0f,
        WATER, 0.2f
    ));
  }
}