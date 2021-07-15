package entity.enemies;

import static core.DamageTypes.FIRE;
import static core.DamageTypes.ICE;
import static core.DamageTypes.LIGHTNING;
import static core.DamageTypes.PHYSICAL;
import static core.DamageTypes.POISON;
import static core.DamageTypes.WATER;

import entity.Armor;
import entity.Enemy;
import entity.interfaces.Poisonable;
import entity.interfaces.Stunnable;
import entity.items.weapons.Cane;
import java.util.Map;
import main.Game;

/**
 * Gnome in Dretnos.
 */
public class Dris extends Enemy implements Poisonable, Stunnable {

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
  public boolean chooseAbility(Game game, int userIndex, Enemy[] enemies) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setDefaultValues() {
    name = "Dris";
    weapon = new Cane();
    armor = new GreenRobesSmall();
    dropTable.put(1f, new Cane());
    setMaxResources(100, 150, 0);
    setStats(2, 5, 0);
  }
}
