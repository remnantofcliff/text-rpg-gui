package entity.enemies;

import static core.DamageTypes.FIRE;
import static core.DamageTypes.ICE;
import static core.DamageTypes.LIGHTNING;
import static core.DamageTypes.PHYSICAL;
import static core.DamageTypes.POISON;
import static core.DamageTypes.WATER;

import entity.Armor;
import entity.Enemy;
import entity.abilities.spells.DuplicateDeception;
import entity.abilities.spells.PolymorphSelf;
import entity.interfaces.Poisonable;
import entity.interfaces.Stunnable;
import entity.items.weapons.Cane;
import java.util.List;
import java.util.Map;
import main.Game;

/**
 * Gnome in Dretnos.
 */
public class Dris extends Enemy implements Poisonable, Stunnable {
  private boolean usedPolymorph;

  public Dris() {
    setDefaultValues();
  }

  private class GreenRobesSmall extends Armor {
    private GreenRobesSmall() {
      name = "Small green robes";
      flatAbsorption = 1;
      absorptionMap.putAll(Map.of(
        FIRE, 0f,
        ICE, 0.1f,
        LIGHTNING, 0f,
        PHYSICAL, 0.1f,
        POISON, 0f,
        WATER, 0.1f
      ));
    }
  }

  @Override
  public boolean chooseAbility(Game game, int userIndex, List<Enemy> enemies) {
    var polymorph = spells.get(0);
    var dd = spells.get(1);
    if (!usedPolymorph && mp >= polymorph.getResourceCost()) {
      spells.get(0).use(game, userIndex, enemies);
      usedPolymorph = true;
      return true;
    } else if (mp >= dd.getResourceCost()) {
      dd.use(game, userIndex, enemies);
      return true;
    }
    return false;
  }

  @Override
  public void setDefaultValues() {
    name = "Dris";
    weapon = new Cane();
    armor = new GreenRobesSmall();
    dropTable.put(1f, weapon);
    spells.addAll(List.of(new PolymorphSelf(), new DuplicateDeception()));
    setMaxResources(100, 150, 0);
    setStats(2, 5, 0);
    usedPolymorph = false;
  }
}
