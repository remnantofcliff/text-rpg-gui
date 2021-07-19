package entity.enemies;

import static core.DamageTypes.FIRE;
import static core.DamageTypes.ICE;
import static core.DamageTypes.LIGHTNING;
import static core.DamageTypes.PHYSICAL;
import static core.DamageTypes.POISON;
import static core.DamageTypes.WATER;

import core.DamageTypes;
import entity.Armor;
import entity.Enemy;
import entity.Weapon;
import entity.abilities.specials.Rush;
import entity.interfaces.Poisonable;
import entity.interfaces.Stunnable;
import entity.items.CrawlerScale;
import entity.player.Player;
import java.util.List;
import java.util.Map;
import main.Game;

/**
 * Crawler-enemy.
 */
public class Crawler extends Enemy implements Poisonable, Stunnable {
  /**
   * Constructor sets the initial values.
   */
  public Crawler() {
    setDefaultValues();
  }

  private class CrawlerBite extends Weapon {
    private CrawlerBite() {
      name = "Bite";
      baseDamage = 1;
      dexterityModifier = 1;
      magicModifier = 0;
      strengthModifier = 1;
      damageTypeMap.put(DamageTypes.PHYSICAL, 1f);
    }
  }

  private class CrawlerScales extends Armor {
    private CrawlerScales() {
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

  @Override
  public boolean chooseAbility(Game game, int userIndex, List<Enemy> enemies) {
    var player = Player.getInstance();
    if (player.getHp() < 50 && sp >= specials.get(0).getResourceCost()) {
      specials.get(0).use(game, userIndex, enemies);
      return true;
    }
    return false;
  }

  @Override
  public void setDefaultValues() {
    name = "Crawler";
    weapon = new CrawlerBite();
    armor = new CrawlerScales();
    specials.add(new Rush());
    dropTable.put(0.2f, new CrawlerScale());
    setMaxResources(50, 0, 4);
    setStats(1, 0, 1);
  }
}
