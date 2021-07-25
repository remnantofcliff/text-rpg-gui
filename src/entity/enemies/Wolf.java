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
import java.util.List;
import java.util.Map;

/**
 * Wolf-enemy.
 */
public class Wolf extends Enemy {
  public Wolf() {
    setDefaultValues();
  }

  private class WolfBite extends Weapon {
    private WolfBite() {
      name = "Bite";
      baseDamage = 5;
      dexterityModifier = 2;
      magicModifier = 0;
      strengthModifier = 2;
      damageTypeMap.put(DamageTypes.PHYSICAL, 1f);
    }
  }

  private class WolfFur extends Armor {
    private WolfFur() {
      name = "Fur";
      flatAbsorption = 1;
      absorptionMap.putAll(Map.of(
        FIRE, 0f,
        ICE, 0.2f,
        LIGHTNING, 0f,
        PHYSICAL, 0.1f,
        POISON, 0f,
        WATER, 0.3f
      ));
    }
  }

  @Override
  public boolean chooseAbility(int userIndex, List<Enemy> enemies) {
    return false;
  }

  @Override
  public void setDefaultValues() {
    name = "Wolf";
    weapon = new WolfBite();
    armor = new WolfFur();
    setMaxResources(80, 0, 0);
    setStats(1, 0, 1);
  }
  
}
