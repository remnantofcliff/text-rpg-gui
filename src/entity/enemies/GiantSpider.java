package entity.enemies;

import core.DamageTypes;
import entity.Enemy;
import entity.Weapon;
import entity.items.armors.Naked;
import java.util.List;
import main.Game;

/**
 * Giant-spider enemy.
 */
public class GiantSpider extends Enemy {
  public GiantSpider() {
    setDefaultValues();
  }

  private class SpiderBite extends Weapon {
    private SpiderBite() {
      name = "Bite";
      baseDamage = 10;
      dexterityModifier = 2;
      magicModifier = 0;
      strengthModifier = 2;
      damageTypeMap.put(DamageTypes.PHYSICAL, 1f);
    }
  }

  @Override
  public boolean chooseAbility(Game game, int userIndex, List<Enemy> enemies) {
    return false;
  }

  @Override
  public void setDefaultValues() {
    name = "Giant spider";
    weapon = new SpiderBite();
    armor = Naked.getInstance();
    setMaxResources(120, 0, 8);
    setStats(4, 0, 4);
  }
}
